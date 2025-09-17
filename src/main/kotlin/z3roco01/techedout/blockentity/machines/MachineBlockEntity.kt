package z3roco01.techedout.blockentity.machines

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.ImplementedInventory

/**
 * A class that outlines a few utilites for machines ( excluding batteries ) and standardizes capacities and draw
 */
abstract class MachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState, private val tier: Tier):
    EnergyStorageBlockEntity(type, pos, state), ExtendedScreenHandlerFactory, ImplementedInventory {

    init {
        // set the permissions so it can only draw power, ever supply
        for(dir in Direction.entries) // loop over every direction and set its permission to insert only
            ioPermMap[dir] = IOPermission.INSERT
    }

    // machines may not cover every tier, unsure
    override fun getEnergyCapacity() = when(tier) {
        Tier.LOW        -> 2000L
        Tier.MIDDLE     -> 4000L
        Tier.HIGH       -> 8000L
        Tier.VERY_HIGH  -> 16000L
        Tier.ULTRA_HIGH -> 32000L
    }

    /**
     * A common transfer rate function, used in max insert AND extract, incase a machine allows extraction
     */
    fun getTransferRate(): Long = when(tier) {
        Tier.LOW        -> 32L
        Tier.MIDDLE     -> 48L
        Tier.HIGH       -> 64L
        Tier.VERY_HIGH  -> 128L
        Tier.ULTRA_HIGH -> 256L
    }

    override fun getMaxInsert()  = getTransferRate()
    override fun getMaxExtract() = getTransferRate()


    // write the block pos of this block so it can be retrieved on the client
    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(this.pos)
    }

    // set the screens name to the translation key of this block
    override fun getDisplayName() = Text.translatable(cachedState.block.translationKey)

    // need to store inventory data
    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Inventories.writeNbt(nbt, items)
    }

    // need to read inventory
    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, items)
    }
}