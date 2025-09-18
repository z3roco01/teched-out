package z3roco01.techedout.blockentity

import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import team.reborn.energy.api.EnergyStorage
import team.reborn.energy.api.EnergyStorageUtil
import team.reborn.energy.api.base.SimpleSidedEnergyContainer
import z3roco01.techedout.network.SyncEnergyPacket

/**
 * Outlines what is needed to implement an energy storage in a block entity
 * @param type the BlockEntityType for the block
 * @param pos passed from the block, its position
 * @param state passed from the block, its BlockState
 */
abstract class EnergyStorageBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState):
    BlockEntity(type, pos, state), BlockEntityTicker<EnergyStorageBlockEntity> {

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

    // overridden so that it will always sync energy to the clients when called
    override fun markDirty() {
        // check if the world exists and were not on the client
        if(world != null && !world!!.isClient){
            // loop over every player and send them an update packet
            for(player in PlayerLookup.tracking(world as ServerWorld, getPos()))
                ServerPlayNetworking.send(player, SyncEnergyPacket.ID, SyncEnergyPacket(getEnergy(), pos).toBuf())
        }

        super.markDirty()
    }

    // overidden so the block can push to other blocks around it ( how its supposed to work )
    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyStorageBlockEntity) {
        // do not run on the client or if it has no energy
        if(world.isClient || getEnergy() <= 0) return

        // loop over each side
        for(side in Direction.entries) {
            // push energy to anything that is accepting energy beside it
            // this api is push based not pull so energy should never be pulled
            EnergyStorageUtil.move(
                energyStorage.getSideStorage(side),
                EnergyStorage.SIDED.find(world, pos.offset(side), side.opposite),
                Long.MAX_VALUE,
                null
            )
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
     * Returns how much energy is stored in the energy storage
     */
    fun getEnergy() = energyStorage.amount

    /**
     * Decrements the stored energy by the specified amount, and marks this block dirt
     * @return the amount of energy that could not be consumed ( if any )
     */
    fun decrementEnergy(amount: Long): Long {
        var remainder = 0L
        // dont let the energy go 0, if it is to little, set it to 0 and return remainder
        if(getEnergy() < amount) {
            remainder = amount - energyStorage.amount
            energyStorage.amount = 0
        }else // otherwise just remove energy
            energyStorage.amount -= amount

        // mark dirty so it saves and syncs
        markDirty()

        return remainder
    }

    /**
     * Increments the stored energy by the specified amount
     * @return the amount of energy that could not be stored ( if any )
     */
    fun incrementEnergy(amount: Long): Long {
        var remainder = 0L

        // if it cant store all the energy, set it to capacity and return the remainder
        if(getEnergy() + amount > getEnergyCapacity()) {
            remainder = getEnergyCapacity() - energyStorage.amount
            energyStorage.amount = getEnergyCapacity()
        }else // otherwise just remove energy
            energyStorage.amount += amount

        // mark dirty so it saves and syncs
        markDirty()

        return remainder
    }

    /**
     * the key for accessing the energy count in nbt
     */
    val ENERGY_NBT_KEY = "teched_out:energy"

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

    companion object {
        /**
         * Called from the block, allows for ticking of this block
         */
        fun staticTick(world: World, pos: BlockPos, state: BlockState, blockEntity: EnergyStorageBlockEntity) {
            blockEntity.tick(world, pos, state, blockEntity)
        }
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