package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.AlloyingRecipe
import z3roco01.techedout.screen.AlloySmelterScreenHandler
import java.util.Optional

class AlloySmelterBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.ALLOY_SMELTER, pos, state, tier) {
    constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)


    override val items = DefaultedList.ofSize(4, ItemStack.EMPTY)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler =
        AlloySmelterScreenHandler(syncId, playerInventory, this)

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
            maxProgress = recipe.get().alloyTime

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
     * Resets both current and max progress to 0, to be used when there is no recipe
     */
    private fun resetProgresses() {
        curProgress = 0
        maxProgress = 0
    }

    /**
     * Checks if the output is either empty, or of the same item and has space for the new amount
     */
    private fun outputHasSpace(recipe: AlloyingRecipe): Boolean {
        // if the output slot is not empty, and if the items are not the same, there is no way it can be outputted
        if(!items[3].isEmpty && items[3].item != recipe.getOutput(null).item) return false

        // if the current output stack is at its max capacity then it also cant be added to
        if(items[3].count == items[3].maxCount) return false

        // now check if it is output-count less than or more its max, if so return true
        return (items[3].count <= items[3].maxCount-recipe.getOutput(null).count)
    }

    /**
     * Sets the output, and takes away the inputs
     */
    private fun craft(recipe: AlloyingRecipe) {
        // loop over each input and ingredient
        for(i in 0..<3) {
            // attempt to find a matching ingredient
            val ingredient = recipe.findIngredient(items[i])
            // if an ingredient was found, decrement the count
            if(ingredient != null)
                items[i].decrement(ingredient.count)
        }

        // if there is already the output item in the slot, just increment the count
        if(items[3].item == recipe.getOutput(null).item)
            items[3].increment(recipe.getOutput(null).count)
        else // otherwise just set the slot
            items[3] = recipe.getOutput(null).copy()

        // then reset all progress counters
        resetProgresses()
    }

    /**
     * Checks if there is a recipe that corresponds to the inputs
     */
    private fun hasRecipe() = getCurrentRecipe().isPresent

    /**
     * Checks and returns if the current inputs correspond to a recipe
     */
    private fun getCurrentRecipe(): Optional<AlloyingRecipe> {
        // if there is no world then it cant get recipe manager
        if(world == null) return Optional.empty<AlloyingRecipe>()

        // create a simple inventory of the first 3 slots ( inputs ) for the recipe
        val inv = SimpleInventory(items[0], items[1], items[2])

        return world!!.recipeManager.getFirstMatch(AlloyingRecipe.Type.INSTANCE, inv, world!!)
    }
}