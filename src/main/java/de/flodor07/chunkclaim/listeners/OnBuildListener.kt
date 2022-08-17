package de.flodor07.chunkclaim.listeners

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import java.util.StringJoiner


class OnBuildListener : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent)  {
        if(getList() == null) return
        val claimedChunksKeys = getList()!!
        val key: String = event.block.location.chunk.chunkKey.toString();

        if(claimedChunksKeys.contains(key)) {
            if(checkOwner(event.player, key) == null) return
            if(checkOwner(event.player, key) == true) return
            if(checkTrusted(event.player, key) == true) return

            if(event.player.hasPermission("chunkclaim.admin")) {
                if(getOwner(key) != null) {
                    Plugin.sendMessage(event.player, "§cAchtung dieser Chunk gehört ${getOwner(key)?.name}")
                } else {
                    Plugin.sendMessage(event.player, "§cAchtung dieser Chunk gehört ${getOwnerOffline(key)?.name}")
                }
                return
            }

            if(getOwner(key) != null) {
                Plugin.sendMessage(event.player, "Dieser Chunk gehört ${getOwner(key)?.name}")
            } else {
                Plugin.sendMessage(event.player, "Dieser Chunk gehört ${getOwnerOffline(key)?.name}")
            }

            event.isCancelled = !checkOwner(event.player, event.block.location.chunk.chunkKey.toString())!!
            return
        }

    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if(getList() == null) return
        val claimedChunksKeys = getList()!!
        val key: String = event.block.location.chunk.chunkKey.toString();

        if(claimedChunksKeys.contains(key)) {
            if(checkOwner(event.player, key) == null) return
            if(checkOwner(event.player, key) == true) return
            if(checkTrusted(event.player, key) == true) return

            if(event.player.hasPermission("chunkclaim.admin")) {
                if(getOwner(key) != null) {
                    Plugin.sendMessage(event.player, "§cAchtung dieser Chunk gehört ${getOwner(key)?.name}")
                } else {
                    Plugin.sendMessage(event.player, "§cAchtung dieser Chunk gehört ${getOwnerOffline(key)?.name}")
                }
                return
            }

            if(getOwner(key) != null) {
                Plugin.sendMessage(event.player, "Dieser Chunk gehört ${getOwner(key)?.name}")
            } else {
                Plugin.sendMessage(event.player, "Dieser Chunk gehört ${getOwnerOffline(key)?.name}")
            }

            event.isCancelled = !checkOwner(event.player, event.block.location.chunk.chunkKey.toString())!!
            return
        }

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

    private fun checkOwner(player: Player, key: String): Boolean? {
        if(Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false) == null) return null
        if(!Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false)!!.contains(player.name)) return false

        for (chunkKey in Plugin.getPlugin()?.config?.getStringList("claim.${player.name}")!!) {
            if(key == chunkKey) return true
        }

        return false
    }

    private fun checkTrusted(player: Player, key: String): Boolean? {
        if(Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false) == null) return null
        if(Plugin.getPlugin()?.config?.getConfigurationSection("trusted")?.getKeys(false) == null) return null

        val trustedPlayer = ArrayList<String>()
        var chunkOwner: String? = null

        for (name: String in Plugin.getPlugin()?.config?.getConfigurationSection("claim")?.getKeys(false)!!) {
            for (chunkKey: String in Plugin.getPlugin()?.config?.getStringList("claim.$name")!!) {
                if (chunkKey == key) chunkOwner = name
            }
        }

        for (trusted: String in Plugin.getPlugin()?.config?.getStringList("trusted.$chunkOwner")!!) {
            trustedPlayer.add(trusted)
        }

        return trustedPlayer.contains(player.name)
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

    private fun getOwnerOffline(key: String): OfflinePlayer? {
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
}