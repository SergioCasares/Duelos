package me.djbiokinetix.utils;

import java.util.Map;
import java.util.Random;

import me.djbiokinetix.Main;

public class Controlador {
	
	public static int randomArena() {
		for (Map.Entry<Integer, Integer> x : Main.arenas.entrySet()) {
			int i = new Random().nextInt(x.getKey().intValue()); i++;
			if (i == x.getKey().intValue()) {
				return x.getKey().intValue();
			}
		}
		return 0;
	}
	
	public static String getColoredPlayerListName(String name, String displayName) {
		String nuevoNombre = "&";
		int i = 1;
		while (displayName.charAt(i - 1) != '§') {
			i++;
			if (i > displayName.length()) {
				return name;
			}
		}
		nuevoNombre = nuevoNombre + displayName.charAt(i) + name;
		nuevoNombre = nuevoNombre.replaceAll("&([0-9a-fA-F])", "§$1");
		return nuevoNombre;
	}
	
}
