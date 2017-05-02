package com.sergio.casares.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

public class TPCommand implements CommandExecutor {

	public Main main;
	
	public TPCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				p.sendMessage(main.c("&8[&6Code&8] &7Uso correcto: &c/tp <Player>"));
			}
			
			if (args.length > 0) {

					
				Player target = Bukkit.getPlayer(args[0]);
				
				if (p == target) {
					p.sendMessage(main.c("&8[&6Code&8] &7No puedes teletransportarte a ti mismo."));
					return true;
				}
				
				if (target == null) {
					p.sendMessage(main.c("&8[&6Code&8] &7Ese jugador esta offline!"));
					return true;
				}
				
				if (p.hasPermission("planetcraft-duels.teleport.tp")) {
					p.teleport(target);
					p.sendMessage(main.c("&8[&6Code&8] &7Teletransportando con &b"+target.getName()));
					return false;
				} else {
					p.sendMessage(main.c("&8[&6Code&8] &7No tienes permisos."));
					return true;
				}
				
			}
			
		} else {
			return true;
		}
		return false;
	}

}
