package z3roco01.techedout.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import team.reborn.energy.api.base.SimpleSidedEnergyContainer
import z3roco01.techedout.TechedOut

/**
 * Outlines what is needed to implement an energy storage in a block entity
 * @param type the BlockEntityType for the block
 * @param pos passed from the block, its position
 * @param state passed from the block, its BlockState
 */
abstract class EnergyStorageBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState):
    BlockEntity(type, pos, state) {

    /**
     * Holds a map from the directions to that sides permissions
     */
    val ioPermMap = mutableMapOf(
        Direction.UP    to IOPermission.INS_EXT,
        Direction.DOWN  to IOPermission.INS_EXT,
        Direction.EAST  to IOPermission.INS_EXT,
        Direction.WEST  to IOPermission.INS_EXT,
        Direction.NORTH to IOPermission.INS_EXT,
        Direction.SOUTH to IOPermission.INS_EXT
    )

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
            // only return the insert value if this side can insert
            if(canInsert(dir))
                return getMaxInsert()
            return 0
        }

        override fun getMaxExtract(dir: Direction?): Long {
            // only return the extract value if this side is allowed to extract
            if(canExtract(dir))
                return getMaxInsert()
            return 0
        }

        /**
         * Returns if the passed side can be inserted into
         */
        fun canInsert(dir: Direction?): Boolean {
            // if there is no direction, then dont allow it
            if(dir == null) return false
            // else get the permissions from the map and return canInsert
            return ioPermMap[dir]!!.canInsert
        }

        /**
         * Returns if the passed side can be extracted from
         */
        fun canExtract(dir: Direction?): Boolean {
            // if there is no direction, then dont allow it
            if(dir == null) return false
            // else get the permissions from the map and return canExtract
            return ioPermMap[dir]!!.canExtract
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

    /**
     * An enum to denote what permissions are on a side
     * @param canInsert true when this permission allows for insertion
     * @param canExtract true when this permissions allows for extraction
     */
    enum class IOPermission(val canInsert: Boolean, val canExtract: Boolean) {
        NONE(false, false),
        INSERT(true, false),
        EXTRACT(false, true),
        INS_EXT(true, true)
    }
}