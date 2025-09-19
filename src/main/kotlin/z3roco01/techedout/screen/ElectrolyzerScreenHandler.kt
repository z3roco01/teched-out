package z3roco01.techedout.screen

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import z3roco01.techedout.blockentity.machines.ElectrolyzerBlockEntity

class ElectrolyzerScreenHandler(syncId: Int, playerInventory: PlayerInventory, blockEntity: ElectrolyzerBlockEntity):
    BaseScreenHandler(TechedOutScreenHandlers.ELECTROLYZER, syncId, playerInventory, blockEntity) {
    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf):
            this(syncId, playerInventory, playerInventory.player.world.getBlockEntity(buf.readBlockPos()) as ElectrolyzerBlockEntity)
}