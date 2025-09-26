package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut
import z3roco01.techedout.screen.element.EnergyBarElement

class MillScreen(handler: MillScreenHandler, playerInv: PlayerInventory, title: Text):
    BaseScreen<MillScreenHandler>(handler, playerInv, title, TechedOut.mkId("textures/gui/container/mill.png")) {
    val energyBar = EnergyBarElement(12, 18, handler.blockEntity)

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.drawForeground(context, mouseX, mouseY)
        energyBar.render(context, mouseX, mouseY)
    }
}