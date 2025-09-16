package z3roco01.techedout.block

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.block.entity.BatteryBlockEntity
import z3roco01.techedout.block.entity.EnergyStorageBlockEntity

/**
 * A class to make simple battery blocks
 * @param tier the [Tier] of the battery, used to determine the battery's specs
 */
class BatteryBlock(private val tier: Tier) : BlockWithEntity(Settings.create()) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BatteryBlockEntity(tier, pos, state)

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

        player.sendMessage(Text.of("${blockEntity.energyStorage.amount}/${blockEntity.energyStorage.capacity}"))
        // open the screen for the player
        player.openHandledScreen(screenHandlerFactory)

        return ActionResult.SUCCESS
    }
}