package de.flodor07.chunkclaim.commands.trust

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TrustTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if(sender is ConsoleCommandSender) return null
        val list = ArrayList<String>()
        val p:Player = sender as Player

        val list2 = ArrayList<String>()

        if(Plugin.getPlugin()?.config?.getList("trusted.${p.name}") != null) {
            for (targetName in Plugin.getPlugin()?.config?.getList("trusted.${p.name}")!!) {
                list2.add(targetName.toString())
            }
        }

        for (player:Player in Bukkit.getOnlinePlayers()) {
            if(player.name == p.name ) continue
            if(list2.contains(player.name)) continue

            list.add(player.name)
        }

        return list
    }
}