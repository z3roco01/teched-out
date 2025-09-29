package z3roco01.techedout.recipe

import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.world.World
import z3roco01.techedout.recipe.ingredient.CountedIngredient
import kotlin.math.round

/**
 * Creates common interfaces for inputs and outputs
 * @param inputs the inputs of the recipe
 * @param outputs the outputs of the recipe
 * @param craftTime the amount of time it takes to craft
 * @param energyCost the energy it will consume over the whole craft time
 */
abstract class BaseRecipe(val inputs: List<CountedIngredient>, val outputs: List<ItemStack>, val craftTime: Int, val energyCost: Int):
    Recipe<SimpleInventory> {
    /**
     * The amount of energy that should be consumed each tick ( gets rounded to the nearest digit )
     */
    val energyPerTick: Int = round(energyCost.toDouble()/craftTime.toDouble()).toInt()

    override fun matches(inventory: SimpleInventory, world: World): Boolean {
        // never craft on client
        if(world.isClient) return false

        // use findIngredient function, if it doesnt find one return false, since that item does not exist in the recipe
        for(input in inputs) {
            if(input.isEmpty()) continue // if it is empty then it does not matter

            for(idx in 0..<2) {
                // if there is one that matches, break to the next ingredient
                if(input.test(inventory.getStack(idx))) {
                    break
                }

                // if it got to the end without breaking, return false since there is no match
                if(idx == 1)
                    return false
            }
        }

        // if there were no problems in the loop, then return true
        return true
    }

    /**
     * Should not be used because of multiple outputs
     */
    override fun craft(inventory: SimpleInventory, registryManager: DynamicRegistryManager) = ItemStack.EMPTY

    // not going to be used so it doesnt matter
    override fun fits(width: Int, height: Int) = true

    /**
     * Should not be used, it has multiple outputs
     */
    override fun getOutput(registryManager: DynamicRegistryManager?) = ItemStack.EMPTY

    /**
     * Finds the ingredient that corresponds with an item if it exists
     */
    fun findInput(itemStack: ItemStack): CountedIngredient? {
        for(input in inputs) {
            // if the item tests true with the ingredient, return it
            if(input.testItem(itemStack))
                return input
        }

        // if it did not find an ingredient, return null
        return null
    }
}