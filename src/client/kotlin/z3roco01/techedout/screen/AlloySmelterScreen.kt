package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut

class AlloySmelterScreen(handler: AlloySmelterScreenHandler, playerInv: PlayerInventory, title: Text):
    BaseScreen<AlloySmelterScreenHandler>(handler, playerInv, title, TechedOut.mkId("textures/gui/container/alloy_smelter.png")) {
}