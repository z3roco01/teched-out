package z3roco01.techedout.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier

/**
 * A block entity for the [z3roco01.techedout.block.BatteryBlock]
 * @param tier the tier of this battery, determines the specs
 */
class BatteryBlockEntity(private val tier: Tier, pos: BlockPos, state: BlockState):
    EnergyStorageBlockEntity(TechedOutBlockEntities.tempBattery, pos, state) {
    // constructor for registration
    constructor(pos: BlockPos, state: BlockState): this(Tier.LOW, pos, state)

    override fun getEnergyCapacity() = when(tier) {
        Tier.LOW -> 64000L // 16 coal
        Tier.MIDDLE -> 512000L // 128 coal
        Tier.HIGH -> 1024000L // 256 coal
    }

    override fun getMaxInsert() = when(tier) {
        Tier.LOW -> 187L // ~quater of a plank a tick
        Tier.MIDDLE -> 375L // ~half a plank a tick
        Tier.HIGH -> 1075L // ~1.5 planks a tick
    }

    override fun getMaxExtract() = when(tier) {
        Tier.LOW -> 187L // ~quater of a plank a tick
        Tier.MIDDLE -> 375L // ~half a plank a tick
        Tier.HIGH -> 1075L // ~1.5 planks a tick
    }
}