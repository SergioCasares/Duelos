package me.djbiokinetix.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public class Arena {

	private final int id;
	public final Location spawn;
	private final List<UUID> jugadores = new ArrayList<UUID>();
	
	public Arena(Location spawn_instancia, int id_instancia) {
		spawn = spawn_instancia;
		id = id_instancia;
	}
	
	public int getId() {
		return id;
	}
	
	public List<UUID> getPlayers() {
		return jugadores;
	}
	
}
