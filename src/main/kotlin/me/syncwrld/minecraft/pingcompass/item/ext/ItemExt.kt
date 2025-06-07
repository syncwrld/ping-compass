package me.syncwrld.minecraft.pingcompass.item.ext

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

fun ItemMeta.hasStringTag(namespacedKey: NamespacedKey): Boolean {
    persistentDataContainer.keys.forEach { key ->
        if (key.namespace == namespacedKey.namespace && key.key == namespacedKey.key) {
            return persistentDataContainer.get(key, PersistentDataType.STRING) != null
        }
    }
    return false
}