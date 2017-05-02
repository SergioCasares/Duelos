package com.sergio.casares.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.sergio.casares.Main;

public class MundoEventos implements Listener {

	public Main main;
	
	public MundoEventos(Main instancia) {
		main = instancia;
	}
	
	@EventHandler
	public void onMundo(WeatherChangeEvent e) {
		main.getLogger().info("Quitando: Lluvia.");
		e.setCancelled(true);
	}
	
}
