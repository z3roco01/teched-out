package z3roco01.techedout.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

class BatteryBlockEntity(pos: BlockPos, state: BlockState): EnergyStorageBlockEntity(TechedOutBlockEntities.tempBattery, pos, state) {
    override fun getEnergyCapacity() = 100000L

    override fun getMaxInsert() = 375L

    override fun getMaxExtract() = 375L
}