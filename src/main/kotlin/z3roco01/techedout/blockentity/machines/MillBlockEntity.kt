package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.MillRecipe
import z3roco01.techedout.screen.MillScreenHandler
import java.util.Optional

class MillBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.MILL, pos, state, tier) {
    constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)

    override val items = DefaultedList.ofSize(4, ItemStack.EMPTY)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        MillScreenHandler(syncId, playerInventory, this)

    /**
     * Keeps track of how far this current recipe is progressed
     */
    private var curProgress: Int = 0
    /**
     * Holds the max progression value of this recipe
     */
    private var maxProgress: Int = 0

    // ticked so it can check for recipe, and progress it
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyStorageBlockEntity) {
        if(world.isClient) return

        val recipe = getCurrentRecipe()
        // only do recipe logic if there is actually a recipe
        if(recipe.isPresent) {
            // set the max progress to the alloy time
            maxProgress = recipe.get().millTime

            // if it can keep drawing energy and the recipe is not finished, then draw and continue
            if(curProgress < maxProgress && getEnergy() >= recipe.get().energyPerTick) {
                decrementEnergy(recipe.get().energyPerTick.toLong())
                // also increment progress, since we have taken energy
                curProgress++
            }

            // if the progress equal to the max, make the recipe if there is space
            if(curProgress >= maxProgress && outputHasSpace(recipe.get()))
                craft(recipe.get())
        }else {
            // there is not longer a recipe, so reset the progresses
            resetProgresses()
        }
    }

    /**
     * Resets both current progress and max progress
     */
    fun resetProgresses() {
        curProgress = 0
        maxProgress = 0
    }

    /**
     * Checks and returns if the current inputs correspond to a recipe
     */
    private fun getCurrentRecipe(): Optional<MillRecipe> {
        if(world == null) return Optional.empty()

        val inv = SimpleInventory(items[0])

        return world!!.recipeManager.getFirstMatch(MillRecipe.Type.INSTANCE, inv, world!!)
    }


    /**
     * gets the number of output slots that are empty
     * @return how many output slots are empty
     */
    fun emptyOutputs(): Int {
        var empty = 0
        // loop over slots 3 through 6
        for(idx in 1..3) {
            if(items[idx].isEmpty)
                empty++
        }

        return empty
    }

    /**
     * Calculates the number of not empty item stacks in a list
     */
    fun notEmptyInList(list: List<ItemStack>): Int {
        var notEmpty = 0
        for(item in list) {
            if(!item.isEmpty)
                notEmpty++
        }

        return notEmpty
    }

    /**
     * Consumes the input items and creates the output items
     */
    fun craft(recipe: MillRecipe) {
        // loop over each output and add it to the slots
        for(output in recipe.outputs) {
            addToFirstSlot(output)
        }

        // and take away the proper amount from the inputs
        // attempt to find an ingredient
        val ingredient = recipe.input
        // if there is one for this item, take away the count
        if(ingredient != null)
            items[0].count -= ingredient.count
    }

    /**
     * Finds either an empty slot, or slot with enough space ( which ever is first) and copys the item stack to it
     * @return true if it was successfully added, false otherwise
     */
    fun addToFirstSlot(stack: ItemStack): Boolean {
        var ret = false
        // loop over slots 3 to 6
        for(i in 1..3) {
            val slot = items[i]
            // if an empty one is found then it can just be added
            if(slot.isEmpty) {
                items[i] = stack.copy()
                ret = true
                break
            }

            // if it is of the same item, but there is space, add it
            if(slot.item == stack.item && (slot.maxCount - slot.count) >= stack.count) {
                items[i].count += stack.count
                ret = true
                break
            }
        }

        return ret
    }

    /**
     * Checks the output slots for space for the new output
     */
    fun outputHasSpace(recipe: MillRecipe): Boolean {
        // if there is at least one slot of each empty item, then no further checks are needed
        if(emptyOutputs() >= notEmptyInList(recipe.outputs)) return true

        // keep true until a uninsertable items comes by
        var ret = true
        // loop over each output and check against each slot
        for(output in recipe.outputs) {
            for(i in 1..3) {
                val stack = items[i]
                // check if the item is the same
                ret = !(stack.item == output.item)
                // if it is them check if there is space
                if(ret) ret = ((stack.maxCount - stack.count) >= output.count)

                // if ret is true, meaning a slot has been found, break
                if(ret) break
            }
            // if ret is false then there was not a slot that it could fit in, so return false
            if(!ret)
                return false
        }
        // every check has succeeded, so return true
        return true
    }
}