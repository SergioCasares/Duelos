package com.sergio.casares.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

public class ClearChatCommand implements CommandExecutor {

	public Main main;
	
	public ClearChatCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("planetcraft-duels.cc")) {
					for (int i = 0; i < 180; i++) {
						for (Player players : Bukkit.getOnlinePlayers()) {
							players.sendMessage("");
						}
					}
					p.sendMessage(main.c("&8[&6Code&8] &aHaz limpiado el chat."));
				} else {
					for (int i = 0; i < 180; i++) {
						p.sendMessage("");
					}
					p.sendMessage(main.c("&8[&6Code&8] &7Haz limpiado tu chat."));
				}
			}
		} else {
			return true;
		}
		return false;
	}

}
