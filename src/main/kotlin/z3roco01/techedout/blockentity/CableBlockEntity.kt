package z3roco01.techedout.blockentity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier

class CableBlockEntity(private val tier: Tier, pos: BlockPos, state: BlockState):
    EnergyStorageBlockEntity(TechedOutBlockEntities.CABLE, pos, state) {
    constructor(pos: BlockPos, state: BlockState): this(Tier.LOW, pos, state)

    override fun getEnergyCapacity() = when(tier) {
        Tier.LOW        -> 128L
        Tier.MIDDLE     -> 256L
        Tier.HIGH       -> 512L
        Tier.VERY_HIGH  -> 1024L
        Tier.ULTRA_HIGH -> 2048L
    }

    override fun getMaxInsert() = when(tier) {
        Tier.LOW        -> 128L
        Tier.MIDDLE     -> 256L
        Tier.HIGH       -> 512L
        Tier.VERY_HIGH  -> 1024L
        Tier.ULTRA_HIGH -> 2048L
    }

    override fun getMaxExtract() = when(tier) {
        Tier.LOW        -> 128L
        Tier.MIDDLE     -> 256L
        Tier.HIGH       -> 512L
        Tier.VERY_HIGH  -> 1024L
        Tier.ULTRA_HIGH -> 2048L
    }

}