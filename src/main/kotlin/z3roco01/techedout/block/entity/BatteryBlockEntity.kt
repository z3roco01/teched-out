package z3roco01.techedout.block.entity

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier
import z3roco01.techedout.screen.BatteryScreenHandler

/**
 * A block entity for the [z3roco01.techedout.block.BatteryBlock]
 * @param tier the tier of this battery, determines the specs
 */
class BatteryBlockEntity(private val tier: Tier, pos: BlockPos, state: BlockState):
    EnergyStorageBlockEntity(TechedOutBlockEntities.tempBattery, pos, state), ExtendedScreenHandlerFactory {
    // constructor for registration
    constructor(pos: BlockPos, state: BlockState): this(Tier.LOW, pos, state)

    override fun getEnergyCapacity() = when(tier) {
        Tier.LOW -> 64000L // 16 coal
        Tier.MIDDLE -> 512000L // 128 coal
        Tier.HIGH -> 1024000L // 256 coal
    }

    override fun getMaxInsert() = when(tier) {
        Tier.LOW -> 187L // ~quater of a plank a tick
        Tier.MIDDLE -> 375L // ~half a plank a tick
        Tier.HIGH -> 1075L // ~1.5 planks a tick
    }

    override fun getMaxExtract() = when(tier) {
        Tier.LOW -> 187L // ~quater of a plank a tick
        Tier.MIDDLE -> 375L // ~half a plank a tick
        Tier.HIGH -> 1075L // ~1.5 planks a tick
    }

    // write the block pos of this block so it can be retrieved on the client
    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(this.pos)
    }

    // set the screens name to the translation key of this block
    override fun getDisplayName() = Text.translatable(cachedState.block.translationKey)

    // creates a BatteryScreenHandler with the passed parameters
    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        BatteryScreenHandler(syncId, playerInventory, this)
}