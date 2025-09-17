package z3roco01.techedout.block.machines

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity.Companion.staticTick
import z3roco01.techedout.blockentity.TechedOutBlockEntities

/**
 * Handles things common amongst all machines ( screen opening, blockentity, etc )
 * if you want the block to push power you need to register a ticker
 * @param tier the tier of this block, doesnt always need to be used, but prob will be
 */
abstract class MachineBlock(protected val tier: Tier, settings: Settings):
    BlockWithEntity(settings) {
    // easier constructor, used by like everything
    constructor(tier: Tier): this(tier, Settings.create())

    // needed since by default BlockWithEntity makes it invisible
    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult):
            ActionResult {
        // do not run on client
        if(world.isClient) return ActionResult.PASS

        // get this blocks BlockEntity
        val blockEntity = world.getBlockEntity(pos) as EnergyStorageBlockEntity
        // cast the block entity to screen handler factory so it can be used to create a screen handle
        val screenHandlerFactory = (blockEntity as ExtendedScreenHandlerFactory)

        // open the screen for the player
        player.openHandledScreen(screenHandlerFactory)

        return ActionResult.SUCCESS
    }
}