package z3roco01.techedout.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import team.reborn.energy.api.base.SimpleSidedEnergyContainer

/**
 * Outlines what is needed to implement an energy storage in a block entity
 * @param type the BlockEntityType for the block
 * @param pos passed from the block, its position
 * @param state passed from the block, its BlockState
 */
abstract class EnergyStorageBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState):
    BlockEntity(type, pos, state) {

    /**
     * Handles energy storage with the TechReborn Energy API
     */
    val energyStorage = object: SimpleSidedEnergyContainer() {
        override fun onFinalCommit() {
            // Marks the BlockEntity as dirty, so that it will trigger a data save
            markDirty()
        }

        override fun getCapacity(): Long = getEnergyCapacity()

        override fun getMaxInsert(dir: Direction?): Long {
            return getMaxInsert()
        }

        override fun getMaxExtract(dir: Direction?): Long {
            return getMaxExtract()
        }
    }

    /**
     * Implemented by inheriters, used to determine the capacity of the energy storage
     */
    abstract fun getEnergyCapacity(): Long
    /**
     * Implemented by inheriters, used to determine the max amount of energy that can be inserted per tick
     */
    abstract fun getMaxInsert(): Long
    /**
     * Implemented by inheriters, used to determine the max amount of energy that can be extracted per tick
     */
    abstract fun getMaxExtract(): Long

    /**
     * the key for accessing the energy count in nbt
     */
    val ENERGY_NBT_KEY = ""

    // overriden so it can store its energy amount
    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putLong(ENERGY_NBT_KEY, energyStorage.amount)
    }

    // retrieves the amount from nbt
    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        // get the long from the nbt and set it
        energyStorage.amount = nbt.getLong(ENERGY_NBT_KEY)
    }
}