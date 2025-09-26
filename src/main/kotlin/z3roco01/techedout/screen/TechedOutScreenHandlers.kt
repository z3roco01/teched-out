package z3roco01.techedout.screen

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import z3roco01.techedout.TechedOut

object TechedOutScreenHandlers {
    val BATTERY_TYPE = register("battery", ExtendedScreenHandlerType(::BatteryScreenHandler))
            as ScreenHandlerType<BatteryScreenHandler>
    val ALLOY_SMELTER = register("alloy_smelter", ExtendedScreenHandlerType(::AlloySmelterScreenHandler))
            as ScreenHandlerType<AlloySmelterScreenHandler>
    val ELECTROLYZER = register("electrolyzer", ExtendedScreenHandlerType(::ElectrolyzerScreenHandler))
            as ScreenHandlerType<ElectrolyzerScreenHandler>
    val MILL = register("mill", ExtendedScreenHandlerType(::MillScreenHandler))
            as ScreenHandlerType<MillScreenHandler>

    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * registers a [ScreenHandlerType] and returns it
     * @param name the id of the type
     * @param type the [ScreenHandlerType] to be registered
     * @return the BlockEntityType, so it can be registered and stored easier
     */
    private fun register(name: String, type: ScreenHandlerType<*>): ScreenHandlerType<*> =
        Registry.register(Registries.SCREEN_HANDLER, TechedOut.mkId(name), type)
}