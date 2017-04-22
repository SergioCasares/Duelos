package me.djbiokinetix;

import java.io.DataOutputStream;
import java.util.HashMap;

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

public class Main extends JavaPlugin {
	
	PluginManager pm = Bukkit.getPluginManager();
	Messenger messenger = Bukkit.getMessenger();
	public HashMap<String, String> ultimo = new HashMap<>();
	public HashMap<String, String> afk;
	public Location localizacion;
	
	@Override
	public void onEnable() {
		
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
	
	@Override
	public void onDisable() {
		reloadConfig();
		saveConfig();
	}
	
}
