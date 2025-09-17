package z3roco01.techedout.recipe

import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

/**
 * Handles registering of recipes
 */
object TechedOutRecipes {
    /**
     * Registers each recipe
     */
    fun register() {
        Registry.register(Registries.RECIPE_TYPE, AlloyingRecipe.ID, AlloyingRecipe.Type.INSTANCE)
        Registry.register(Registries.RECIPE_SERIALIZER, AlloyingRecipe.ID, AlloyingRecipe.Serializer.INSTANCE)
    }
}