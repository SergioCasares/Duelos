package me.djbiokinetix.comandos.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class TPHCommand implements CommandExecutor {

	public Main main;
	
	public TPHCommand(Main instancia) {
		
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
				
				if (target == p) {
					p.sendMessage(main.c("&8[&6Code&8] &7No puedes teletransportarte a ti mismo."));
					return true;
				}
				
				if (target == null) {
					p.sendMessage(main.c("&8[&6Code&8] &7Ese jugador esta offline!"));
					return true;
				}
				
				if (p.hasPermission("planetcraft-duels.teleport.tp")) {
					target.teleport(p);
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
