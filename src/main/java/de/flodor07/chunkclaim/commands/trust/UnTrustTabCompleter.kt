package de.flodor07.chunkclaim.commands.trust

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class UnTrustTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if(sender is ConsoleCommandSender) return null
        val player:Player = sender as Player
        val list = ArrayList<String>()

        if(args.size == 1) {
            if(Plugin.getPlugin()?.config?.getList("trusted.${player.name}") == null) return list
            for (targetName in Plugin.getPlugin()?.config?.getList("trusted.${player.name}")!!) {
                list.add(targetName.toString())
            }
            return list
        }


        return list
    }
}