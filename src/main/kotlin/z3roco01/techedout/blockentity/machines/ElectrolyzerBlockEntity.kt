package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.TechedOutBlockEntities

class ElectrolyzerBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.ALLOY_SMELTER, pos, state, tier) {
        constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
    }

    // TODO: TEMP SIZE WILL MAKE ACTUAL INVENTORY SIZE
    override val items = DefaultedList.ofSize(1, ItemStack.EMPTY)
}