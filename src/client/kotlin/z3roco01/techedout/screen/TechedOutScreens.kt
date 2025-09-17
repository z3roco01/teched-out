package z3roco01.techedout.screen

import net.minecraft.client.gui.screen.ingame.HandledScreens

object TechedOutScreens {
    /**
     * Registers every screen handler
     */
    fun reigster() {
        HandledScreens.register(TechedOutScreenHandlers.BATTERY_TYPE, ::BatteryScreen)
        HandledScreens.register(TechedOutScreenHandlers.ALLOY_SMELTER, ::AlloySmelterScreen)
    }
}