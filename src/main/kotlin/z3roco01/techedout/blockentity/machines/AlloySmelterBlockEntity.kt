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
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.AlloyingRecipe
import z3roco01.techedout.screen.AlloySmelterScreenHandler
import java.util.Optional
import kotlin.math.max

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
            // increment progress each tick, only if it has not exceeded the mxa
            if(curProgress < maxProgress)
                curProgress++

            // if it is equal to the max, make the recipe if there is space
            if(items[3].isEmpty) {
                items[3] = recipe.get().getOutput(null).copy()
            }
        }else {

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