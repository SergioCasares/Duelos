package com.sergio.casares.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sergio.casares.Main;

public class Configuraciones {

	public static Main main;
	
	public Configuraciones(Main instancia) {
		main = instancia;
	}
	
	public static void cargarLocalizacion() {
		try {
			String configuracion = main.getConfig().getString("funciones.teleport.localizacion.mundo");
			World world = Bukkit.getWorld(main.getConfig().getString("funciones.teleport.localizacion.mundo"));
			double x = main.getConfig().getDouble("funciones.teleport.localizacion.x");
			double y = main.getConfig().getDouble("funciones.teleport.localizacion.y");
			double z = main.getConfig().getDouble("funciones.teleport.localizacion.z");
			float yaw = Float.parseFloat(main.getConfig().getString("funciones.teleport.localizacion.yaw"));
			float pitch = Float.parseFloat(main.getConfig().getString("funciones.teleport.localizacion.pitch"));
			if(configuracion!=null)
			{
				main.localizacion=new Location(world,x,y,z,yaw,pitch);
			}
		} catch (Exception ex) {}
	}
	
	public static void cargarConfiguracion() {
		main.getConfig().options().copyDefaults(true);
		main.saveConfig();
	}
	
	public static void salvarLocalizacion(Location loc) {
		main.getConfig().set("funciones.teleport.localizacion",loc);
		main.getConfig().set("funciones.teleport.localizacion.mundo",loc.getWorld().getName());
		main.getConfig().set("funciones.teleport.localizacion.x",Double.valueOf(loc.getX()));
		main.getConfig().set("funciones.teleport.localizacion.y",Double.valueOf(loc.getY()));
		main.getConfig().set("funciones.teleport.localizacion.z",Double.valueOf(loc.getZ()));
		main.getConfig().set("funciones.teleport.localizacion.yaw",Float.valueOf(loc.getYaw()));
		main.getConfig().set("funciones.teleport.localizacion.pitch",Float.valueOf(loc.getPitch()));
		main.saveConfig();
	}
	
}
