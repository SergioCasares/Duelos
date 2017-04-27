package me.djbiokinetix;

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

import me.djbiokinetix.comandos.AcceptCommand;
import me.djbiokinetix.comandos.DuelCommand;
import me.djbiokinetix.comandos.MatchCommand;
import me.djbiokinetix.comandos.admin.AFKCommand;
import me.djbiokinetix.comandos.admin.ClearChatCommand;
import me.djbiokinetix.comandos.admin.CrearCommand;
import me.djbiokinetix.comandos.admin.EstablecerCommand;
import me.djbiokinetix.comandos.admin.PararCommand;
import me.djbiokinetix.comandos.admin.TPCommand;
import me.djbiokinetix.comandos.admin.TPHCommand;
import me.djbiokinetix.comandos.extern.LobbyCommand;
import me.djbiokinetix.comandos.extern.MensajePrivado;
import me.djbiokinetix.comandos.extern.MensajePrivadoReply;
import me.djbiokinetix.comandos.extern.SpawnCommand;
import me.djbiokinetix.comandos.modes.AdventureCommand;
import me.djbiokinetix.comandos.modes.CreativeCommand;
import me.djbiokinetix.comandos.modes.GamemodeCommand;
import me.djbiokinetix.comandos.modes.SurvivalCommand;
import me.djbiokinetix.eventos.EventosJugador;
import me.djbiokinetix.eventos.MundoEventos;
import me.djbiokinetix.utils.Conexiones;
import me.djbiokinetix.utils.Configuraciones;
import me.djbiokinetix.utils.DiaInfinito;
import me.djbiokinetix.utils.Localizaciones;

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