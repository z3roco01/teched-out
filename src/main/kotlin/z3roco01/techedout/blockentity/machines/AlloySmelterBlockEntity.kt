package z3roco01.techedout.blockentity.machines

import com.mojang.datafixers.TypeRewriteRule
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.AlloyingRecipe
import z3roco01.techedout.screen.AlloySmelterScreenHandler

class AlloySmelterBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    CraftingBlockEntity<AlloyingRecipe>(TechedOutBlockEntities.ALLOY_SMELTER, pos, state, tier, AlloyingRecipe.Type.INSTANCE) {
    constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.LOW)

    override val items = DefaultedList.ofSize(4, ItemStack.EMPTY)
    override val outputRange = 3..3
    override val inputRange = 0..2

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler =
        AlloySmelterScreenHandler(syncId, playerInventory, this)

}