package de.flodor07.chunkclaim.commands.claim

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class UnClaimCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.claim")) return false
        val player: Player = sender as Player


        if(Plugin.getPlugin()?.config?.getStringList("claim.${player.name}") == null) {
            Plugin.sendMessage(sender, "§cDir gehören keine Chunks")
            return false
        }
        if(Plugin.getPlugin()?.config?.getStringList("claim.${player.name}")!!.isEmpty()) {
            Plugin.sendMessage(sender, "§cDir gehören keine Chunks")
            return false
        }


        val claimedChunks = ArrayList<String>()

        for (chunkKey in Plugin.getPlugin()?.config?.getStringList("claim.${player.name}")!!) {
            claimedChunks.add(chunkKey.toString())
        }

        if(!claimedChunks.contains(player.location.chunk.chunkKey.toString())) {
            Plugin.sendMessage(player, "§cDieser Chunk gehört dir nicht")
            return false
        }

        claimedChunks.remove(player.location.chunk.chunkKey.toString())

        Plugin.getPlugin()?.config?.set("claim.${player.name}",claimedChunks )
        Plugin.getPlugin()?.saveConfig()

        Plugin.sendMessage(player, "Dieser Chunck gehört nun nichtmehr dir")
        return true
    }
}