package z3roco01.techedout.item

import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut

/**
 * Holds all the registered items
 */
object TechedOutItems {
    val RED_GOLD_INGOT  = register("red_gold_ingot", Item(Item.Settings()))
    val RED_GOLD_NUGGET = register("red_gold_nugget", Item(Item.Settings()))

    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * Registers an item, and returns it so it can be registered easier
     */
    private fun register(name: String, item: Item) = Registry.register(Registries.ITEM, TechedOut.mkId(name), item)
}