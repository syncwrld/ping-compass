package me.syncwrld.minecraft.pingcompass.task

import me.syncwrld.minecraft.pingcompass.cache.LatencyCache
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class PlayerLatencyDeterminationRunnable : BukkitRunnable() {

    override fun run() {
        val players = Bukkit.getOnlinePlayers()
        if (players.isEmpty()) return

        var maxPlayer: Player? = null
        var minPlayer: Player? = null
        var maxPing = Int.MIN_VALUE
        var minPing = Int.MAX_VALUE

        players.forEach { player ->
            val ping = player.ping
            if (ping > maxPing) {
                maxPing = ping
                maxPlayer = player
            }
            if (ping < minPing) {
                minPing = ping
                minPlayer = player
            }
        }

        LatencyCache.highestPingPlayer = maxPlayer!!
        LatencyCache.lowestPingPlayer = minPlayer!!
    }

}
