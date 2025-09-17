package z3roco01.techedout

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import z3roco01.techedout.block.TechedOutBlocks
import z3roco01.techedout.blockentity.TechedOutBlockEntities
import z3roco01.techedout.recipe.TechedOutRecipes
import z3roco01.techedout.screen.TechedOutScreenHandlers

object TechedOut : ModInitializer {
    val modid = "teched_out"
    val logger = LoggerFactory.getLogger(modid)

	override fun onInitialize() {
		logger.info("starting init...")

        TechedOutBlocks.init()
        TechedOutBlockEntities.init()
        TechedOutScreenHandlers.init()
        TechedOutRecipes.register()

        logger.info("init finished !!")
	}

    /**
     * Makes an identifier with the teched out id
     * @param name the name part of the identifier
     * @return the fully qualified id
     */
    fun mkId(name: String) = Identifier.of(modid, name)!!
}