package z3roco01.techedout.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.blockentity.CableBlockEntity
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity.Companion.staticTick
import z3roco01.techedout.blockentity.TechedOutBlockEntities

// TODO: MAKE IT LOOK LIKE A CABLE !!!
class CableBlock(private val tier: Tier): BlockWithEntity(Settings.create()) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = CableBlockEntity(tier, pos, state)

    // needed since by default BlockWithEntity makes it invisible
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    // sets up ticking so it will push to other blocks
    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T?>) =
        checkType(type, TechedOutBlockEntities.CABLE, EnergyStorageBlockEntity::staticTick)
}