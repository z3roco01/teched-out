package z3roco01.techedout.blockentity.machines

import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.Tier
import z3roco01.techedout.blockentity.EnergyStorageBlockEntity
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.ElectrolyzeRecipe
import z3roco01.techedout.screen.ElectrolyzerScreenHandler
import java.security.IdentityScope
import java.util.Optional

class ElectrolyzerBlockEntity(pos: BlockPos, state: BlockState, tier: Tier):
    CraftingBlockEntity<ElectrolyzeRecipe>(TechedOutBlockEntities.ELECTROLYZER, pos, state,
        tier, ElectrolyzeRecipe.Type.INSTANCE) {
        constructor(pos: BlockPos, state: BlockState): this(pos, state, Tier.HIGH)

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity) =
        ElectrolyzerScreenHandler(syncId, playerInventory, this)

    override val items = DefaultedList.ofSize(6, ItemStack.EMPTY)
    override val outputRange = 2..5
    override val inputRange = 0..1
}