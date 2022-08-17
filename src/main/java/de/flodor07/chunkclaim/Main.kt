package de.flodor07.chunkclaim

import de.flodor07.chunkclaim.commands.EmptyTabCompleter
import de.flodor07.chunkclaim.commands.admin.InfoCommand
import de.flodor07.chunkclaim.commands.admin.RemoveClaimCommand
import de.flodor07.chunkclaim.commands.claim.ClaimCommand
import de.flodor07.chunkclaim.commands.claim.UnClaimCommand
import de.flodor07.chunkclaim.commands.trust.TrustCommand
import de.flodor07.chunkclaim.commands.trust.TrustTabCompleter
import de.flodor07.chunkclaim.commands.trust.UnTrustCommand
import de.flodor07.chunkclaim.commands.trust.UnTrustTabCompleter
import de.flodor07.chunkclaim.listeners.OnBuildListener
import de.flodor07.chunkclaim.listeners.OnRTPFindLocation
import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        Plugin.setPlugin(this)
        getCommand("claim")?.setExecutor(ClaimCommand())
        getCommand("claim")?.setTabCompleter(EmptyTabCompleter())

        getCommand("unclaim")?.setExecutor(UnClaimCommand())
        getCommand("unclaim")?.setTabCompleter(EmptyTabCompleter())

        getCommand("ctrust")?.setExecutor(TrustCommand())
        getCommand("ctrust")?.setTabCompleter(TrustTabCompleter())

        getCommand("cuntrust")?.setExecutor(UnTrustCommand())
        getCommand("cuntrust")?.setTabCompleter(UnTrustTabCompleter())

        getCommand("claiminfo")?.setExecutor(InfoCommand())
        getCommand("claiminfo")?.setTabCompleter(EmptyTabCompleter())

        getCommand("removeclaim")?.setExecutor(RemoveClaimCommand())
        getCommand("removeclaim")?.setTabCompleter(EmptyTabCompleter())
        server.pluginManager.registerEvents(OnBuildListener(), this)

        if(isClass("me.SuperRonanCraft.BetterRTP.references.customEvents.RTP_FindLocationEvent")) {
           server.pluginManager.registerEvents(OnRTPFindLocation(), this)
        } else {
           Plugin.log("BetterRTP not installed skipping...")
        }

        Plugin.log("Plugin loaded")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun isClass(className: String?): Boolean {
        return try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}