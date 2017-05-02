package com.sergio.casares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import com.sergio.casares.comandos.AcceptCommand;
import com.sergio.casares.comandos.DuelCommand;
import com.sergio.casares.comandos.MatchCommand;
import com.sergio.casares.comandos.admin.AFKCommand;
import com.sergio.casares.comandos.admin.ClearChatCommand;
import com.sergio.casares.comandos.admin.CrearCommand;
import com.sergio.casares.comandos.admin.EstablecerCommand;
import com.sergio.casares.comandos.admin.PararCommand;
import com.sergio.casares.comandos.admin.TPCommand;
import com.sergio.casares.comandos.admin.TPHCommand;
import com.sergio.casares.comandos.extern.LobbyCommand;
import com.sergio.casares.comandos.extern.MensajePrivado;
import com.sergio.casares.comandos.extern.MensajePrivadoReply;
import com.sergio.casares.comandos.extern.SpawnCommand;
import com.sergio.casares.comandos.modes.AdventureCommand;
import com.sergio.casares.comandos.modes.CreativeCommand;
import com.sergio.casares.comandos.modes.GamemodeCommand;
import com.sergio.casares.comandos.modes.SurvivalCommand;
import com.sergio.casares.eventos.EventosJugador;
import com.sergio.casares.eventos.MundoEventos;
import com.sergio.casares.utils.Conexiones;
import com.sergio.casares.utils.Configuraciones;
import com.sergio.casares.utils.DiaInfinito;
import com.sergio.casares.utils.Localizaciones;

public class Main extends JavaPlugin {
	
	PluginManager pm = Bukkit.getPluginManager();
	Messenger messenger = Bukkit.getMessenger();
	public Main main;
	public HashMap<String, String> duelo = new HashMap<>();
	public HashMap<Player, Integer> activo = new HashMap<>();
	public ConcurrentHashMap<Player, Player> peticion = new ConcurrentHashMap<>();
	public static HashMap<Integer, Integer> arenas = new HashMap<>();
	public HashMap<Integer, Localizaciones> configArena = new HashMap<>();
	public HashMap<String, String> anon = new HashMap<>();
	public ArrayList<Player> match = new ArrayList<>();
	public HashMap<String, String> ultimo = new HashMap<>();
	public HashMap<String, String> afk;
	public Integer TID;
	public Location localizacion;
	
	@Override
	public void onEnable() {
		
		main = this;
		afk = new HashMap<>();
		
		messenger.registerOutgoingPluginChannel((Plugin) new Conexiones(this), "BungeeCord");
		
		getCommand("duel").setExecutor(new DuelCommand(this));
		getCommand("accept").setExecutor(new AcceptCommand(this));
		getCommand("match").setExecutor(new MatchCommand(this));
		getCommand("lobby").setExecutor(new LobbyCommand(this));
		getCommand("establecer").setExecutor(new EstablecerCommand(this));
		getCommand("parar").setExecutor(new PararCommand(this));
		getCommand("clearchat").setExecutor(new ClearChatCommand(this));
		getCommand("tp").setExecutor(new TPCommand(this));
		getCommand("s").setExecutor(new TPHCommand(this));
		getCommand("gmc").setExecutor(new CreativeCommand(this));
		getCommand("gms").setExecutor(new SurvivalCommand(this));
		getCommand("gma").setExecutor(new AdventureCommand(this));
		getCommand("gm").setExecutor(new GamemodeCommand(this));
		getCommand("msg").setExecutor(new MensajePrivado(this));
		getCommand("r").setExecutor(new MensajePrivadoReply(this));
		getCommand("crear").setExecutor(new CrearCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("afk").setExecutor(new AFKCommand(this));
		
		Configuraciones.cargarLocalizacion();
		Configuraciones.cargarConfiguracion();
		
		pm.registerEvents(new EventosJugador(this), this);
		pm.registerEvents(new MundoEventos(this), this);
		
		new DiaInfinito(this).runTaskTimer(this, 20, 20);
		
	}
	
	public String c(String colorizar) {
		return ChatColor.translateAlternateColorCodes('&', colorizar);
	}
	
	public Main instancia() {
		return main;
	}
	
	@Override
	public void onDisable() {
		reloadConfig();
		saveConfig();
	}
	
}
//Main class