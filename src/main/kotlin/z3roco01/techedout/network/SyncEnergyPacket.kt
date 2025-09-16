package z3roco01.techedout.network

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.TechedOut

/**
 * Server to client payload, updates the energy value of a block on the client side so guis and stuff works
 * @param amount the amount of energy now stored here
 * @param pos the position of the block
 */
data class SyncEnergyPacket(val amount: Long, val pos: BlockPos) {

    /**
     * Creates a packet in a PacketByteBuffer so it can be sent
     */
    fun toBuf(): PacketByteBuf {
        // make the buffer
        val buf = PacketByteBufs.create()
        // write in the amount first, then the position
        buf.writeLong(amount)
        buf.writeBlockPos(pos)

        return buf
    }

    companion object {
        /**
         * Identifier used as the channel identifier
         */
        val ID = TechedOut.mkId("sync_energy")

        /**
         * Takes in a PacketByteBuf from the network and parses it into a packet
         */
        fun fromBuf(buf: PacketByteBuf): SyncEnergyPacket {
            // read the vales in the same order
            val amount = buf.readLong()
            val pos = buf.readBlockPos()

            // create and return a new packet
            return SyncEnergyPacket(amount, pos)
        }
    }
}