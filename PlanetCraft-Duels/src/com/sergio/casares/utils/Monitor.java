package com.sergio.casares.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

/**
 * 
 * @author DJBiokinetix
 *
 */

public class Monitor {

	//Invocar clase Main.
	public Main main;
	
	/**
	 * 
	 * @param instancia
	 */
	//Crear instancia de Main.
	public Monitor(Main instancia) {
		main = instancia;
	}
	
	/**
	 * 
	 * @param p
	 * @param file
	 */
	//En espera de ser ejecutado para salvar localizacion de Player en una Fila.
	public void salvarLocalizacion(Player p, File file) {
		//Si la fila es nula retornar e interrumpir.
		if (file == null) {
			main.getLogger().info("[Localizacion] Fila no encontrada.");
			return;
		}
		//Si la fila existe borrar para crear una nueva.
		if (file.exists()) {
			//Borrar fila.
			file.delete();
			//Sin retorno para lecutra de nueva fila.
		}
		//Leer nueva fila.
		FileConfiguration configuracion = YamlConfiguration.loadConfiguration(file);
		//Obtener localizacion y establecer.
		Location loc = p.getLocation();
		configuracion.set("Localizacion", escribirLocalizacion(loc));
		//Comprobar errores al escribir en la fila.
		try {
			//Sin error salvar, retornar e interrumpir.
			configuracion.save(file);
			main.getLogger().info("[Juego] Temporal: Escribiendo fila en "+file);
			return;
		} catch (IOException e) {
			//Con error retornar e interrumpir.
			main.getLogger().info("[Juego] Error: la fila "+file+" no pudo ser escrita por el servidor.");
			return;
		}
	}

	/**
	 * 
	 * @param p
	 * @param file
	 */
	//Metodo ejecutor para restablecer localizaciones.
	public void restablecerLocalizacion(Player p, File file) {
		//Si la fila es nula interrumpir metodo y retornar.
		if (file == null) {
			main.getLogger().info("[Localizacion] Fila no encontrada.");
			return;
		}
		//Si la fila no existe interrumpir metodo y retornar.
		if (!file.exists()) {
			main.getLogger().info("[Localizacion] Fila no encontrada.");
			return;
		}
		//Si el jugador es nulo interrumpir metodo y retornar.
		if (p == null) {
			main.getLogger().info("[Juego] Jugador no encontrado.");
			//Borrar fila.
			file.delete();
			return;
		}
		//Llamar desde configuracion creada.
		FileConfiguration configuracion = YamlConfiguration.loadConfiguration(file);
		//Si la configuracion contiene "Localizacion" continuar.
		if (configuracion.contains("Localizacion")) {
			//Teletransportar jugador al acabar el duelo o llamar al metodo ejecutor.
			p.teleport(localizacionEscrita(configuracion.getString("Localizacion")));
			//Sin retorno para ejecutar lo siguiente.
		}
		//Una vez pasado el evento de teletransporte borrar fila si aun existe.
		if (file.exists()) {
			//Borrar fila.
			file.delete();
			//Devolver retorno para dejar de ejecutar el metodo.
			return;
		}
	}
	
	/**
	 * 
	 * @param l
	 * @return
	 */
	//Cuando sea llamado el metodo constructor escribir una localizacion.
	public String escribirLocalizacion(Location l) {
		//Obtener el nombre del mundo, localizaciones basicas y avanzadas.
		String world = l.getWorld().getName();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		//Retornar en una localizacion y acabar el constructor.
		return world+","+x+","+y+","+z+","+yaw+","+pitch;
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	//Cuando sea llamado el metodo constructor retornar localizacion en String.
	public Location localizacionEscrita(String string) {
		//Obtener el nombre del mundo, localizaciones basicas y avanzadas en forma de String.
		String[] loc = string.split(",");
		World world = Bukkit.getWorld(loc[0]);
		Double x = Double.parseDouble(loc[1]);
		Double y = Double.parseDouble(loc[2]);
		Double z = Double.parseDouble(loc[3]);
		Float yaw = Float.parseFloat(loc[4]);
		Float pitch = Float.parseFloat(loc[5]);
		//Retornar estableciendo una localizacion con las variables transformadas.
		return new Location(world, x, y, z, yaw, pitch);
		//Fin del constructor.
	}
	
}
