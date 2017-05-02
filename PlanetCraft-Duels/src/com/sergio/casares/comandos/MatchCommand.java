package com.sergio.casares.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

public class MatchCommand implements CommandExecutor {

	public Main main;
	
	public MatchCommand(Main instancia) {
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
