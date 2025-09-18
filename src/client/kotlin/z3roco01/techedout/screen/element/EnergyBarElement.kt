package z3roco01.techedout.screen.element

import net.minecraft.client.gui.DrawContext
import z3roco01.techedout.TechedOut
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import kotlin.math.round

/**
 * A GUI element that displays the current amount of energy as an energy bar, will also show the exact amount in a tooltip
 * @param x the x coordinate of the top left pixel
 * @param y the y coordinate of the top left pixel
 * @param blockEntity the block entity which is storing the energy
 */
class EnergyBarElement(val x: Int, val y: Int, val blockEntity: EnergyStorageBlockEntity): GUIElement() {
    val BG_TEXT = TechedOut.mkId("textures/gui/container/sprite/energy_bar_bg.png")
    val BAR_TEXT = TechedOut.mkId("textures/gui/container/sprite/energy_bar.png")

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        // draw the background
        context.drawTexture(BG_TEXT, x, y, 0f, 0f, 18, 53, 18, 53)
        // calculate the hight of the bar by dividing the current energy by capacity and multiplying by texture height ( basically percentage )
        val barHeight = round((blockEntity.getEnergy().toFloat()/blockEntity.getEnergyCapacity().toFloat()) * 53f).toInt()

        context.drawTexture(BAR_TEXT, x, y + 53 - barHeight, 0f, 53f - barHeight.toFloat(), 18, barHeight, 18, 53)
    }
}