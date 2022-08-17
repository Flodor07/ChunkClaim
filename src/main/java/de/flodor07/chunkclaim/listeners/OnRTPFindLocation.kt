package de.flodor07.chunkclaim.listeners

import de.flodor07.chunkclaim.util.Plugin
import me.SuperRonanCraft.BetterRTP.references.customEvents.RTP_FindLocationEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnRTPFindLocation : Listener {


    @EventHandler
    fun onRTPSetup(event: RTP_FindLocationEvent) {
        if(getList() == null) return
        if(getList()!!.contains(event.location?.chunk?.chunkKey.toString())) {
            Bukkit.getServer().pluginManager.callEvent(RTP_FindLocationEvent(event.player, event.world))
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
}