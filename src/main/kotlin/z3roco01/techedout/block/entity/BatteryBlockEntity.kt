package z3roco01.techedout.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class BatteryBlockEntity(pos: BlockPos, state: BlockState): BlockEntity(TechedOutBlockEntities.tempBattery, pos, state) {
}