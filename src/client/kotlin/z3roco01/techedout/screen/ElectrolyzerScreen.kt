package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut
import z3roco01.techedout.screen.element.EnergyBarElement

class ElectrolyzerScreen(handler: ElectrolyzerScreenHandler, playerInv: PlayerInventory, title: Text):
    BaseScreen<ElectrolyzerScreenHandler>(handler, playerInv, title, TechedOut.mkId("textures/gui/container/battery.png")) {
    val energyBar = EnergyBarElement(16, 18, handler.blockEntity)

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.drawForeground(context, mouseX, mouseY)
        energyBar.render(context, mouseX, mouseY)
    }
}