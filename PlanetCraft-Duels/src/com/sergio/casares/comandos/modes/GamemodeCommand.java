package com.sergio.casares.comandos.modes;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

public class GamemodeCommand implements CommandExecutor {

	public Main main;
	
	public GamemodeCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				p.sendMessage(main.c("&8[&6Code&8] &7Uso correcto: /gm <0|1|2> [s|c|a]"));
			}
			
			if (args.length > 0) {
				
				if (args[0].equalsIgnoreCase("0")) {
					if (p.hasPermission("planetcraft-duels.gamemode.survival")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &bsurvival&7!"));
						p.setGameMode(GameMode.SURVIVAL);
					}
				}
				if (args[0].equalsIgnoreCase("1")) {
					if (p.hasPermission("planetcraft-duels.gamemode.creative")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &bcreativo&7!"));
						p.setGameMode(GameMode.CREATIVE);
					}
				}
				if (args[0].equalsIgnoreCase("2")) {
					if (p.hasPermission("planetcraft-duels.gamemode.adventure")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &baventura&7!"));
						p.setGameMode(GameMode.ADVENTURE);
					}
				}
				
				if (args[0].equalsIgnoreCase("s")) {
					if (p.hasPermission("planetcraft-duels.gamemode.survival")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &bsurvival&7!"));
						p.setGameMode(GameMode.SURVIVAL);
					}
				}
				if (args[0].equalsIgnoreCase("c")) {
					if (p.hasPermission("planetcraft-duels.gamemode.creative")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &bcreativo&7!"));
						p.setGameMode(GameMode.CREATIVE);
					}
				}
				if (args[0].equalsIgnoreCase("a")) {
					if (p.hasPermission("planetcraft-duels.gamemode.adventure")) {
						p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &baventura&7!"));
						p.setGameMode(GameMode.ADVENTURE);
					}
				}
				
			}
			
		} else {
			return true;
		}
		return false;
	}
}
