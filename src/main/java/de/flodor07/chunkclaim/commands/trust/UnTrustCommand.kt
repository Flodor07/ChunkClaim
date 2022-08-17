package de.flodor07.chunkclaim.commands.trust

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class UnTrustCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.trust")) return false
        if(Plugin.getPlugin()?.config?.getList("trusted.${sender.name}") == null) {
            Plugin.sendMessage(sender, "Du vertraust keinen Spielern")
            return false
        }

        if(Plugin.getPlugin()?.config?.getList("trusted.${sender.name}")!!.isEmpty()) {
            Plugin.sendMessage(sender, "Du vertraust keinen Spielern")
            return false
        }
        val player:Player = sender as Player
        val trustedPlayer = ArrayList<String>()

        if (args.isEmpty() || args == null) {
            sendHelp(player)
            return false
        }

        val target: String = args[0]

        for (targetName in Plugin.getPlugin()?.config?.getList("trusted.${player.name}")!!) {
            trustedPlayer.add(targetName.toString())
        }

        if(!trustedPlayer.contains(target)) {
            Plugin.sendMessage(player, "Du vertraust ${args[0]} schon nicht")
            return true
        }


        trustedPlayer.remove(target.toString())

        Plugin.getPlugin()?.config?.set("trusted.${player.name}", trustedPlayer)
        Plugin.getPlugin()?.saveConfig()

        Plugin.sendMessage(player, "Du vertraust nun nichtmehr ${target}")
        return true
    }


    private fun sendHelp(player: Player) {
        Plugin.sendMessage(player, "/cuntrust [Spieler]")
    }
}