package z3roco01.techedout.data

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import z3roco01.techedout.data.providers.TechedOutEnglishProvider
import z3roco01.techedout.data.providers.TechedOutModelProvider

object TechedOutDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()

        pack.addProvider(::TechedOutModelProvider)
        pack.addProvider(::TechedOutEnglishProvider)
	}
}