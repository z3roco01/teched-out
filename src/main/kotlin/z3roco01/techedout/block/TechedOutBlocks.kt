package z3roco01.techedout.block

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut
import z3roco01.techedout.block.machines.AlloySmelterBlock
import z3roco01.techedout.block.machines.BatteryBlock

/**
 * Handles the registration of all blocks
 */
object TechedOutBlocks {
    // register all the blocks
    val LOW_BATTERY        = register(BatteryBlock(Tier.LOW), "low_battery", true)
    val MIDDLE_BATTERY     = register(BatteryBlock(Tier.MIDDLE), "middle_battery", true)
    val HIGH_BATTERY       = register(BatteryBlock(Tier.HIGH), "high_battery", true)
    val VERY_HIGH_BATTERY  = register(BatteryBlock(Tier.VERY_HIGH), "very_high_battery", true)
    val ULTRA_HIGH_BATTERY = register(BatteryBlock(Tier.ULTRA_HIGH), "ultra_high_battery", true)

    val COPPER_CABLE = register(CableBlock(Tier.LOW), "copper_cable", true)
    val GOLD_CABLE   = register(CableBlock(Tier.MIDDLE), "gold_cable", true)
    // TODO: name them after their material instead
    val HIGH_CABLE = register(CableBlock(Tier.HIGH), "high_cable", true)
    val VERY_HIGH_CABLE = register(CableBlock(Tier.VERY_HIGH), "very_high_cable", true)
    val ULTRA_HIGH_CABLE = register(CableBlock(Tier.ULTRA_HIGH), "ultra_high_cable", true)

    val LOW_ALLOY_SMELTER    = register(AlloySmelterBlock(Tier.LOW), "low_alloy_smelter", true)
    val MIDDLE_ALLOY_SMELTER = register(AlloySmelterBlock(Tier.MIDDLE), "middle_alloy_smelter", true)
    val HIGH_ALLOY_SMELTER   = register(AlloySmelterBlock(Tier.HIGH), "high_alloy_smelter", true)

    /**
     * Dummy function to ensure everything is loaded in time
     */
    fun init() {}

    /**
     * Registers a block ( and maybe its item )
     * @param block the [Block] to register
     * @param name the id of the block, with out the mod id
     * @param createItem if true a BlockItem will also be registered
     * @return the block after being successfully registered
     */
    private fun register(block: Block, name: String, createItem: Boolean): Block {
        // create the id
        val id = TechedOut.mkId(name)

        // create and register the item if needed
        if(createItem) {
            val item = BlockItem(block, Item.Settings())
            Registry.register(Registries.ITEM, id, item)
        }

        // register the item then return it ( so it can be referenced later
        return Registry.register(Registries.BLOCK, id, block)
    }
}