package de.flodor07.chunkclaim.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Plugin {
    companion object {
        private var plugin: JavaPlugin? = null
        private const val prefix: String = "§8[§2Claim§8]§r"

        fun getPlugin(): JavaPlugin? {
            return plugin
        }
        fun setPlugin(plugin: JavaPlugin) {
            Companion.plugin = plugin
        }

        val config = plugin?.config
        fun saveConfig() {
            plugin?.saveConfig()
        }

        fun getPlayer(player: Player, args: Array<out String>, index: Int = 0) : Player {
            if(args.isEmpty()) return player
            if(args.size != index + 1) return player

            if(Bukkit.getPlayer(args[index]) != null){
                return Bukkit.getPlayer(args[index])!!
            }

            return player
        }

        fun checkPermission(sender: CommandSender, permission: String): Boolean {
            return if (sender.hasPermission(permission)) {
                true
            } else {
                sender.sendMessage("$prefix ${ChatColor.RED}Du hast keine Berechtigung für diesen Command")
                false
            }
        }



        fun sendMessage(sender: CommandSender, message: String) {
            sender.sendMessage("$prefix ${ChatColor.GRAY}$message")
        }
        fun sendMessage(player: Player, message: String) {
            player.sendMessage("$prefix ${ChatColor.GRAY}$message")
        }

        fun sendMessage(player: Player, content: String, hoverEvent: HoverEvent<*>?, clickEvent: ClickEvent?) {
            val message: TextComponent =
                Component.text(prefix + ChatColor.GRAY + " " + content)
                    .clickEvent(clickEvent)
                    .hoverEvent(hoverEvent)
            player.sendMessage(message)
        }

        fun sendEmptyMessage(player: Player) {
            player.sendMessage(" ")
        }

        fun sendEmptyMessage(sender: CommandSender) {
            sender.sendMessage(" ")
        }

        fun sendMessageToSenderAPlayer(commandSender: Player, player: Player, message2Sender: String, message: String) {
            if(commandSender == player) sendMessage(commandSender, message)
            if(commandSender != player) {
                sendMessage(commandSender, message2Sender)
                sendMessage(player, message)
            }
        }

        fun sendNoPermissionMessage(sender: CommandSender, message: String) {
            sender.sendMessage("$prefix ${ChatColor.RED}You are not allowed to use this command.")
        }
        fun sendNoPermissionMessage(player: Player, message: String) {
            player.sendMessage("$prefix ${ChatColor.RED}You are not allowed to use this command.")
        }

        fun error(message: String) {
            Bukkit.getServer().consoleSender.sendMessage("$prefix ${ChatColor.RED}ERROR §8| §7$message")
        }
        fun info(message: String) {
            Bukkit.getServer().consoleSender.sendMessage("$prefix ${ChatColor.GREEN}INFO §8| §7$message")
        }
        fun log(message: String) {
            Bukkit.getServer().consoleSender.sendMessage("$prefix ${ChatColor.YELLOW}LOG §8| §7$message")
        }

    }

}