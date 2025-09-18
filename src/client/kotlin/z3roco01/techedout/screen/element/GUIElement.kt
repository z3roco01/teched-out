package z3roco01.techedout.screen.element

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Element

/**
 * A common class for all GUI elements, handles focus, and has standard functions for rendering
 */
abstract class GUIElement(): Element {
    /**
     * Used to hold the current focused state
     */
    private var focused = false

    override fun setFocused(focused: Boolean) {
        this.focused = focused
    }

    override fun isFocused() = focused

    /**
     * Should be called in the screens `render` method , will render the element
     */
    abstract fun render(context: DrawContext, mouseX: Int, mouseY: Int)
}