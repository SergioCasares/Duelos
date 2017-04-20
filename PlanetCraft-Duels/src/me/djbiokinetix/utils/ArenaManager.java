package me.djbiokinetix.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class ArenaManager {

	public Main main;
	
	private static ArenaManager am;
	
	private final Map<UUID, Location> locs = new HashMap<UUID, Location>();
	
	private final List<Arena> arenas = new ArrayList<Arena>();
	
	private int arenaSize = 0;
	
	private ArenaManager() {}
	
	public static ArenaManager getManager() {
		if (am == null) {
			am = new ArenaManager();
		}
		return am;
	}
	
	public Arena getArena(int i) {
		for (Arena a : arenas) {
			if (a.getId() == i) {
				return a;
			}
		}
		return null;
	}
	
	public void addPlayer(Player p, int i) {
		Arena a = getArena(i);
		if (a == null) {
			p.sendMessage(main.c("&8[&6Code&8] &7Arena invalida."));
		}
		if (isInGame(p)) {
			p.sendMessage(main.c("&8[&6Code&8] &7No puedes entrar a mas de 1 duelo."));
		}
		a.getPlayers().add(p.getUniqueId());
		locs.put(p.getUniqueId(), p.getLocation());
		p.teleport(a.spawn);
	}
	
	public void removePlayer(Player p) {
		Arena a = null;
		for (Arena arena : arenas) {
			if (arena.getPlayers().contains(p.getUniqueId()))
				a = arena;
		}
		
		if (a == null) {
			p.sendMessage(main.c("&8[&6Code&8] &7Operacion invalida."));
			return;
		}
		
		a.getPlayers().remove(p.getName());
		
		p.teleport(locs.get(p.getUniqueId()));
		locs.remove(p.getUniqueId());
		
		p.setFireTicks(0);
		p.setHealth(20);
		p.getActivePotionEffects().clear();
		
	}
	
	public Arena createArena(Location l) {
		arenaSize++;
		Arena a = new Arena(l, arenaSize);
		arenas.add(a);
		return a;
	}
	
	public boolean isInGame(Player p) {
		for (Arena a : arenas) {
			if (a.getPlayers().contains(p.getUniqueId()))
				return true;
		}
		return false;
	}
	
}
