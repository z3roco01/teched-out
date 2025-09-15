package z3roco01.techedout.block.entity

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import team.reborn.energy.api.EnergyStorage
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.TechedOutBlocks


/**
 * Handles registration of [BlockEntity]
 */
object TechedOutBlockEntities {
    // register the block entity types
    val tempBattery = registerEnergyStorage("battery",
        FabricBlockEntityTypeBuilder.create(::BatteryBlockEntity, TechedOutBlocks.LOW_BATTERY,
            TechedOutBlocks.MIDDLE_BATTERY, TechedOutBlocks.HIGH_BATTERY).build())


    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * Does everything that [TechedOutBlockEntities.register] does, and registers this block as an energy storage
     */
    fun registerEnergyStorage(name: String, blockEntityType: BlockEntityType<out EnergyStorageBlockEntity>): BlockEntityType<EnergyStorageBlockEntity> {
        // register the energy storage
        EnergyStorage.SIDED.registerForBlockEntity({blockEntity, direction -> blockEntity.energyStorage.getSideStorage(direction)}, blockEntityType)
        // actually register the blockentitytype
        return register(name, blockEntityType) as BlockEntityType<EnergyStorageBlockEntity>
    }

    /**
     * registers a [BlockEntityType] and returns it
     * @param name the id of the type
     * @param blockEntityType the [BlockEntityType] to be registered
     * @return the BlockEntityType, so it can be registered and stored easier
     */
    fun <T : BlockEntityType<*>> register(name: String, blockEntityType: T): T =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, TechedOut.mkId(name), blockEntityType)
}