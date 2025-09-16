package z3roco01.techedout.block.machines

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.machines.AlloySmelterBlockEntity

class AlloySmelterBlock(private val tier: Tier): BlockWithEntity(Settings.create()) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = AlloySmelterBlockEntity(pos, state, tier)

    // needed since by default BlockWithEntity makes it invisible
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL
}