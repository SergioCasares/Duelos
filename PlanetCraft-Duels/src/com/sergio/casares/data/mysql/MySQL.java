package com.sergio.casares.data.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.sergio.casares.data.Database;

public class MySQL extends Database {

	private final String usuario;
	private final String database;
	private final String pass;
	private final String puerto;
	private final String ip;
	
	/**
	 * Crear nueva instancia de MySQL
	 * 
	 * @param ip / IP del servidor
	 * @param puerto / Puerto del servidor
	 * @param usuario / Usuario de la base de datos o servidor.
	 * @param pass / Contraseña.
	 */
	public MySQL(String ip, String puerto, String usuario, String pass) {
		this(ip, puerto, null, usuario, pass);
	}
	
	public MySQL(String ip, String puerto, String database, String usuario, String pass) {
		this.ip = ip;
		this.puerto = puerto;
		this.database = database;
		this.usuario = usuario;
		this.pass = pass;
	}
	
	@Override
	public Connection abrirConexion() throws SQLException, ClassNotFoundException {
		if (checarConexion()) {
			return conexion;
		}
		String conexionURL = "jdbc:mysql://"+this.ip+":"+this.puerto;
		if (database != null) {
			conexionURL = conexionURL + "/" + this.database;
		}
		Class.forName("com.mysql.jdbc.Driver");
		conexion = (Connection) DriverManager.getConnection(conexionURL, this.usuario, this.pass);
		return conexion;
	}
	
}
