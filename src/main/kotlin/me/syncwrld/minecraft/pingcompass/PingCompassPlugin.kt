package me.syncwrld.minecraft.pingcompass

import me.syncwrld.minecraft.pingcompass.command.GiveCompassCommand
import me.syncwrld.minecraft.pingcompass.listener.TrackingModeAlternateListener
import me.syncwrld.minecraft.pingcompass.task.CompassPointerCheckRunnable
import me.syncwrld.minecraft.pingcompass.task.PlayerLatencyDeterminationRunnable
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PingCompassPlugin : JavaPlugin() {

    companion object {
        private lateinit var instance: PingCompassPlugin

        fun getInstance(): PingCompassPlugin {
            return instance;
        }
    }

    override fun onEnable() {
        instance = this
        getCommand("givepc")?.setExecutor(GiveCompassCommand())

        PlayerLatencyDeterminationRunnable().runTaskTimer(this, 0L, 45L)
        CompassPointerCheckRunnable().runTaskTimer(this, 0L, 10L)
        Metrics(this, 26112)

        Bukkit.getPluginManager().registerEvents(TrackingModeAlternateListener(), this)
    }

}