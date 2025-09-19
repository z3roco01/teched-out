package z3roco01.techedout.recipe.ingredient

import com.google.gson.JsonObject
import net.fabricmc.fabric.api.recipe.v1.ingredient.FabricIngredient
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import java.util.function.Predicate

/**
 * Handles recipe ingredients that have an item count
 * @param ingredient the ingredient part, handles to item type/tags or wtv
 * @param count the amount of items needed
 */
class CountedIngredient(val ingredient: Ingredient, val count: Int): Predicate<ItemStack>, FabricIngredient {
    /**
     * Returns true if the item stack has at least [count] items and tests true with [ingredient]
     */
    override fun test(itemStack: ItemStack) = (itemStack.count >= count) && (ingredient.test(itemStack))

    /**
     * Only tests the item and not the count
     */
    fun testItem(itemStack: ItemStack) : Boolean {
        // need a clause if it is empty, since Ingredient.test() returns true on empty
        if(itemStack.isEmpty) {
            // if they are both empty, then return true
            return ingredient.isEmpty
        }else
            return ingredient.test(itemStack)
    }

    /**
     * Returns true if either the count is less than or equal to 0, or the ingredient is empty
     */
    fun isEmpty() = (count <= 0) || (ingredient.isEmpty)

    /**
     * Writes the ingredient to the passed PacketByteBuf
     * @param buf the [net.minecraft.network.PacketByteBuf] to write to
     */
    fun write(buf: PacketByteBuf) {
        // first write the ingredient
        ingredient.write(buf)
        // then write the count
        buf.writeInt(count)
    }

    /**
     * Creates and returns a JsonObject for this ingredient
     */
    fun toJson(): JsonObject {
        val json = JsonObject()
        // add the ingredient's json to the object
        json.add("ingredient", ingredient.toJson())
        // next add the count
        json.addProperty("count", count)

        return json
    }

    companion object {
        /**
         * Creates a CountedIngredient from a PacketByteBuf, ingredient read first, then count
         */
        fun fromPacket(buf: PacketByteBuf): CountedIngredient {
            // read ingredient using ingredient method
            val ingredient = Ingredient.fromPacket(buf)
            // then read the count ( must also be written in this order )
            val count = buf.readInt()

            // create and return CountedIngredient
            return CountedIngredient(ingredient, count)
        }

        /**
         * Creates a CountedIngredient from a JsonObject, it must contain an ingredient labeled "ingredient" and a count labeled "count"
         */
        fun fromJson(json: JsonObject): CountedIngredient {
            // read in the ingredient
            val ingredient = Ingredient.fromJson(json["ingredient"])
            // then the count
            val count = json["count"].asInt

            // then create and return the ingredient
            return CountedIngredient(ingredient, count)
        }

        /**
         * A standardised empty ingredient to store for empty slots
         */
        val EMPTY = CountedIngredient(Ingredient.EMPTY, 0)
    }
}