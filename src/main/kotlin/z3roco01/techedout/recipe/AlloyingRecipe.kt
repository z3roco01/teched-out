package z3roco01.techedout.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import kotlin.contracts.SimpleEffect
import kotlin.coroutines.coroutineContext
import kotlin.math.round

/**
 * Defines and checks recipes in the alloy smelter
 * @param ingredients the inputs of the alloying
 * @param output the output stack of the alloying
 * @param alloyTime how many ticks the alloying takes
 * @param energyCost how much energy is taken
 */
class AlloyingRecipe(val ingredients: List<CountedIngredient>, private val output: ItemStack, val alloyTime: Int, val energyCost: Int): Recipe<SimpleInventory> {
    /**
     * The amount of energy that should be consumed each tick ( gets rounded to the nearest digit )
     */
    val energyPerTick: Int = round(energyCost.toDouble()/alloyTime.toDouble()).toInt()

    /**
     * Finds the ingredient that corresponds with an item if it exists
     */
    fun findIngredient(itemStack: ItemStack): CountedIngredient? {
        for(ingredient in ingredients) {
            // if the item tests true with the ingredient, return it
            if(ingredient.testItem(itemStack))
                return ingredient
        }

        // if it did not find an ingredient, return null
        return null
    }

    override fun matches(inventory: SimpleInventory, world: World): Boolean {
        // crafting should only happen server side
        if(world.isClient) return false

        // check every input against the first 3 stacks
        for(ingredient in ingredients) {
            if(ingredient.isEmpty()) continue // if it is empty then it does not matter

            for(idx in 0..inventory.size()) {
                // if there is one that matches, break to the next ingredient
                if(ingredient.test(inventory.getStack(idx))) {
                    break
                }

                // if it got to the end without breaking, return false since there is no match
                if(idx == inventory.size()-1)
                    return false
            }
        }

        return true
    }

    override fun craft(inventory: SimpleInventory, registryManager: DynamicRegistryManager?) = output.copy()

    override fun fits(width: Int, height: Int) = true

    override fun getOutput(registryManager: DynamicRegistryManager?) = output

    override fun getId() = ID

    override fun getSerializer() = Serializer.INSTANCE

    override fun getType() = Type.INSTANCE

    class Serializer: RecipeSerializer<AlloyingRecipe> {
        override fun read(id: Identifier, json: JsonObject): AlloyingRecipe {
            // get ingredients as an array
            val ingredientsArr = json["ingredients"].asJsonArray

            // create list for ingredients
            val ingredients = DefaultedList.ofSize(3, CountedIngredient.EMPTY)
            // then parse in each ingredient from the array
            for(i in 0..<ingredientsArr.size())
                ingredients[i] = CountedIngredient.fromJson(ingredientsArr.get(i).asJsonObject)

            // parse in ItemStack using helper from another recipe
            val output = ShapedRecipe.outputFromJson(json["output"] as JsonObject?)

            // parse in alloy time and energy cost
            val alloyTime = json["alloy_time"].asInt
            val energyCost = json["energy_cost"].asInt

            // finally create and return recipe
            return AlloyingRecipe(ingredients, output, alloyTime, energyCost)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): AlloyingRecipe {
            // must be read/written in the same order
            // read in the ingredients size
            val size = buf.readInt()

            // create the ingredients list
            val ingredients = DefaultedList.ofSize(size, CountedIngredient.EMPTY)
            // and read in each ingredient from the buffer
            for(i in 0..<size)
                ingredients[i] = CountedIngredient.fromPacket(buf)
            // then read in the output
            val output = buf.readItemStack()
            // next read in alloy time
            val alloyTime = buf.readInt()
            // and finally energy cost
            val energyCost = buf.readInt()

            // then create and return the recipe
            return AlloyingRecipe(ingredients, output, alloyTime, energyCost)
        }

        override fun write(buf: PacketByteBuf, recipe: AlloyingRecipe) {
            // first write the size of inputs, so it can be read properly
            buf.writeInt(recipe.ingredients.size)
            // then write each ingredient
            for(ingredient in recipe.ingredients)
                ingredient.writePacket(buf)
            // then write the output
            buf.writeItemStack(recipe.output)
            // write the alloy time
            buf.writeInt(recipe.alloyTime)
            // and the energy cost
            buf.writeInt(recipe.energyCost)
        }

        companion object {
            // an instance is needed for the recipe
            val INSTANCE = Serializer()
        }
    }

    class Type: RecipeType<AlloyingRecipe> {
        companion object {
            val INSTANCE = Type()
        }
    }

    companion object {
        val ID = TechedOut.mkId("alloying")
    }
}