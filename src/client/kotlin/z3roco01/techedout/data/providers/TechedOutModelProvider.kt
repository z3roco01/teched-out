package z3roco01.techedout.data.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import z3roco01.techedout.item.TechedOutItems

class TechedOutModelProvider(output: FabricDataOutput): FabricModelProvider(output) {
    override fun generateBlockStateModels(blocks: BlockStateModelGenerator) {
    }

    override fun generateItemModels(items: ItemModelGenerator) {
        items.register(TechedOutItems.RED_GOLD_INGOT, Models.GENERATED)
        items.register(TechedOutItems.RED_GOLD_NUGGET, Models.GENERATED)
        items.register(TechedOutItems.RED_GOLD_DUST, Models.GENERATED)
        items.register(TechedOutItems.RED_GOLD_SMALL_DUST, Models.GENERATED)

        items.register(TechedOutItems.GREEN_GOLD_INGOT, Models.GENERATED)
        items.register(TechedOutItems.GREEN_GOLD_NUGGET, Models.GENERATED)
        items.register(TechedOutItems.GREEN_GOLD_DUST, Models.GENERATED)
        items.register(TechedOutItems.GREEN_GOLD_SMALL_DUST, Models.GENERATED)

        items.register(TechedOutItems.WHITE_GOLD_INGOT, Models.GENERATED)
        items.register(TechedOutItems.WHITE_GOLD_NUGGET, Models.GENERATED)
        items.register(TechedOutItems.WHITE_GOLD_DUST, Models.GENERATED)
        items.register(TechedOutItems.WHITE_GOLD_SMALL_DUST, Models.GENERATED)

        items.register(TechedOutItems.SILVER_INGOT, Models.GENERATED)
        items.register(TechedOutItems.SILVER_NUGGET, Models.GENERATED)
        items.register(TechedOutItems.SILVER_DUST, Models.GENERATED)
        items.register(TechedOutItems.SILVER_SMALL_DUST, Models.GENERATED)

        items.register(TechedOutItems.COAL_DUST, Models.GENERATED)
        items.register(TechedOutItems.COPPER_DUST, Models.GENERATED)
        items.register(TechedOutItems.IRON_DUST, Models.GENERATED)
        items.register(TechedOutItems.GOLD_DUST, Models.GENERATED)

        items.register(TechedOutItems.COAL_SMALL_DUST, Models.GENERATED)
        items.register(TechedOutItems.COPPER_SMALL_DUST, Models.GENERATED)
        items.register(TechedOutItems.IRON_SMALL_DUST, Models.GENERATED)
        items.register(TechedOutItems.GOLD_SMALL_DUST, Models.GENERATED)

        items.register(TechedOutItems.COAL_ORE_DUST, Models.GENERATED)
        items.register(TechedOutItems.COPPER_ORE_DUST, Models.GENERATED)
        items.register(TechedOutItems.IRON_ORE_DUST, Models.GENERATED)
        items.register(TechedOutItems.GOLD_ORE_DUST, Models.GENERATED)
    }
}