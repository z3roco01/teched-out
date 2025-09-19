package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.slot.Slot
import z3roco01.techedout.blockentity.machines.ElectrolyzerBlockEntity
import z3roco01.techedout.screen.slot.OutputSlot

class ElectrolyzerScreenHandler(syncId: Int, playerInventory: PlayerInventory, blockEntity: ElectrolyzerBlockEntity):
    BaseScreenHandler(TechedOutScreenHandlers.ELECTROLYZER, syncId, playerInventory, blockEntity) {
    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf):
            this(syncId, playerInventory, playerInventory.player.world.getBlockEntity(buf.readBlockPos()) as ElectrolyzerBlockEntity)

    init {
        // add input slots
        addSlot(Slot(blockEntity, 0, 44, 30))
        addSlot(Slot(blockEntity, 1, 44, 48))

        // add outputs
        addSlot(OutputSlot(blockEntity, 2, 96, 30))
        addSlot(OutputSlot(blockEntity, 3, 114, 30))
        addSlot(OutputSlot(blockEntity, 4, 96, 48))
        addSlot(OutputSlot(blockEntity, 5, 114, 48))
    }
}