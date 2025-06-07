package me.syncwrld.minecraft.pingcompass.task

import com.cryptomorin.xseries.messages.ActionBar
import me.syncwrld.minecraft.pingcompass.cache.LatencyCache
import me.syncwrld.minecraft.pingcompass.item.Items
import me.syncwrld.minecraft.pingcompass.sample.TrackingMode
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class CompassPointerCheckRunnable : BukkitRunnable() {
    override fun run() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val handItem = player.inventory.itemInMainHand
            if (!Items.isCompass(handItem)) return@forEach

            Items.trackingMode(handItem)?.let { trackingMode ->
                when (trackingMode) {
                    TrackingMode.HIGHEST -> player.compassTarget = LatencyCache.highestPingPlayer.location
                    TrackingMode.LOWEST -> player.compassTarget = LatencyCache.lowestPingPlayer.location
                }

                val trackedPlayer =
                    if (trackingMode == TrackingMode.HIGHEST) LatencyCache.highestPingPlayer.name else LatencyCache.lowestPingPlayer.name
                ActionBar.sendActionBar(
                    player,
                    "§7Tracking: ${trackingMode.state()} ping §8| §7Tracked player: §9$trackedPlayer"
                )
            }
        }
    }
}