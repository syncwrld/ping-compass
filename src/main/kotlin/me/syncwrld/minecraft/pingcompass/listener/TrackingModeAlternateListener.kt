package me.syncwrld.minecraft.pingcompass.listener

import me.syncwrld.minecraft.pingcompass.item.Items
import me.syncwrld.minecraft.pingcompass.sample.TrackingMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class TrackingModeAlternateListener : Listener {

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerSneak(event: PlayerInteractEvent) {
        val item = event.item
        if (!Items.isCompass(item)) return

        val action = event.action
        if (!action.name.contains("RIGHT")) return

        val invertedMode = Items.trackingMode(item)?.let {
            when (it) {
                TrackingMode.HIGHEST -> TrackingMode.LOWEST
                TrackingMode.LOWEST -> TrackingMode.HIGHEST
            }
        }

        Items.buildCompass(invertedMode ?: return).let {
            event.player.inventory.setItemInMainHand(it)
            event.player.updateInventory()
            event.isCancelled = true
        }
    }
}