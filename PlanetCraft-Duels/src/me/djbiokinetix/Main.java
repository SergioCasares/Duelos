package me.djbiokinetix;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
import me.djbiokinetix.utils.DiaInfinito;
import me.djbiokinetix.utils.Localizaciones;

public class Main extends JavaPlugin {
	
	PluginManager pm = Bukkit.getPluginManager();
	Messenger messenger = Bukkit.getMessenger();
	public Main main;
	public HashMap<String, String> duelo = new HashMap<>();
	public HashMap<Player, Integer> activo = new HashMap<>();
	public ConcurrentHashMap<Player, Player> peticion = new ConcurrentHashMap<>();
	public HashMap<Integer, Integer> arenas = new HashMap<>();
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
		
		messenger.registerOutgoingPluginChannel(this, "BungeeCord");
		
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
		cargarLocalizacion();
		cargarConfiguracion();
		
		pm.registerEvents(new EventosJugador(this), this);
		pm.registerEvents(new MundoEventos(this), this);
		
		new DiaInfinito(this).runTaskTimer(this, 20, 20);
		
	}

	@SuppressWarnings("resource")
	public void bungeeLobby(Player jugador) {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream datos = new DataOutputStream(bytes);
		
		try {
			datos.writeUTF("Connect");
			datos.writeUTF("Lobby");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		jugador.sendPluginMessage(this, "BungeeCord", bytes.toByteArray());
		return;
		
	}

	public void match() {
		if ((match == null) && (match.size() < 2)) {
			return;
		}
		if ((match != null) && (match.size() == 2)) {
			Random random = new Random();
			Player jugador_1 = (Player) match.get(random.nextInt(match.size()));
			Player jugador_2 = (Player) match.get(random.nextInt(match.size()));
			if ((!match.contains(jugador_1)) && (!match.contains(jugador_2))) {
				return;
			}
			if (jugador_1 == jugador_2) {
				match();
			} else if ((match.contains(jugador_1)) && (match.contains(jugador_2)) && (jugador_1 != jugador_2)) {
				int arena = randomArena();
				arenas.put(arena, 1);
				//empezar juego
			}
		}
	}
	
	public int randomArena() {
		for (Map.Entry<Integer, Integer> x : arenas.entrySet()) {
			int i = new Random().nextInt(x.getKey()); i++;
			if (i == x.getKey()) {
				return x.getKey();
			}
		}
		return 0;
	}
	
	public void comprobar(final Player jugador) {
		TID=Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				if (instancia().activo.get(jugador) != null) {
					return;
				} else {
					jugador.sendMessage(instancia().c("&8[&6Code&8] &7Duelo no encontrado dentro del tiempo de espera, has sido removido del match."));
					instancia().removerMatch(jugador);
					return;
				}
			}
		}, 30*20);
	}
	
	public void añadirMatch(Player jugador) {
		if (!match.contains(jugador)) {
			match.add(jugador);
			jugador.sendMessage(c("&8[&6Code&8] &7Buscando contrincantes..."));
			comprobar(jugador);
			return;
		} else if (match.contains(jugador)) {
			removerMatch(jugador);
			Bukkit.getScheduler().cancelTask(TID);
			return;
		}
	}
	
	public void removerMatch(Player jugador) {
		if (match.contains(jugador)) {
			match.remove(jugador);
			return;
		} else {
			jugador.sendMessage(c("&8[&6Code&8] &7Ya habias sido removido del match."));
			return;
		}
	}
	
	public String getColoredPlayerListName(String name, String displayName) {
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
	
	public void cargarLocalizacion() {
		try {
			String configuracion = getConfig().getString("funciones.teleport.localizacion.mundo");
			World world = Bukkit.getWorld(getConfig().getString("funciones.teleport.localizacion.mundo"));
			double x = getConfig().getDouble("funciones.teleport.localizacion.x");
			double y = getConfig().getDouble("funciones.teleport.localizacion.y");
			double z = getConfig().getDouble("funciones.teleport.localizacion.z");
			float yaw = Float.parseFloat(getConfig().getString("funciones.teleport.localizacion.yaw"));
			float pitch = Float.parseFloat(getConfig().getString("funciones.teleport.localizacion.pitch"));
			if(configuracion!=null)
			{
				localizacion=new Location(world,x,y,z,yaw,pitch);
			}
		} catch (Exception ex) {}
	}
	
	public void cargarConfiguracion() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public void salvarLocalizacion(Location loc) {
		getConfig().set("funciones.teleport.localizacion",loc);
		getConfig().set("funciones.teleport.localizacion.mundo",loc.getWorld().getName());
		getConfig().set("funciones.teleport.localizacion.x",Double.valueOf(loc.getX()));
		getConfig().set("funciones.teleport.localizacion.y",Double.valueOf(loc.getY()));
		getConfig().set("funciones.teleport.localizacion.z",Double.valueOf(loc.getZ()));
		getConfig().set("funciones.teleport.localizacion.yaw",Float.valueOf(loc.getYaw()));
		getConfig().set("funciones.teleport.localizacion.pitch",Float.valueOf(loc.getPitch()));
		saveConfig();
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