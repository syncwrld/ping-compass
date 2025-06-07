package me.syncwrld.minecraft.pingcompass.command

import me.syncwrld.minecraft.pingcompass.item.Items
import me.syncwrld.minecraft.pingcompass.sample.TrackingMode
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GiveCompassCommand : CommandExecutor {
    override fun onCommand(
        commandIssuer: CommandSender,
        command: Command,
        label: String,
        arguments: Array<out String?>?
    ): Boolean {
        if (arguments?.isEmpty() == true) {
            commandIssuer.sendMessage("§7Usage: §9/${label} <player>");
            return false;
        }

        val playerName: String? = arguments?.get(0);
        val playerTarget = Bukkit.getPlayerExact(playerName!!)

        if (playerTarget == null || !(playerTarget.isOnline)) {
            commandIssuer.sendMessage("§c$playerName is invalid or offline.")
            return false;
        }

        playerTarget.inventory.addItem(Items.buildCompass(TrackingMode.HIGHEST))
        return true;
    }
}