package z3roco01.techedout.block

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import z3roco01.techedout.TechedOut

/**
 * Handles the registration of all blocks
 */
object TechedOutBlocks {
    // register all the blocks
    val tempBattery = register(BatteryBlock(), "battery", true)

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