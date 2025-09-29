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

class MillRecipe(val input: CountedIngredient, val outputs: List<ItemStack>, val millTime: Int, val energyCost: Int):
    Recipe<SimpleInventory> {
    /**
     * The amount of energy that should be consumed each tick ( gets rounded to the nearest digit )
     */
    val energyPerTick: Int = round(energyCost.toDouble()/millTime.toDouble()).toInt()

    override fun matches(inventory: SimpleInventory, world: World) = input.test(inventory.getStack(0))

    /**
     * Should not be used since this recipe type has multiple outputs
     */
    override fun craft(inventory: SimpleInventory, registryManager: DynamicRegistryManager) = ItemStack.EMPTY

    override fun fits(width: Int, height: Int) = true

    /**
     * Should not be used since this recipe type has multiple outputs
     */
    override fun getOutput(registryManager: DynamicRegistryManager) = ItemStack.EMPTY

    override fun getId() = ID

    override fun getSerializer() = Serializer.INSTANCE

    override fun getType() = Type.INSTANCE

    class Serializer: RecipeSerializer<MillRecipe> {
        override fun read(id: Identifier, json: JsonObject): MillRecipe {
            // read in the one input
            val input = CountedIngredient.fromJson(json["input"].asJsonObject)

            val outputsArr = json["outputs"].asJsonArray
            // create a list to store the outputs
            val outputs = DefaultedList.ofSize(3, ItemStack.EMPTY)
            // loop over each element and add it to the list, with a max of 3 outputs
            for(i in 0..<min(outputsArr.size(), 3))
                outputs[i] = ShapedRecipe.outputFromJson(outputsArr[i].asJsonObject)

            // read in time and energy usage
            val millTime = json["mill_time"].asInt
            val energyCost = json["energy_cost"].asInt

            return MillRecipe(input, outputs, millTime, energyCost)
        }

        override fun read(id: Identifier, buf: PacketByteBuf): MillRecipe {
            // read in the input
            val input = CountedIngredient.fromPacket(buf)

            // then get the count ( not more than 3 ) of outputs
            val outputCnt = min(buf.readInt(), 3)

            val outputs = DefaultedList.ofSize(3, ItemStack.EMPTY)
            // loop for each output and read it into the list
            for(i in 0..<outputCnt)
                outputs[i] = buf.readItemStack()

            // then read in the time and energy
            val millTime = buf.readInt()
            val energyCost = buf.readInt()

            return MillRecipe(input, outputs, millTime, energyCost)
        }

        override fun write(buf: PacketByteBuf, recipe: MillRecipe) {
            // write the input
            recipe.input.write(buf)

            // write the count ( not more than 3 ) of outputs
            buf.writeInt(min(recipe.outputs.size, 3))
            // then write each item stack
            for(output in recipe.outputs)
                buf.writeItemStack(output)

            // then write the time and cost
            buf.writeInt(recipe.millTime)
            buf.writeInt(recipe.energyCost)
        }

        companion object {
            val INSTANCE = Serializer()
        }
    }

    /**
     * Needed for registration and lookup
     */
    class Type: RecipeType<MillRecipe> {
        companion object {
            val INSTANCE = Type()
        }
    }

    companion object {
        val ID = TechedOut.mkId("mill")
    }
}