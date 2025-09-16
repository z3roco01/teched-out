package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.TechedOutBlockEntities

class AlloySmelterBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    MachineBlockEntity(TechedOutBlockEntities.ALLOY_SMELTER, pos, state, tier) {
    constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)


}