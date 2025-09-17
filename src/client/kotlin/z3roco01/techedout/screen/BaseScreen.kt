package z3roco01.techedout.screen

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.Identifier

/**
 * A helper class that is inherited by all screens in this mod, adds some basic helper functions like slot creation
 * @param bgText the identifier for the background texture
 */
abstract class BaseScreen<T: ScreenHandler>(handler: T, protected val playerInv: PlayerInventory, title: Text, protected val bgText: Identifier):
    HandledScreen<T>(handler, playerInv, title) {
    // store centers so they dont have to be calculated each draw call
    protected var centerX = 0
    protected var centerY = 0

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
        context.drawTexture(bgText, centerX, centerY, 0, 0, this.backgroundWidth, this.backgroundHeight)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)
        drawMouseoverTooltip(context, mouseX, mouseY)
    }
}