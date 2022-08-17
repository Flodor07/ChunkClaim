package de.flodor07.chunkclaim.commands.admin

import de.flodor07.chunkclaim.util.Plugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class InfoCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.info")) return false
        val player = sender as Player
        val key: String = player.location.chunk.chunkKey.toString();

        Plugin.sendEmptyMessage(player)
        Plugin.sendMessage(player, "§a§lClaim Info")
        Plugin.sendEmptyMessage(player)

        if(getList() == null) {
            Plugin.sendMessage(player, "Claimed: Nein")
        }
        else if(getList()?.contains(key)!!) {
            Plugin.sendMessage(player, "Claimed: Ja")

            if(getOwner(key) != null) {
                Plugin.sendMessage(player, "Owner: ${getOwner(key)!!.name}" )
                Plugin.sendMessage(player, "UUID: ${getOfflineOwner(key)?.uniqueId!!}",
                    HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7Klick zum kopieren")),
                    ClickEvent.copyToClipboard("${getOfflineOwner(key)?.uniqueId!!}"))

            } else if(getOfflineOwner(key) != null) {
                Plugin.sendMessage(player, "Owner: ${getOfflineOwner(key)!!.name}" )

                Plugin.sendMessage(player, "UUID: ${getOfflineOwner(key)?.uniqueId!!}",
                    HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7Klick zum kopieren")),
                    ClickEvent.copyToClipboard("${getOfflineOwner(key)?.uniqueId!!}"))
            }
        } else {
            Plugin.sendMessage(player, "Claimed: Nein")
        }

        Plugin.sendEmptyMessage(player)

        Plugin.sendMessage(player, "ChunkID: $key",
            HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7Klick zum kopieren")),
            ClickEvent.copyToClipboard(key))

        Plugin.sendMessage(player, "ChunkX: ${player.location.chunk.x}")
        Plugin.sendMessage(player, "ChunkZ: ${player.location.chunk.z}")

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