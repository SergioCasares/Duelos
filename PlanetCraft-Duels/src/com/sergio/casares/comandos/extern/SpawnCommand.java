package com.sergio.casares.comandos.extern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;
import com.sergio.casares.utils.Configuraciones;

public class SpawnCommand implements CommandExecutor {

	public Main main;
	
	public SpawnCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				if (main.getConfig().getString("funciones.teleport.localizacion.mundo")!=null) {
					p.teleport(main.localizacion);
					p.sendMessage(main.c("&8[&6Code&8] &7Teletransportando al &bspawn&7."));
					return false;
				} else {
					p.sendMessage(main.c("&8[&6Code&8] &7Spawn no establecido."));
					return true;
				}
			}
			
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("set")) {
					if (p.hasPermission("planetcraft-duels.teleport.set.spawn")) {
						main.localizacion = p.getLocation();
						Configuraciones.salvarLocalizacion(main.localizacion);
						String mundo = main.getConfig().getString("funciones.teleport.localizacion.mundo");
						String x = main.getConfig().getString("funciones.teleport.localizacion.x");
						String y = main.getConfig().getString("funciones.teleport.localizacion.y");
						String z = main.getConfig().getString("funciones.teleport.localizacion.z");
						p.sendMessage(main.c("&8[&6Code&8] &7Localizacion establecida."));
						p.sendMessage(main.c("&8[&6Code&8] &2W&7: &b"+mundo));
						p.sendMessage(main.c("&8[&6Code&8] &2X&7: &b"+x));
						p.sendMessage(main.c("&8[&6Code&8] &2Y&7: &b"+y));
						p.sendMessage(main.c("&8[&6Code&8] &2Z&7: &b"+z));
						return false;
					} else {
						p.sendMessage(main.c("&8[&6Code&8] &7No tienes permisos para ejecutar este comando!"));
						return true;
					}
				}
			}
			
		} else {
			
		}
		return false;
	}

}
