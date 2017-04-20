package me.djbiokinetix.comandos.extern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class MensajePrivado implements CommandExecutor {

	public Main main;
	
	public MensajePrivado(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				p.sendMessage(main.c("&8[&6Code&8] &7Uso correcto: &c/m <Jugador> <Mensaje>"));
			}
			
			if (args.length > 0) {
				//Mensaje privado...
			}
			
		} else {
			return true;
		}
		return false;
	}

}
