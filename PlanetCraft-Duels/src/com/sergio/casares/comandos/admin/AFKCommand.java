package com.sergio.casares.comandos.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;
import com.sergio.casares.utils.Controlador;

public class AFKCommand implements CommandExecutor {

	public Main main;
	
	public AFKCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				
				if (main.afk.containsKey(p.getName())) {
					p.getServer().broadcastMessage(main.c("&8[&6Code&8] &a"+p.getName()+" &7ya no esta ausente!"));
					p.setPlayerListName(Controlador.getColoredPlayerListName(p.getName(), p.getDisplayName()));
					main.afk.remove(p.getName());
					return true;
				} else {
					main.afk.put(p.getName(), p.getDisplayName());
					p.getServer().broadcastMessage(main.c("&8[&6Code&8] &a"+p.getName()+" &7esta ausente!"));
					p.setPlayerListName(main.c("&8[&cAFK&8] &f"+p.getName()));
					return true;
				}
				
			}
			
		} else {
			return true;
		}
		return false;
	}

}
