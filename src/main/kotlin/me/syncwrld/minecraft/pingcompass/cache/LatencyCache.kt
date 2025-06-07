package me.syncwrld.minecraft.pingcompass.cache

import org.bukkit.entity.Player

object LatencyCache {
    lateinit var highestPingPlayer: Player
    lateinit var lowestPingPlayer: Player
}