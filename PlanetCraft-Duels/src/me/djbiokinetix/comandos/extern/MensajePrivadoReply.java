package me.djbiokinetix.comandos.extern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class MensajePrivadoReply implements CommandExecutor {

	public Main main;
	
	public MensajePrivadoReply(Main instancia) {
		main = instancia;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (args.length == 0) {
				p.sendMessage(main.c("&8[&6Code&8] &7Uso correcto: &c/r <Mensaje>"));
			}
			
			if (args.length >= 1) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					if (i > 0) {
						sb.append(" ");
					}
					sb.append(args[i]);
				}
				String targetMsg = null;
				if (main.ultimo.containsKey(p.getName())) {
					targetMsg = (String) main.ultimo.get(p.getName());
				} else {
					p.sendMessage(main.c("&8[&6Code&8] &7Nadie te ha enviado un mensaje privado!"));
					return true;
				}
				if (targetMsg != null) {
					String checar = sb.toString();
					if (checar == null) {
						p.sendMessage("&8[&6Code&8] &7La respuesta no puede estar vacia.");
						return false;
					}
					Player target = Bukkit.getPlayer(targetMsg);
					p.sendMessage(main.c("&8[&6Code&8] &7Mensaje para &a"+target.getName()+"\n&8[&6Ex&8] &7Mensaje: &f"+sb));
					target.sendMessage(main.c("&8[&6Code&8] &7Mensaje de &a"+p.getName()+"\n&8[&6Ex&8] &7Mensaje: &f"+sb));
				} else {
					return true;
				}
			}
			
		} else {
			return true;
		}
		return false;
	}

}
