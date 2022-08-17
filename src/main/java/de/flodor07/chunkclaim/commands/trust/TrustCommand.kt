package de.flodor07.chunkclaim.commands.trust

import de.flodor07.chunkclaim.util.Plugin
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class TrustCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is ConsoleCommandSender) return false
        if(!Plugin.checkPermission(sender, "chunkclaim.trust")) return false

        val trustedPlayer = ArrayList<String>()
        val player:Player = sender as Player


        if (args.isEmpty() || args == null) {
            sendHelp(player)
            return false
        }

        val target: Player? = Bukkit.getPlayer(args[0])
        if(target == null){
            Plugin.sendMessage(player, "§cSpieler '${args[0]}' nicht gefunden")
            return false
        }


        if(Plugin.getPlugin()?.config?.getList("trusted.${player.name}") != null) {
            if(Plugin.getPlugin()?.config?.getList("trusted.${player.name}")!!.isNotEmpty() ) {
                for (targetName in Plugin.getPlugin()?.config?.getList("trusted.${player.name}")!!) {
                    trustedPlayer.add(targetName.toString())
                }

                if(trustedPlayer.contains(target.name)) {
                    Plugin.sendMessage(player, "Du vertraust ${args[0]} schon")
                    return true
                }

                if(target.name == player.name) {
                    Plugin.sendMessage(player, "Du kannst dir nicht selber vertrauen")
                    return true
                }
            }
        }


        trustedPlayer.add(target.name.toString())

        Plugin.getPlugin()?.config?.set("trusted.${player.name}", trustedPlayer)
        Plugin.getPlugin()?.saveConfig()

        Plugin.sendMessage(player, "Du vertraust nun ${target.name}")
        Plugin.sendMessage(target, "${player.name} vertraut dir jetzt")
        return true
    }


    private fun sendHelp(player: Player) {
        Plugin.sendMessage(player, "/trust [Spieler]")
    }
}