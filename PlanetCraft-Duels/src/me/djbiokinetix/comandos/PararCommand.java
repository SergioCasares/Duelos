package me.djbiokinetix.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class PararCommand implements CommandExecutor {

	public Main main;
	
	public PararCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("Ningun comando coincide.");
			}
		} else {
			return true;
		}
		return false;
	}

}
