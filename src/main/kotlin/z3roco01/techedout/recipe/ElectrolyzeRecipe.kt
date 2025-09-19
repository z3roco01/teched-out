package z3roco01.techedout.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.util.Identifier
import net.minecraft.world.World
import z3roco01.techedout.TechedOut
import z3roco01.techedout.recipe.AlloyingRecipe.Serializer
import z3roco01.techedout.recipe.ingredient.CountedIngredient
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

    override fun matches(inventory: SimpleInventory, world: World): Boolean {
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
        override fun read(id: Identifier, json: JsonObject?): ElectrolyzeRecipe {
        }

        override fun read(id: Identifier, buf: PacketByteBuf): ElectrolyzeRecipe {
        }

        override fun write(buf: PacketByteBuf, recipe: ElectrolyzeRecipe) {
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