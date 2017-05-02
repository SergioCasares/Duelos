package com.sergio.casares.utils;

import java.io.DataOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.entity.Player;

import com.sergio.casares.Main;

public class Conexiones {

	public static Main main;
	
	public Conexiones(Main instancia) {
		main = instancia;
	}
	
	@SuppressWarnings("resource")
	public static void bungeeLobby(Player jugador) {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream datos = new DataOutputStream(bytes);
		
		try {
			datos.writeUTF("Connect");
			datos.writeUTF("Lobby");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		jugador.sendPluginMessage(main, "BungeeCord", bytes.toByteArray());
		return;
		
	}
	
}
