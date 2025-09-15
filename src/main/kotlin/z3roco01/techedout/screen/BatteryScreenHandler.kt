package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import z3roco01.techedout.block.entity.BatteryBlockEntity

class BatteryScreenHandler(syncId: Int, private val playerInventory: PlayerInventory, val blockEntity: BatteryBlockEntity):
    ScreenHandler(TechedOutScreenHandlers.BATTERY_TYPE, syncId) {
    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf):
            this(syncId, playerInventory, playerInventory.player.world.getBlockEntity(buf.readBlockPos()) as BatteryBlockEntity)

    override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
        return ItemStack.EMPTY
    }

    override fun canUse(player: PlayerEntity) = true
}