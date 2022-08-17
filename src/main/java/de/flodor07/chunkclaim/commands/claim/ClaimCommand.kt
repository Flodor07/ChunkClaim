package de.flodor07.chunkclaim.commands.claim

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.Collections

class ClaimCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.claim")) return false

        val player: Player = sender as Player
        val claimedChunks = ArrayList<String>()

        val maxChunksList: ArrayList<Int> = ArrayList()
        maxChunksList.add(1)


        player.effectivePermissions.forEach{ perm -> run {
            if("chunkclaim.claim.chunks" in perm.permission) {
                maxChunksList.add(perm.permission.split("chunkclaim.claim.chunks.")[1].toInt())
            }
        } }

        val maxChunks:Int = Collections.max(maxChunksList)

        if(Plugin.getPlugin()?.config?.getStringList("claim.${player.name}") != null) {
            if(Plugin.getPlugin()?.config?.getStringList("claim.${player.name}")!!.isNotEmpty()) {
                for (chunkKey in Plugin.getPlugin()?.config?.getStringList("claim.${player.name}")!!) {
                    claimedChunks.add(chunkKey.toString())
                }

                if (claimedChunks.size >= maxChunks) {
                    Plugin.sendMessage(player, "§cDu hast dein Maximum an claimed Chunks erreicht")
                    return false
                }

                if(claimedChunks.contains(player.location.chunk.chunkKey.toString())) {
                    Plugin.sendMessage(player, "Dieser Chunk gehört dir schon")
                    return true
                }
            }
        }

        if(getList() != null) {
            if(getList()!!.contains(player.location.chunk.chunkKey.toString())) {
                Plugin.sendMessage(player, "§cDieser Chunk gehört jemand anderen")
                return false
            }
        }

        claimedChunks.add(player.location.chunk.chunkKey.toString())

        Plugin.getPlugin()?.config?.set("claim.${player.name}",claimedChunks )
        Plugin.getPlugin()?.saveConfig()

        Plugin.sendMessage(player, "Dieser Chunk gehört nun dir")
        return true
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