package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import z3roco01.techedout.TechedOut

class BatteryScreen(handler: BatteryScreenHandler, private val playerInv: PlayerInventory, title: Text):
    HandledScreen<BatteryScreenHandler>(handler, playerInv, title) {
    val BG_TEXTURE = TechedOut.mkId("textures/gui/container/battery.png")
    // store centers so they dont have to be calculated each draw call
    private var centerX = 0
    private var centerY = 0

    override fun init() {
        super.init()
        // ensure title is centred vertically
        this.titleX = (this.backgroundWidth - textRenderer.getWidth(this.title)) / 2
        // calculate the middle screen coordinates on the x and y to draw bg in the right spot
        centerX = (this.width - this.backgroundWidth) / 2
        centerY = (this.height - this.backgroundHeight) / 2
    }

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        // render the background texture to the screen with the center coords
        context.drawTexture(BG_TEXTURE, centerX, centerY, 0, 0, this.backgroundWidth, this.backgroundHeight)

        // temp way to show power amount
        context.drawText(textRenderer, handler.blockEntity.getEnergy().toString(), centerX+50, centerY+20, (0xFF0000FFu).toInt(), true)
    }
}