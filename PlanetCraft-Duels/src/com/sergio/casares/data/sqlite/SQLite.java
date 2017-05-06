package com.sergio.casares.data.sqlite;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.sergio.casares.data.Database;

public class SQLite extends Database {

	private final String localizacionDatabase;
	
	public SQLite(String localizacionDatabase) {
		this.localizacionDatabase = localizacionDatabase;
	}
	
	@Override
	public Connection abrirConexion() throws SQLException, ClassNotFoundException {
		if (checarConexion()) {
			return conexion;
		}
		File directorio = new File("sqlite-db/");
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		File fila = new File(directorio, localizacionDatabase);
		if (!(fila.exists())) {
			try {
				fila.createNewFile();
			} catch (IOException e) {
				System.out.println("No se puede crear la base de datos!");
			}
		}
		Class.forName("org.sqlite.JDBC");
		conexion = (Connection) DriverManager.getConnection("jdbc:sqlite:"+directorio+"/"+localizacionDatabase);
		return conexion;
	}
	
}
