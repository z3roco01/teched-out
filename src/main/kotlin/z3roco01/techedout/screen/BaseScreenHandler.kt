package z3roco01.techedout.screen

import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.machines.BatteryBlockEntity

/**
 * Does common logic ( like setting up slots ) that is common among all screen handlers
 */
abstract class BaseScreenHandler(type: ScreenHandlerType<*>, syncId: Int, protected val playerInventory: PlayerInventory, val blockEntity: EnergyStorageBlockEntity):
    ScreenHandler(type, syncId) {

    init {
        // add the hotbar and inventory slots
        addPlayerInventory(playerInventory)
    }

    // handles the qucik moving ( shift + click ) of items into this blocks inventory
    override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot2 = slots[slot]
        if (slot2.hasStack()) {
            val itemStack2 = slot2.stack
            itemStack = itemStack2.copy()
            if (if (slot < 27) !this.insertItem(itemStack2, 27, slots.size, true) else !this.insertItem(
                    itemStack2,
                    0,
                    27,
                    false
                )
            ) {
                return ItemStack.EMPTY
            }
            if (itemStack2.isEmpty) {
                slot2.stack = ItemStack.EMPTY
            } else {
                slot2.markDirty()
            }
        }
        return itemStack!!
    }

    // returns if a player can open this inventory
    override fun canUse(player: PlayerEntity) = true

    /**
     * adds all the required slots for the players inventory, including hotbar.
     * should be called in the constructor of a sub class
     */
    protected fun addPlayerInventory(playerInv: PlayerInventory) {
        // add every inventory slot
        for (i in 0..2) {
            for (l in 0..8) {
                this.addSlot(Slot(playerInv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))
            }
        }

        addHotbar(playerInv)
    }

    /**
     * adds the slots for the players hotbar
     */
    protected fun addHotbar(playerInv: PlayerInventory) {
        for (i in 0..8) {
            this.addSlot(Slot(playerInv, i, 8 + i * 18, 142))
        }
    }
}