package me.syncwrld.minecraft.pingcompass.item.builder

import me.syncwrld.minecraft.pingcompass.PingCompassPlugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class ItemBuilder(private val itemStack: ItemStack) {

    companion object {
        val NAMESPACE: String = "itembuilder-plugin-" + PingCompassPlugin.getInstance().description.name.lowercase()
    }

    private val placeholderMap = mutableMapOf<String, String>()

    constructor(material: Material?) : this(ItemStack(material!!))

    constructor(material: Material, data: Int) : this(ItemStack(material, data))

    fun addPlaceholder(key: String, value: String): ItemBuilder = apply {
        placeholderMap[key] = value
    }

    fun addPlaceholders(placeholders: Map<String, String>): ItemBuilder = apply {
        placeholderMap.putAll(placeholders)
    }

    fun displayName(name: String): ItemBuilder = modifyItemMeta {
        it.setDisplayName(name)
    }

    fun lore(vararg lines: String): ItemBuilder = lore(lines.toList())

    fun lore(lines: List<String>): ItemBuilder = modifyItemMeta {
        it.lore = lines
    }

    fun addLore(vararg lines: String): ItemBuilder = modifyItemMeta {
        val current = it.lore?.toMutableList() ?: mutableListOf()
        current.addAll(lines)
        it.lore = current
    }

    fun removeLore(line: String): ItemBuilder = modifyItemMeta {
        val current = it.lore?.toMutableList() ?: return@modifyItemMeta
        current.remove(line)
        it.lore = current
    }

    fun clearLore(): ItemBuilder = modifyItemMeta {
        it.lore = listOf()
    }

    fun amount(amount: Int): ItemBuilder = apply {
        itemStack.amount = amount
    }

    fun enchantment(enchant: Enchantment, level: Int): ItemBuilder = modifyItemMeta {
        it.addEnchant(enchant, level, true)
    }

    fun removeEnchantment(enchant: Enchantment): ItemBuilder = modifyItemMeta {
        it.removeEnchant(enchant)
    }

    fun itemFlags(vararg flags: ItemFlag): ItemBuilder = modifyItemMeta {
        it.addItemFlags(*flags)
    }

    fun hideAll(): ItemBuilder = modifyItemMeta {
        it.addItemFlags(*ItemFlag.entries.toTypedArray())
    }

    fun setUnbreakable(unbreakable: Boolean): ItemBuilder = modifyItemMeta {
        it.isUnbreakable = unbreakable
    }

    fun unbreakable(): ItemBuilder = setUnbreakable(true)

    fun build(): ItemStack {
        val meta = itemStack.itemMeta ?: return itemStack
        if (placeholderMap.isNotEmpty()) {
            placeholderMap.forEach { (placeholder, value) ->
                meta.setDisplayName(
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        meta.displayName.replace(placeholder, value)
                    )
                )

                meta.lore = meta.lore?.map {
                    it.replace(placeholder, ChatColor.translateAlternateColorCodes('&', value))
                }
            }
            itemStack.itemMeta = meta
        }
        return itemStack
    }

    private fun modifyItemMeta(action: (ItemMeta) -> Unit): ItemBuilder {
        val meta = itemStack.itemMeta ?: return this
        action(meta)
        itemStack.itemMeta = meta
        return this
    }

    fun persistentStringData(key: String, value: String): ItemBuilder = apply {
        dataContainer {
            it.set(NamespacedKey(NAMESPACE, key), PersistentDataType.STRING, value)
        }
    }

    fun dataContainer(consumer: (PersistentDataContainer) -> Unit): ItemBuilder = modifyItemMeta {
        consumer(it.persistentDataContainer)
    }
}
