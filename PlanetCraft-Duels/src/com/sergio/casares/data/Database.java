package com.sergio.casares.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public abstract class Database {

	protected Connection conexion;
	
	protected Database() {
		conexion = null;
	}
	
	public abstract Connection abrirConexion() throws SQLException, ClassNotFoundException;
	
	public boolean checarConexion() throws SQLException {
		return conexion != null && !conexion.isClosed();
	}
	
	public Connection obtenerConexion() {
		return conexion;
	}
	
	public boolean cerrarConexion() throws SQLException {
		if (conexion == null) {
			return false;
		}
		conexion.close();
		return true;
	}
	
	public ResultSet querySQL(String query) throws SQLException, ClassNotFoundException {
		if (!checarConexion()) {
			abrirConexion();
		}
		Statement statement = (Statement) conexion.createStatement();
		ResultSet resultado = statement.executeQuery(query);
		return resultado;
	}
	
	public int actualizarSQL(String query) throws SQLException, ClassNotFoundException {
		if (!checarConexion()) {
			abrirConexion();
		}
		Statement statement = (Statement) conexion.createStatement();
		int resultado = statement.executeUpdate(query);
		return resultado;
	}
	
}
