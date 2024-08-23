package dev.krysztal.are.common

import dev.krysztal.are.AgrarianRevolution.identifier
import dev.krysztal.are.AgrarianRevolution.log
import dev.krysztal.are.common.ItemGroups.BASE_GROUP_KEY
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks as MBlocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey

object Blocks {

    // Dirt
    val ACIDIFIED_DIRT: Block = register(
      "acidified_dirt",
      Block(AbstractBlock.Settings.copy(MBlocks.DIRT_PATH))
    )
    val ALKALIZED_DIRT: Block = register(
      "alkalized_dirt",
      Block(AbstractBlock.Settings.copy(MBlocks.DIRT_PATH))
    )
    val HARDENED_DIRT: Block = register(
      "hardened_dirt",
      Block(AbstractBlock.Settings.copy(MBlocks.NETHERRACK))
    )

    // Ore
    val APATITE_ORE: Block = register(
      "apatite_ore",
      Block(AbstractBlock.Settings.copy(MBlocks.COAL_ORE))
    )

    private def register(
        id: String,
        block: Block,
        shouldRegisterItem: Boolean = true,
        itemSettings: Item.Settings = Item.Settings(),
        key: RegistryKey[ItemGroup] = BASE_GROUP_KEY
    ) = {
        log.trace(s"Registering block: $id -> $block")
        Registry.register(Registries.BLOCK, identifier(id), block)

        if (shouldRegisterItem) {
            val blockItem = BlockItem(block, itemSettings)
            log.trace(s"Registering block item: $id -> $blockItem")
            Registry.register(Registries.ITEM, identifier(id), blockItem)
        }

        ItemGroupEvents.modifyEntriesEvent(key).register(_.add(block.asItem()))

        block
    }

    def initialize(): Unit = {}
}
