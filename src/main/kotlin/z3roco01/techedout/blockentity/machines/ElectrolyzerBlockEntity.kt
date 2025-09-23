package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.ElectrolyzeRecipe
import z3roco01.techedout.screen.ElectrolyzerScreenHandler
import java.security.IdentityScope
import java.util.Optional

class ElectrolyzerBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.ELECTROLYZER, pos, state, tier) {
        constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.HIGH)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        ElectrolyzerScreenHandler(syncId, playerInventory, this)

    override val items = DefaultedList.ofSize(6, ItemStack.EMPTY)

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
            maxProgress = recipe.get().electrolyzeTime

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
     * Checks the output slots for space for the new output
     */
    fun outputHasSpace(recipe: ElectrolyzeRecipe): Boolean {
        // if there is at least one slot of each empty item, then no further checks are needed
        if(emptyOutputs() >= notEmptyInList(recipe.outputs)) return true

        return false // ( temp )
        // loop over each output and check against each slot
        for(output in recipe.outputs) {

        }
    }

    /**
     * gets the number of output slots that are empty
     * @return how many output slots are empty
     */
    fun emptyOutputs(): Int {
        var empty = 0
        // loop over slots 3 through 6
        for(idx in 2..5) {
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
    fun craft(recipe: ElectrolyzeRecipe) {
        // loop over each output and add it to the slots
        for(output in recipe.outputs) {
            addToFirstSlot(output)
        }

        // and take away the proper amount from the inputs
        for(i in 0..1) {
            // attempt to find an ingredient
            val ingredient = recipe.findInput(items[i])
            // if there is one for this item, take away the count
            if(ingredient != null) {
                items[i].count -= ingredient.count
            }
        }
    }

    /**
     * Finds either an empty slot, or slot with enough space ( which ever is first) and copys the item stack to it
     * @return true if it was successfully added, false otherwise
     */
    fun addToFirstSlot(stack: ItemStack): Boolean {
        var ret = false
        // loop over slots 3 to 6
        for(i in 2..5) {
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
     * Resets both current progress and max progress
     */
    fun resetProgresses() {
        curProgress = 0
        maxProgress = 0
    }

    /**
     * Checks and returns if the current inputs correspond to a recipe
     */
    private fun getCurrentRecipe(): Optional<ElectrolyzeRecipe> {
        // if there is no world then it cant get recipe manager
        if(world == null) return Optional.empty<ElectrolyzeRecipe>()

        // create a simple inventory of the first 2 slots ( inputs ) for the recipe
        val inv = SimpleInventory(items[0], items[1])

        return world!!.recipeManager.getFirstMatch(ElectrolyzeRecipe.Type.INSTANCE, inv, world!!)
    }
}