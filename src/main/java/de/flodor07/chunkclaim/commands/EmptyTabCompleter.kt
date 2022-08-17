package de.flodor07.chunkclaim.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter

class EmptyTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        if(sender is ConsoleCommandSender) return null
        val list = ArrayList<String>()

        return list
    }
}