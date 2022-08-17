package de.flodor07.chunkclaim.commands.admin

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class RemoveClaimCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.claim")) return false
        val player: Player = sender as Player
        var target: String? = getOwner(player.location.chunk.chunkKey.toString())?.name

        if(target == null) {
           target = getOfflineOwner(player.location.chunk.chunkKey.toString())?.name
        }

        if(target == null) {
            Plugin.sendMessage(player, "§cChunk gehört keinem Spieler")
            return false
        }


        val claimedChunks = ArrayList<String>()
        for (chunkKey in Plugin.getPlugin()?.config?.getStringList("claim.${target}")!!) {
            claimedChunks.add(chunkKey.toString())
        }

        claimedChunks.remove(player.location.chunk.chunkKey.toString())

        Plugin.getPlugin()?.config?.set("claim.${target}",claimedChunks )
        Plugin.getPlugin()?.saveConfig()

        Plugin.sendMessage(player, "Dieser Chunck gehört nun nichtmehr ${target}")
        return true
    }


    private fun getOwner(key: String): Player? {
        if(Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false) == null) return null
        var chunkOwner: String? = null;

        for (name in Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false)!!) {
            for (chunkKey: String in Plugin.getPlugin()?.config?.getStringList("claim.$name")!!) {
                if (chunkKey == key) chunkOwner = name
            }
        }

        if(chunkOwner == null) return null;

        return Bukkit.getPlayer(chunkOwner);
    }

    private fun getOfflineOwner(key: String): OfflinePlayer? {
        if(Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false) == null) return null
        var chunkOwner: String? = null;

        for (name in Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false)!!) {
            for (chunkKey: String in Plugin.getPlugin()?.config?.getStringList("claim.$name")!!) {
                if (chunkKey == key) chunkOwner = name
            }
        }

        if(chunkOwner == null) return null;

        return Bukkit.getOfflinePlayer(chunkOwner);
    }

    private fun getList(): ArrayList<String>? {
        if(Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false) == null) return null

        val claimedChunksKeys = ArrayList<String>()

        for (name:String in Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false)!!) {
            for (chunkKey in Plugin.getPlugin()?.config?.getStringList("claim.$name")!!) {
                claimedChunksKeys.add(chunkKey)
            }
        }

        return claimedChunksKeys
    }
}