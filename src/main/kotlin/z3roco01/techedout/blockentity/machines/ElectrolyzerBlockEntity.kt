package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.AlloyingRecipe
import z3roco01.techedout.recipe.ElectrolyzeRecipe
import z3roco01.techedout.screen.ElectrolyzerScreenHandler
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
        TechedOut.logger.info("fart")
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
            if(curProgress >= maxProgress/* && outputHasSpace(recipe.get())*/)
                TechedOut.logger.info("hey gang")
                //craft(recipe.get())
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
    private fun getCurrentRecipe(): Optional<ElectrolyzeRecipe> {
        // if there is no world then it cant get recipe manager
        if(world == null) return Optional.empty<ElectrolyzeRecipe>()

        // create a simple inventory of the first 3 slots ( inputs ) for the recipe
        val inv = SimpleInventory(items[0], items[1], items[2])

        return world!!.recipeManager.getFirstMatch(ElectrolyzeRecipe.Type.INSTANCE, inv, world!!)
    }
}