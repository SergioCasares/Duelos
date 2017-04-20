package me.djbiokinetix.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import me.djbiokinetix.Main;

public class DiaInfinito extends BukkitRunnable {

	public Main main;
	
	public DiaInfinito(Main instancia) {
		main = instancia;
	}
	
	@Override
	public void run() {
		World w = Bukkit.getWorld("world");
		w.setTime(6000L);
	}
	
}
