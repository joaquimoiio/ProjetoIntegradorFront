package com.totalcross.util;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.Statement;
import totalcross.sys.Settings;

public class DatabaseManager {

	public static SQLiteUtil sqliteUtil;

	static {

		try {

			sqliteUtil = new SQLiteUtil(Settings.appPath, "front.db");

			Statement st = sqliteUtil.con().createStatement();

			StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append("CREATE TABLE IF NOT EXISTS person (");
			stringBuilder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ");
			stringBuilder.append("nomeDoCliente TEXT NOT NULL, ");
			stringBuilder.append("tipoDePessoa TEXT NOT NULL, ");
			stringBuilder.append("cpf TEXT, ");
			stringBuilder.append("cnpj TEXT, ");
			stringBuilder.append("telefone TEXT NOT NULL, ");
			stringBuilder.append("email TEXT, ");
			stringBuilder.append("\"sync\" INTEGER,");
			stringBuilder.append("\"deletado\" INTEGER");
			stringBuilder.append(")");

			st.execute(stringBuilder.toString());
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return sqliteUtil.con();
	}

}