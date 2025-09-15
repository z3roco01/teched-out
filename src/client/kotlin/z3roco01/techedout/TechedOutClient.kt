package z3roco01.techedout

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object TechedOutClient : ClientModInitializer {
    val logger = LoggerFactory.getLogger(TechedOut.modid + "-client")
	override fun onInitializeClient() {
        logger.info("starting client init...")



        logger.info("client init finished !!")
	}
}