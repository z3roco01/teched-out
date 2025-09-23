package z3roco01.techedout.data.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import z3roco01.techedout.block.TechedOutBlocks
import z3roco01.techedout.item.TechedOutItems

class TechedOutEnglishProvider(fabricDataOutput: FabricDataOutput): FabricLanguageProvider(fabricDataOutput, "en_us") {
    override fun generateTranslations(trans: TranslationBuilder) {
        // blocks
        trans.add(TechedOutBlocks.LOW_BATTERY, "Low Battery")
        trans.add(TechedOutBlocks.MIDDLE_BATTERY, "Middle Battery")
        trans.add(TechedOutBlocks.HIGH_BATTERY, "High Battery")
        trans.add(TechedOutBlocks.VERY_HIGH_BATTERY, "VH Battery")
        trans.add(TechedOutBlocks.ULTRA_HIGH_BATTERY, "UH Battery")

        trans.add(TechedOutBlocks.COPPER_CABLE, "Copper Cable")
        trans.add(TechedOutBlocks.GOLD_CABLE, "Gold Cable")
        trans.add(TechedOutBlocks.RED_GOLD_CABLE, "Red Gold Cable")
        trans.add(TechedOutBlocks.GREEN_GOLD_CABLE, "Green Gold Cable")
        trans.add(TechedOutBlocks.WHITE_GOLD_CABLE, "White Gold Cable")

        trans.add(TechedOutBlocks.LOW_ALLOY_SMELTER, "Low Alloy Smelter")
        trans.add(TechedOutBlocks.MIDDLE_ALLOY_SMELTER, "Middle Alloy Smelter")
        trans.add(TechedOutBlocks.HIGH_ALLOY_SMELTER, "High Alloy Smelter")

        trans.add(TechedOutBlocks.HIGH_ELECTROLYZER, "High Electrolyzer")
        trans.add(TechedOutBlocks.VERY_HIGH_ELECTROLYZER, "VH Electrolyzer")
        trans.add(TechedOutBlocks.ULTRA_HIGH_ELECTROLYZER, "UH Electrolyzer")

        // items
        trans.add(TechedOutItems.RED_GOLD_INGOT, "Red Gold Ingot")
        trans.add(TechedOutItems.RED_GOLD_NUGGET, "Red Gold Nugget")
        trans.add(TechedOutItems.RED_GOLD_DUST, "Red Gold Dust")
        trans.add(TechedOutItems.RED_GOLD_SMALL_DUST, "Red Gold Small Dust")

        trans.add(TechedOutItems.GREEN_GOLD_INGOT, "Green Gold Ingot")
        trans.add(TechedOutItems.GREEN_GOLD_NUGGET, "Green Gold Nugget")
        trans.add(TechedOutItems.GREEN_GOLD_DUST, "Green Gold Dust")
        trans.add(TechedOutItems.GREEN_GOLD_SMALL_DUST, "Green Gold Small Dust")

        trans.add(TechedOutItems.WHITE_GOLD_INGOT, "White Gold Ingot")
        trans.add(TechedOutItems.WHITE_GOLD_NUGGET, "White Gold Nugget")
        trans.add(TechedOutItems.WHITE_GOLD_DUST, "White Gold Dust")
        trans.add(TechedOutItems.WHITE_GOLD_SMALL_DUST, "White Gold Small Dust")

        trans.add(TechedOutItems.SILVER_INGOT, "Silver Ingot")
        trans.add(TechedOutItems.SILVER_NUGGET, "Silver Nugget")
        trans.add(TechedOutItems.SILVER_DUST, "Silver Dust")
        trans.add(TechedOutItems.SILVER_SMALL_DUST, "Silver Small Dust")

        trans.add(TechedOutItems.COAL_DUST, "Coal Dust")
        trans.add(TechedOutItems.COPPER_DUST, "Copper Dust")
        trans.add(TechedOutItems.IRON_DUST, "Iron Dust")
        trans.add(TechedOutItems.GOLD_DUST, "Gold Dust")

        trans.add(TechedOutItems.COAL_SMALL_DUST, "Coal Small Dust")
        trans.add(TechedOutItems.COPPER_SMALL_DUST, "Copper Small Dust")
        trans.add(TechedOutItems.IRON_SMALL_DUST, "Iron Small Dust")
        trans.add(TechedOutItems.GOLD_SMALL_DUST, "Gold Small Dust")

        trans.add(TechedOutItems.COPPER_ORE_DUST, "Copper Ore Dust")
        trans.add(TechedOutItems.IRON_ORE_DUST, "Iron Ore Dust")
        trans.add(TechedOutItems.GOLD_ORE_DUST, "Gold Ore Dust")
    }
}