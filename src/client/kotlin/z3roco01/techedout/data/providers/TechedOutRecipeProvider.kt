package z3roco01.techedout.data.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.recipe.book.RecipeCategory
import z3roco01.techedout.item.TechedOutItems
import java.util.function.Consumer

class TechedOutRecipeProvider(fabricDataOutput: FabricDataOutput): FabricRecipeProvider(fabricDataOutput) {
    override fun generate(consumer: Consumer<RecipeJsonProvider>) {
        smallDusts2Dust(consumer, TechedOutItems.RED_GOLD_SMALL_DUST, TechedOutItems.RED_GOLD_DUST)
        smallDusts2Dust(consumer, TechedOutItems.GREEN_GOLD_SMALL_DUST, TechedOutItems.GREEN_GOLD_DUST)
        smallDusts2Dust(consumer, TechedOutItems.WHITE_GOLD_SMALL_DUST, TechedOutItems.WHITE_GOLD_DUST)
        smallDusts2Dust(consumer, TechedOutItems.SILVER_SMALL_DUST, TechedOutItems.SILVER_DUST)
        smallDusts2Dust(consumer, TechedOutItems.COAL_SMALL_DUST, TechedOutItems.COAL_DUST)
        smallDusts2Dust(consumer, TechedOutItems.COPPER_SMALL_DUST, TechedOutItems.COPPER_DUST)
        smallDusts2Dust(consumer, TechedOutItems.IRON_SMALL_DUST, TechedOutItems.IRON_DUST)
        smallDusts2Dust(consumer, TechedOutItems.GOLD_SMALL_DUST, TechedOutItems.GOLD_DUST)
    }

    /**
     * Creates a recipe to convert 4 small dusts to one large dust
     */
    private fun smallDusts2Dust(consumer: Consumer<RecipeJsonProvider>, smallDust: Item, dust: Item) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, dust)
            .input(smallDust, 4)
            .criterion(hasItem(smallDust), conditionsFromItem(smallDust))
            .offerTo(consumer)
    }
}