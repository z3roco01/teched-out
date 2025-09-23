package z3roco01.techedout.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut

/**
 * Holds all the registered items
 */
object TechedOutItems {
    // main alloys
    val RED_GOLD_INGOT      = register("red_gold_ingot", Item(Item.Settings()))
    val RED_GOLD_NUGGET     = register("red_gold_nugget", Item(Item.Settings()))
    val RED_GOLD_DUST       = register("red_gold_dust", Item(Item.Settings()))
    val RED_GOLD_SMALL_DUST = register("red_gold_small_dust", Item(Item.Settings()))

    val GREEN_GOLD_INGOT      = register("green_gold_ingot", Item(Item.Settings()))
    val GREEN_GOLD_NUGGET     = register("green_gold_nugget", Item(Item.Settings()))
    val GREEN_GOLD_DUST       = register("green_gold_dust", Item(Item.Settings()))
    val GREEN_GOLD_SMALL_DUST = register("green_gold_small_dust", Item(Item.Settings()))

    val WHITE_GOLD_INGOT      = register("white_gold_ingot", Item(Item.Settings()))
    val WHITE_GOLD_NUGGET     = register("white_gold_nugget", Item(Item.Settings()))
    val WHITE_GOLD_DUST       = register("white_gold_dust", Item(Item.Settings()))
    val WHITE_GOLD_SMALL_DUST = register("white_gold_small_dust", Item(Item.Settings()))
    // extra metals
    val SILVER_INGOT            = register("silver_ingot", Item(Item.Settings()))
    val SILVER_NUGGET           = register("silver_nugget", Item(Item.Settings()))
    val SILVER_DUST             = register("silver_dust", Item(Item.Settings()))
    val SILVER_SMALL_DUST       = register("silver_small_dust", Item(Item.Settings()))
    // dusts for vanilla ores
    val COAL_DUST   = register("coal_dust", Item(Item.Settings()))
    val COPPER_DUST = register("copper_dust", Item(Item.Settings()))
    val IRON_DUST   = register("iron_dust", Item(Item.Settings()))
    val GOLD_DUST   = register("gold_dust", Item(Item.Settings()))
    // smaller dusts
    val COAL_SMALL_DUST   = register("coal_small_dust", Item(Item.Settings()))
    val COPPER_SMALL_DUST = register("copper_small_dust", Item(Item.Settings()))
    val IRON_SMALL_DUST   = register("iron_small_dust", Item(Item.Settings()))
    val GOLD_SMALL_DUST   = register("gold_small_dust", Item(Item.Settings()))
    // ore dusts for better rates and refining
    val COAL_ORE_DUST   = register("coal_ore_dust", Item(Item.Settings()))
    val COPPER_ORE_DUST = register("copper_ore_dust", Item(Item.Settings()))
    val IRON_ORE_DUST   = register("iron_ore_dust", Item(Item.Settings()))
    val GOLD_ORE_DUST   = register("gold_ore_dust", Item(Item.Settings()))

    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * Registers an item, and returns it so it can be registered easier
     */
    private fun register(name: String, item: Item) = Registry.register(Registries.ITEM, TechedOut.mkId(name), item)
}