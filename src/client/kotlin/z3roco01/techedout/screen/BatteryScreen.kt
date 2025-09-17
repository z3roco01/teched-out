package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut

class BatteryScreen(handler: BatteryScreenHandler, playerInv: PlayerInventory, title: Text):
    BaseScreen<BatteryScreenHandler>(handler, playerInv, title, TechedOut.mkId("textures/gui/container/battery.png")) {
    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(context, delta, mouseX, mouseY)

        // temp way to show power amount
        context.drawText(textRenderer, handler.blockEntity.getEnergy().toString(), centerX+50, centerY+20, (0xFF0000FFu).toInt(), true)
    }
}