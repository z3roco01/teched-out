package z3roco01.techedout.screen.slot

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

/**
 * Create an inventory slot that cannot be inserted into
 */
class OutputSlot(inventory: Inventory, index: Int, x: Int, y: Int): Slot(inventory, index, x, y) {
    // players can never insert into the output
    override fun canInsert(stack: ItemStack) = false
}