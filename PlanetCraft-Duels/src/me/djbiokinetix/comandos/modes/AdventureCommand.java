package me.djbiokinetix.comandos.modes;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class AdventureCommand implements CommandExecutor {

	public Main main;
	
	public AdventureCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				if (p.hasPermission("planetcraft-duels.gamemode.adventure")) {
					p.sendMessage(main.c("&8[&6Code&8] &7Modo de juego establecido en &baventura&7!"));
					p.setGameMode(GameMode.ADVENTURE);
				}
			}
			
		} else {
			return true;
		}
		return false;
	}

}
