package z3roco01.techedout.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.entity.BatteryBlockEntity

class BatteryBlock() : BlockWithEntity(Settings.create()) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BatteryBlockEntity(pos, state)

    // needed since by default BlockWithEntity makes it invisible
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL
}