package z3roco01.techedout.data

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import z3roco01.techedout.data.providers.TechedOutEnglishProvider
import z3roco01.techedout.data.providers.TechedOutFrenchProvider
import z3roco01.techedout.data.providers.TechedOutModelProvider
import z3roco01.techedout.data.providers.TechedOutRecipeProvider

object TechedOutDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()

        pack.addProvider(::TechedOutModelProvider)
        pack.addProvider(::TechedOutEnglishProvider)
        pack.addProvider(::TechedOutFrenchProvider)
        pack.addProvider(::TechedOutRecipeProvider)
	}
}