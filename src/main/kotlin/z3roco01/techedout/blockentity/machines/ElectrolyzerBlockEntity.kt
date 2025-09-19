package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.screen.ElectrolyzerScreenHandler

class ElectrolyzerBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.ELECTROLYZER, pos, state, tier) {
        constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.HIGH)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        ElectrolyzerScreenHandler(syncId, playerInventory, this)

    override val items = DefaultedList.ofSize(6, ItemStack.EMPTY)
}