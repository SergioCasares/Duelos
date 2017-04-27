package me.djbiokinetix.comandos.extern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;
import me.djbiokinetix.utils.Conexiones;

public class LobbyCommand implements CommandExecutor {

	public Main main;
	
	public LobbyCommand(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				p.sendMessage(main.c("&8[&6Code&8] &7Enviando al &bLobby&7..."));
				main.getLogger().info("[Socket] "+p.getName()+": /"+cmd.getLabel());
				Conexiones.bungeeLobby(p);
			}
			
		} else {
			return true;
		}
		return false;
	}

}
