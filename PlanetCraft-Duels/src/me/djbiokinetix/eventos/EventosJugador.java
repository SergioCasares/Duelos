package me.djbiokinetix.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.djbiokinetix.Main;

public class EventosJugador implements Listener {

	public Main main;
	
	public EventosJugador(Main instancia) {
		main = instancia;
	}
	
	@EventHandler
	public void onLog(PlayerLoginEvent e) {
		e.setKickMessage("&8[&6Code&8] &7Modo de mantenimiento.");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if ((p.isOp())||(p.hasPermission("planetcraft-duels.message.join"))) {
			e.setJoinMessage(main.c("&8[&6Code&8] &e"+p.getName()+" &8» &7ha entrado al servidor."));
			return;
		}
		e.setJoinMessage(null);
		return;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if ((p.isOp())||(p.hasPermission("planetcraft-duels.message.quit"))) {
			e.setQuitMessage(main.c("&8[&6Code&8] &e"+p.getName()+" &8» &7ha salido del servidor."));
			return;
		}
		e.setQuitMessage(null);
		return;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if ((p.isOp())&&(p.hasPermission("planetcraft-duels.chat.owner"))) {
			e.setFormat(main.c("&8[&4Dueño&8] &7"+p.getName()+" &8» &e"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.operador")) {
			e.setFormat(main.c("&8[&4Operador&8] &7"+p.getName()+" &8» &e"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.developer")) {
			e.setFormat(main.c("&8[&4Dev&8] &7"+p.getName()+" &8» &a"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.admin")) {
			e.setFormat(main.c("&8[&4Admin&8] &7"+p.getName()+" &8» &a"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.subadmin")) {
			e.setFormat(main.c("&8[&aSubAdmin&8] &7"+p.getName()+" &8» &b"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.moderador")) {
			e.setFormat(main.c("&8[&2Moderador&8] &7"+p.getName()+" &8» &b"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.submoderador")) {
			e.setFormat(main.c("&8[&5SubModerador&8] &7"+p.getName()+" &8» &b"+e.getMessage()));
			return;
		} else if (p.hasPermission("planetcraft-duels.chat.ayudante")) {
			e.setFormat(main.c("&8[&6Ayudante&8] &7"+p.getName()+" &8» &b"+e.getMessage()));
			return;
		}
		e.setFormat(main.c("&7"+p.getName()+" &8» &f"+e.getMessage()));
	}
	
}
