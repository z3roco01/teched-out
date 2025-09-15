package z3roco01.techedout

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object TechedOut : ModInitializer {
    val modid = "teched_out"
    val logger = LoggerFactory.getLogger(modid)

	override fun onInitialize() {
		logger.info("starting init...")



        logger.info("init finished !!")
	}
}