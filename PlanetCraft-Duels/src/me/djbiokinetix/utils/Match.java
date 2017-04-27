package me.djbiokinetix.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.djbiokinetix.Main;

public class Match {

	public Main main;
	
	public Match(Main instancia) {
		main = instancia;
	}
	
	public void match() {
		if ((main.match == null) && (main.match.size() < 2)) {
			return;
		}
		if ((main.match != null) && (main.match.size() == 2)) {
			Random random = new Random();
			Player jugador_1 = (Player) main.match.get(random.nextInt(main.match.size()));
			Player jugador_2 = (Player) main.match.get(random.nextInt(main.match.size()));
			if ((!main.match.contains(jugador_1)) && (!main.match.contains(jugador_2))) {
				return;
			}
			if (jugador_1 == jugador_2) {
				match();
			} else if ((main.match.contains(jugador_1)) && (main.match.contains(jugador_2)) && (jugador_1 != jugador_2)) {
				int arena = Controlador.randomArena();
				Main.arenas.put(arena, 1);
				//empezar juego
			}
		}
	}
	
	public void comprobar(final Player jugador) {
		main.TID=Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			@Override
			public void run() {
				if (main.instancia().activo.get(jugador) != null) {
					return;
				} else {
					jugador.sendMessage(main.instancia().c("&8[&6Code&8] &7Duelo no encontrado dentro del tiempo de espera, has sido removido del match."));
					removerMatch(jugador);
					return;
				}
			}
		}, 30*20);
	}
	
	public void añadirMatch(Player jugador) {
		if (!main.match.contains(jugador)) {
			main.match.add(jugador);
			jugador.sendMessage(main.c("&8[&6Code&8] &7Buscando contrincantes..."));
			comprobar(jugador);
			return;
		} else if (main.match.contains(jugador)) {
			removerMatch(jugador);
			Bukkit.getScheduler().cancelTask(main.TID);
			return;
		}
	}
	
	public void removerMatch(Player jugador) {
		if (main.match.contains(jugador)) {
			main.match.remove(jugador);
			return;
		} else {
			jugador.sendMessage(main.c("&8[&6Code&8] &7Ya habias sido removido del match."));
			return;
		}
	}
	
}
