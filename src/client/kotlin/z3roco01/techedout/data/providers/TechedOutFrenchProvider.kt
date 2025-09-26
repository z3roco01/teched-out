package z3roco01.techedout.data.providers

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import z3roco01.techedout.block.TechedOutBlocks
import z3roco01.techedout.item.TechedOutItems

class TechedOutFrenchProvider(fabricDataOutput: FabricDataOutput): FabricLanguageProvider(fabricDataOutput, "fr_fr") {
    override fun generateTranslations(trans: TranslationBuilder) {
        // blocks
        trans.add(TechedOutBlocks.LOW_BATTERY, "Low Battery")
        trans.add(TechedOutBlocks.MIDDLE_BATTERY, "Middle Battery")
        trans.add(TechedOutBlocks.HIGH_BATTERY, "High Battery")
        trans.add(TechedOutBlocks.VERY_HIGH_BATTERY, "VH Battery")
        trans.add(TechedOutBlocks.ULTRA_HIGH_BATTERY, "UH Battery")

        trans.add(TechedOutBlocks.COPPER_CABLE, "Câble du Cuivre")
        trans.add(TechedOutBlocks.GOLD_CABLE, "Câble d'Or")
        trans.add(TechedOutBlocks.RED_GOLD_CABLE, "Câble d'Or Rouge")
        trans.add(TechedOutBlocks.GREEN_GOLD_CABLE, "Câble d'Or Vert")
        trans.add(TechedOutBlocks.WHITE_GOLD_CABLE, "Câble d'Or Blanc")

        trans.add(TechedOutBlocks.LOW_ALLOY_SMELTER, "Low Alloy Smelter")
        trans.add(TechedOutBlocks.MIDDLE_ALLOY_SMELTER, "Middle Alloy Smelter")
        trans.add(TechedOutBlocks.HIGH_ALLOY_SMELTER, "High Alloy Smelter")

        trans.add(TechedOutBlocks.HIGH_ELECTROLYZER, "High Electrolyzer")
        trans.add(TechedOutBlocks.VERY_HIGH_ELECTROLYZER, "VH Electrolyzer")
        trans.add(TechedOutBlocks.ULTRA_HIGH_ELECTROLYZER, "UH Electrolyzer")

        // items
        trans.add(TechedOutItems.RED_GOLD_INGOT,      "Lingot d'Or Rouge")
        trans.add(TechedOutItems.RED_GOLD_NUGGET,     "Pépite d'Or Rouge")
        trans.add(TechedOutItems.RED_GOLD_DUST,       "Poussière d'Or Rouge")
        trans.add(TechedOutItems.RED_GOLD_SMALL_DUST, "Petit Poussière d'Or Rouge")

        trans.add(TechedOutItems.GREEN_GOLD_INGOT,      "Lingot d'Or Vert")
        trans.add(TechedOutItems.GREEN_GOLD_NUGGET,     "Pépite d'Or Vert")
        trans.add(TechedOutItems.GREEN_GOLD_DUST,       "Poussière d'Or Vert")
        trans.add(TechedOutItems.GREEN_GOLD_SMALL_DUST, "Petit Poussière d'Or Vert")

        trans.add(TechedOutItems.WHITE_GOLD_INGOT,      "Lingot d'Or Blanc")
        trans.add(TechedOutItems.WHITE_GOLD_NUGGET,     "Pépite d'Or Blanc")
        trans.add(TechedOutItems.WHITE_GOLD_DUST,       "Poussière d'Or Blanc")
        trans.add(TechedOutItems.WHITE_GOLD_SMALL_DUST, "Petit Poussière d'Or Blanc")

        trans.add(TechedOutItems.SILVER_INGOT,      "Lingot d'Argent")
        trans.add(TechedOutItems.SILVER_NUGGET,     "Pépite d'Argent")
        trans.add(TechedOutItems.SILVER_DUST,       "Poussière d'Argent")
        trans.add(TechedOutItems.SILVER_SMALL_DUST, "Petit Poussière d'Argent")

        trans.add(TechedOutItems.COAL_DUST,   "Poussière de Charbon")
        trans.add(TechedOutItems.COPPER_DUST, "Poussière de Cuivre")
        trans.add(TechedOutItems.IRON_DUST,   "Poussière de Fer")
        trans.add(TechedOutItems.GOLD_DUST,   "Poussière d'Or")

        trans.add(TechedOutItems.COAL_SMALL_DUST,   "Petit Poussière de Charbon")
        trans.add(TechedOutItems.COPPER_SMALL_DUST, "Petit Poussière de Cuivre")
        trans.add(TechedOutItems.IRON_SMALL_DUST,   "Petit Poussière de Fer")
        trans.add(TechedOutItems.GOLD_SMALL_DUST,   "Petit Poussière d'Or")

        trans.add(TechedOutItems.COPPER_ORE_DUST, "Poussière de Minerai de Cuivre")
        trans.add(TechedOutItems.IRON_ORE_DUST,   "Poussière de Minerai de Fer")
        trans.add(TechedOutItems.GOLD_ORE_DUST,   "Poussière de Minerai d'Or")
    }
}