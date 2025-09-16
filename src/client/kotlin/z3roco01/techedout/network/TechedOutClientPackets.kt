package z3roco01.techedout.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity

/**
 * Registers packet listeners on the client side
 */
object TechedOutClientPackets {
    fun register() {
        // register energy sync listener
        ClientPlayNetworking.registerGlobalReceiver(SyncEnergyPacket.ID) { client, handler, buf, responseSender ->
            // parse the buffer as a SyncEnergyPacket
            val packet = SyncEnergyPacket.fromBuf(buf)

            val world = client.world

            // cant do anything if the world is null ( should never happen )
            if(world == null) return@registerGlobalReceiver

            // now set the energy on the block from the packet
            val blockEntity = world.getBlockEntity(packet.pos)

            // if its somehow not energy storage, then dont set energy
            if(blockEntity !is EnergyStorageBlockEntity) return@registerGlobalReceiver

            // set the energy amount without triggering any syncs
            blockEntity.energyStorage.amount = packet.amount
        }
    }
}