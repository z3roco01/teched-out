package z3roco01.techedout.block.machines

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities

class MillBlock(tier: Tier): MachineBlock(tier) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T?>) =
        checkType(type, TechedOutBlockEntities.MILL, EnergyStorageBlockEntity::staticTick)
}