package z3roco01.techedout

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory
import z3roco01.techedout.network.TechedOutClientPackets
import z3roco01.techedout.screen.TechedOutScreens

object TechedOutClient : ClientModInitializer {
    val logger = LoggerFactory.getLogger(TechedOut.modid + "-client")
	override fun onInitializeClient() {
        logger.info("starting client init...")

        TechedOutScreens.reigster()
        TechedOutClientPackets.register()

        logger.info("client init finished !!")
	}
}