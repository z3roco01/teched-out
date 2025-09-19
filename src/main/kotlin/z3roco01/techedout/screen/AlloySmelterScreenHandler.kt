package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.slot.Slot
import z3roco01.techedout.blockentity.machines.AlloySmelterBlockEntity
import z3roco01.techedout.screen.slot.OutputSlot

class AlloySmelterScreenHandler(syncId: Int, playerInventory: PlayerInventory, blockEntity: AlloySmelterBlockEntity):
    BaseScreenHandler(TechedOutScreenHandlers.ALLOY_SMELTER, syncId, playerInventory, blockEntity) {
    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf):
            this(syncId, playerInventory, playerInventory.player.world.getBlockEntity(buf.readBlockPos()) as AlloySmelterBlockEntity)

    init {
        // add slots for the inputs and output
        addSlot(Slot(blockEntity, 0, 58, 26))
        addSlot(Slot(blockEntity, 1, 80, 17))
        addSlot(Slot(blockEntity, 2, 102, 26))
        // one output, TODO: MAKE 2 OUTPUTS MAYBE
        addSlot(OutputSlot(blockEntity, 3, 80, 62))
    }
}