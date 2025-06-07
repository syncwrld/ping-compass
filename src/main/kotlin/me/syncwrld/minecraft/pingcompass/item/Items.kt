package me.syncwrld.minecraft.pingcompass.item

import com.cryptomorin.xseries.XMaterial
import me.syncwrld.minecraft.pingcompass.item.builder.ItemBuilder
import me.syncwrld.minecraft.pingcompass.item.ext.hasStringTag
import me.syncwrld.minecraft.pingcompass.sample.TrackingMode
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

class Items {
    companion object {

        val PING_COMPASS_ITEM_NAMESPACE: NamespacedKey = NamespacedKey(ItemBuilder.NAMESPACE, "ping_compass")
        val PING_COMPASS_ITEM_TRACKING_MODE_KEY: NamespacedKey =
            NamespacedKey(ItemBuilder.NAMESPACE, "ping_compass-tracking_mode")

        fun buildCompass(trackingMode: TrackingMode): ItemStack {
            return ItemBuilder(XMaterial.COMPASS.get())
                .displayName("§9Ping Compass")
                .lore(
                    "§7Tracks the player with the ",
                    "§7biggest/lowest ping on server.",
                    "",
                    "§7Currently tracking: ${trackingMode.state()} ping",
                    "§7Alternate mode with right click."
                )
                .persistentStringData("ping_compass", UUID.randomUUID().toString())
                .persistentStringData("ping_compass-tracking_mode", trackingMode.name.lowercase())
                .unbreakable()
                .hideAll()
                .build();
        }

        fun isCompass(itemStack: ItemStack?): Boolean {
            return itemStack != null && itemStack.type == buildCompass(TrackingMode.HIGHEST).type && hasPersistentData(
                itemStack.itemMeta
            );
        }

        fun trackingMode(itemStack: ItemStack?): TrackingMode? {
            if (itemStack == null || !isCompass(itemStack)) return null;
            val itemMeta: ItemMeta = itemStack.itemMeta!!;
            return if (itemMeta.hasStringTag(PING_COMPASS_ITEM_TRACKING_MODE_KEY)) {
                TrackingMode.valueOf(
                    itemMeta.persistentDataContainer.get(
                        PING_COMPASS_ITEM_TRACKING_MODE_KEY,
                        PersistentDataType.STRING
                    )!!.uppercase()
                )
            } else {
                null;
            }
        }

        private fun hasPersistentData(itemMeta: ItemMeta?): Boolean {
            if (itemMeta == null) return false;
            return itemMeta.hasStringTag(PING_COMPASS_ITEM_NAMESPACE);
        }
    }
}