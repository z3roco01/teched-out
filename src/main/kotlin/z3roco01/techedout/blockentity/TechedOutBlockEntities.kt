package z3roco01.techedout.blockentity

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import team.reborn.energy.api.EnergyStorage
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.TechedOutBlocks
import z3roco01.techedout.blockentity.machines.AlloySmelterBlockEntity
import z3roco01.techedout.blockentity.machines.BatteryBlockEntity


/**
 * Handles registration of [BlockEntity]
 */
object TechedOutBlockEntities {
    // register the block entity types
    val BATTERY = registerEnergyStorage("battery",
        FabricBlockEntityTypeBuilder.create(
            ::BatteryBlockEntity, TechedOutBlocks.LOW_BATTERY,
            TechedOutBlocks.MIDDLE_BATTERY, TechedOutBlocks.HIGH_BATTERY, TechedOutBlocks.VERY_HIGH_BATTERY,
            TechedOutBlocks.ULTRA_HIGH_BATTERY).build())
    val CABLE = TechedOutBlockEntities.registerEnergyStorage("cable", FabricBlockEntityTypeBuilder
        .create(::CableBlockEntity, TechedOutBlocks.COPPER_CABLE, TechedOutBlocks.GOLD_CABLE,
                TechedOutBlocks.HIGH_CABLE, TechedOutBlocks.VERY_HIGH_CABLE, TechedOutBlocks.ULTRA_HIGH_CABLE).build())
    val ALLOY_SMELTER = registerEnergyStorage("alloy_smelter", FabricBlockEntityTypeBuilder.create(
        ::AlloySmelterBlockEntity, TechedOutBlocks.LOW_ALLOY_SMELTER, TechedOutBlocks.MIDDLE_ALLOY_SMELTER,
        TechedOutBlocks.HIGH_ALLOY_SMELTER).build())


        /**
         * Dummy function to ensure everything is loaded in time
         */
        fun init() {}

        /**
         * Does everything that [TechedOutBlockEntities.register] does, and registers this block as an energy storage
         */
        fun registerEnergyStorage(
            name: String,
            blockEntityType: BlockEntityType<out EnergyStorageBlockEntity>
        ): BlockEntityType<EnergyStorageBlockEntity> {
            // register the energy storage
            EnergyStorage.SIDED.registerForBlockEntity({ blockEntity, direction ->
                blockEntity.energyStorage.getSideStorage(
                    direction
                )
            }, blockEntityType)
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