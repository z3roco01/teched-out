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
import z3roco01.techedout.screen.MillScreenHandler

class MillBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.MILL, pos, state, tier) {
    constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)

    override val items = DefaultedList.ofSize(4, ItemStack.EMPTY)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        MillScreenHandler(syncId, playerInventory, this)
}