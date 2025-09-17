package z3roco01.techedout.blockentity

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList


/**
 * A simple `Inventory` implementation with only default methods + an item list getter.
 * adapted from https://wiki.fabricmc.net/tutorial:inventory
 * @author Juuz, z3roco01
 */
interface ImplementedInventory : Inventory {
    /**
     * Retrieves the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    val items: DefaultedList<ItemStack>

    /**
     * Returns the inventory size.
     */
    override fun size() = items.size

    /**
     * Checks if the inventory is empty.
     * @return true if this inventory has only empty stacks, false otherwise.
     */
    override fun isEmpty(): Boolean {
        for (i in 0..<size()) {
            val stack = getStack(i)
            if(!stack.isEmpty)
                return false
        }
        return true
    }

    /**
     * Retrieves the item in the slot.
     */
    override fun getStack(slot: Int) = items[slot]

    /**
     * Removes items from an inventory slot.
     * @param slot  The slot to remove from.
     * @param count How many items to remove. If there are less items in the slot than what are requested,
     * takes all items in that slot.
     */
    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, count)
        if(!result.isEmpty)
            markDirty()

        return result
    }

    /**
     * Removes all items from an inventory slot.
     * @param slot The slot to remove from.
     */
    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(items, slot)

    /**
     * Replaces the current stack in an inventory slot with the provided stack.
     * @param slot  The inventory slot of which to replace the itemstack.
     * @param stack The replacing itemstack. If the stack is too big for
     * this inventory ([Inventory.getMaxCountPerStack]),
     * it gets resized to this inventory's maximum amount.
     */
    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if(stack.count > stack.maxCount)
            stack.count = stack.maxCount
    }

    /**
     * Clears the inventory.
     */
    override fun clear() {
        items.clear()
    }

    /**
     * @return true if the player can use the inventory, false otherwise.
     */
    override fun canPlayerUse(player: PlayerEntity) = true
}