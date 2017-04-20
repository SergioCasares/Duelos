package me.djbiokinetix.comandos.extern;

import org.bukkit.Bukkit;
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
			
			if (args.length == 1) {
				p.sendMessage(main.c("&8[&6Code&8] &7Uso correcto: &c/m <Jugador>"));
			}
			
			if (args.length >= 2) {
				if (Bukkit.getPlayer(args[0])!=null) {
					Player target = Bukkit.getPlayer(args[0]);
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++) {
						if (i > 1) {
							sb.append(" ");
						}
						sb.append(args[i]);
					}
					String checar = sb.toString();
					if (checar == null) {
						p.sendMessage(main.c("&8[&6Code&8] &7El mensaje no puede estar vacio!"));
					} else if (checar != null) {
						p.sendMessage(main.c("&8[&6Code&8] &7Mensaje para &a"+target.getName()+"\n&8[&6Ex&8] &7Mensaje: &f"+sb));
						target.sendMessage(main.c("&8[&6Code&8] &7Mensaje de &a"+p.getName()+"\n&8[&6Ex&8] &7Mensaje: &f"+sb));
			            main.ultimo.put(target.getName(), p.getName());
					}
				} else {
					
				}
			}
			
		} else {
			return true;
		}
		return false;
	}

}
