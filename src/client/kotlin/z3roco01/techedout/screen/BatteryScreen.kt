package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut
import z3roco01.techedout.TechedOutClient

class BatteryScreen(handler: BatteryScreenHandler, playerInv: PlayerInventory, title: Text):
    HandledScreen<BatteryScreenHandler>(handler, playerInv, title) {
    val BG_TEXTURE = TechedOut.mkId("textures/gui/container/battery.png")

    override fun init() {
        super.init()
        // ensure title is centred vertically
        this.titleX = (this.backgroundWidth - textRenderer.getWidth(this.title)) / 2
        TechedOutClient.logger.info(handler.blockEntity.energyStorage.amount.toString())
    }

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        // calculate the middle screen coordinates on the x and y
        val centerX = (this.width - this.backgroundWidth) / 2
        val centerY = (this.height - this.backgroundHeight) / 2

        // render the background texture to the screen with the center coords
        context.drawTexture(BG_TEXTURE, centerX, centerY, 0, 0, this.backgroundWidth, this.backgroundHeight)    }
}