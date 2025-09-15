package z3roco01.techedout.screen

import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut

object TechedOutScreens {
    /**
     * Registers every screen handler
     */
    fun reigster() {
        HandledScreens.register(TechedOutScreenHandlers.BATTERY_TYPE, ::BatteryScreen)
    }
}