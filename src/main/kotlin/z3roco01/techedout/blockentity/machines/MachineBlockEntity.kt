package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity

/**
 * A class that outlines a few utilites for machines ( excluding batteries ) and standardizes capacities and draw
 */
abstract class MachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState, private val tier: Tier):
    EnergyStorageBlockEntity(type, pos, state) {

    init {
        // set the permissions so it can only draw power, ever supply
        for(dir in Direction.entries) // loop over every direction and set its permission to insert only
            ioPermMap[dir] = IOPermission.INSERT
    }

    // machines may not cover every tier, unsure
    override fun getEnergyCapacity() = when(tier) {
        Tier.LOW        -> 2000L
        Tier.MIDDLE     -> 4000L
        Tier.HIGH       -> 8000L
        Tier.VERY_HIGH  -> 16000L
        Tier.ULTRA_HIGH -> 32000L
    }

    /**
     * A common transfer rate function, used in max insert AND extract, incase a machine allows extraction
     */
    fun getTransferRate(): Long = when(tier) {
        Tier.LOW        -> 32L
        Tier.MIDDLE     -> 48L
        Tier.HIGH       -> 64L
        Tier.VERY_HIGH  -> 128L
        Tier.ULTRA_HIGH -> 256L
    }

    override fun getMaxInsert()  = getTransferRate()
    override fun getMaxExtract() = getTransferRate()
}