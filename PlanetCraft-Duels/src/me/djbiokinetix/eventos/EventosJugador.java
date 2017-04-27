package me.djbiokinetix.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.djbiokinetix.Main;
import me.djbiokinetix.utils.Controlador;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class EventosJugador implements Listener {

	public Main main;
	
	public EventosJugador(Main instancia) {
		main = instancia;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (main.afk.containsKey(p.getName())) {
			int movX = e.getFrom().getBlockX() - e.getTo().getBlockX();
			int movZ = e.getFrom().getBlockZ() - e.getTo().getBlockZ();
			if ((Math.abs(movX) >= 1) || (Math.abs(movZ) >= 1)) {
				p.getServer().broadcastMessage(main.c("&8[&6Code&8] &a"+p.getName()+" &7ya no esta ausente!"));
				p.setPlayerListName(Controlador.getColoredPlayerListName(p.getName(), p.getDisplayName()));
				main.afk.remove(p.getName());
			}
		}
	}
	
	@EventHandler
	public void onLog(PlayerLoginEvent e) {
		e.setKickMessage(main.c("&8[&6Code&8] &7Modo de mantenimiento. (Acceso: &aSolo staff &7[&bSkype&7])"));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if ((p.isOp())||(p.hasPermission("planetcraft-duels.message.join"))) {
			e.setJoinMessage(main.c("&8[&6Code&8] &e"+p.getName()+" &8» &7ha entrado al servidor."));
		}
		try {
			if (main.getConfig().getString("funciones.teleport.localizacion.mundo")!=null) {
				p.teleport(main.localizacion);
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getActivePotionEffects().clear();
				p.sendMessage(main.c("&8[&6Code&8] [&cEvent&8] &7Teletransportado automaticamente al &bspawn &7del servidor."));
			} else {
				if (p.isOp()) {
					p.sendMessage(main.c("&8[&6Code&8] &7El &bspawn &7no ha sido establecido."));
				}
			}
		}catch(Exception ex) {}
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
	public void onMensaje(PlayerChatEvent e) {
		Player p = e.getPlayer();
		if (main.afk.containsKey(p.getName())) {
			p.getServer().broadcastMessage(main.c("&8[&6Code&8] &a"+p.getName()+" &7ya no esta ausente!"));
			p.setPlayerListName(Controlador.getColoredPlayerListName(p.getName(), p.getDisplayName()));
			main.afk.remove(p.getName());
		}
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
		} else if (p.hasPermission("planetcraft-duels.chat.color")) {
			e.setFormat(main.c("&7"+p.getName()+" &8» &f"+e.getMessage()));
		} else {
			e.setFormat(ChatColor.GRAY+p.getName()+ChatColor.DARK_GRAY+" » "+ChatColor.WHITE+e.getMessage());
		}
	}
	
}
