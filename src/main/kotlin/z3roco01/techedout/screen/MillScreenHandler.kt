package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.slot.Slot
import z3roco01.techedout.blockentity.machines.MillBlockEntity
import z3roco01.techedout.screen.slot.OutputSlot

class MillScreenHandler(syncId: Int, playerInventory: PlayerInventory, blockEntity: MillBlockEntity):
    BaseScreenHandler(TechedOutScreenHandlers.MILL, syncId, playerInventory, blockEntity){
    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf): this(syncId, playerInventory,
        playerInventory.player.world.getBlockEntity(buf.readBlockPos()) as MillBlockEntity)

    init {
        addSlot(Slot(blockEntity, 0, 44, 30))
        addSlot(OutputSlot(blockEntity, 1, 98, 30))
        addSlot(OutputSlot(blockEntity, 2, 116, 30))
        addSlot(OutputSlot(blockEntity, 1,  107, 48))
    }
}