package z3roco01.techedout.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import z3roco01.techedout.recipe.ingredient.CountedIngredient
import kotlin.math.min
import kotlin.math.round

/**
 * A recipe that can be made in an electrolyzer
 * @param inputs the 2 inputs of the recipe
 * @param outputs the 4 outputs of the recipe
 * @param electrolyzeTime how many ticks the recipe takes
 * @param energyCost how much energy in total will be drawn
 */
class ElectrolyzeRecipe(val inputs: List<CountedIngredient>, val outputs: List<ItemStack>, val electrolyzeTime: Int, val energyCost: Int): Recipe<SimpleInventory>{
    /**
     * The amount of energy that should be consumed each tick ( gets rounded to the nearest digit )
     */
    val energyPerTick: Int = round(energyCost.toDouble()/electrolyzeTime.toDouble()).toInt()

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

    override fun matches(inventory: SimpleInventory, world: World): Boolean {
        // never craft on client
        if(world.isClient) return false

        // use findIngredient function, if it doesnt find one return false, since that item does not exist in the recipe
        for(ingredient in ingredients) {
            if(ingredient.isEmpty()) continue // if it is empty then it does not matter

            for(idx in 0..<2) {
                // if there is one that matches, break to the next ingredient
                if(ingredient.test(inventory.getStack(idx))) {
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

    override fun getId() = ID
    override fun getSerializer() = Serializer.INSTANCE
    override fun getType() = Type.INSTANCE

    class Serializer: RecipeSerializer<ElectrolyzeRecipe> {
        override fun read(id: Identifier, json: JsonObject): ElectrolyzeRecipe {
            // get the array of inputs
            val inputsArr = json["inputs"].asJsonArray

            val inputs = DefaultedList.ofSize(2, CountedIngredient.EMPTY)
            // loop over each element and add it to the list, with a max of 2 inputs
            for(i in 0..<min(inputsArr.size(), 2))
                inputs.add(CountedIngredient.fromJson(inputsArr[i].asJsonObject))

            // do a similar process for outputs, but max it at 4
            val outputsArr = json["outputs"].asJsonArray

            val outputs = DefaultedList.ofSize(4, ItemStack.EMPTY)
            // loop over each element and add it to the list, with a max of 2 outputs
            for(i in 0..<min(outputsArr.size(), 4))
                outputs.add(ShapedRecipe.outputFromJson(outputsArr[i].asJsonObject))

            // then parse the time and energy
            val electrolyzeTime = json["electrolyze_time"].asInt
            val energyCost = json["energy_cost"].asInt

            // now create and return the recipe
            return ElectrolyzeRecipe(inputs, outputs, electrolyzeTime, energyCost)
        }

        // must be read/written to the buf in the same order
        override fun read(id: Identifier, buf: PacketByteBuf): ElectrolyzeRecipe {
            // read in the input count, but do not let it exceed 2, since that is how many inputs there are
            val inputCount = min(buf.readInt(), 2)

            val inputs = DefaultedList.ofSize(2, CountedIngredient.EMPTY)
            // loop for each input and add it to the list, any empty slots are filled with empty ingredients
            for(i in 0..<inputCount)
                inputs[i] = CountedIngredient.fromPacket(buf)

            // do a similar process for outputs, but max is 4 instead of 2
            val outputCount = min(buf.readInt(), 2)

            val outputs = DefaultedList.ofSize(2, ItemStack.EMPTY)
            for(i in 0..<outputCount)
                outputs[i] = buf.readItemStack()

            // then read in the time and energy cost
            val electrolyzeTime = buf.readInt()
            val energyCost = buf.readInt()

            // then create the recipe
            return ElectrolyzeRecipe(inputs, outputs, electrolyzeTime, energyCost)
        }

        override fun write(buf: PacketByteBuf, recipe: ElectrolyzeRecipe) {

            // first write the input count just in case its too little
            buf.writeInt(recipe.inputs.size)
            // write each ingredient to the buf
            for(input in recipe.inputs)
                input.write(buf)

            // do the same processor for the outputs
            buf.writeInt(recipe.outputs.size)
            for(output in recipe.outputs)
                buf.writeItemStack(output)

            // then write the time and energy
            buf.writeInt(recipe.electrolyzeTime)
            buf.writeInt(recipe.energyCost)
        }

        companion object {
            // an instance is needed for the recipe
            val INSTANCE = Serializer()
        }
    }

    class Type: RecipeType<ElectrolyzeRecipe> {
        companion object {
            val INSTANCE = Type()
        }
    }

    companion object {
        val ID = TechedOut.mkId("electrolyze")
    }
}