package z3roco01.techedout.block.entity

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.BatteryBlock
import z3roco01.techedout.block.TechedOutBlocks

/**
 * Handles registration of [BlockEntity]
 */
object TechedOutBlockEntities {
    // register the block entity types
    val tempBattery = register("battery",
        FabricBlockEntityTypeBuilder.create(::BatteryBlockEntity, TechedOutBlocks.tempBattery).build())

    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * registers a [BlockEntityType] and returns it
     * @param name the id of the type
     * @param blockEntityType the [BlockEntityType] to be registered
     * @return the BlockEntityType, so it can be registered and stored easier
     */
    fun <T : BlockEntityType<*>> register(name: String, blockEntityType: T): T =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, TechedOut.mkId(name), blockEntityType)
}