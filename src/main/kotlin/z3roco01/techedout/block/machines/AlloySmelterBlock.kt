package z3roco01.techedout.block.machines

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity.Companion.staticTick
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.blockentity.machines.AlloySmelterBlockEntity

class AlloySmelterBlock(tier: Tier): MachineBlock(tier) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = AlloySmelterBlockEntity(pos, state, tier)
}