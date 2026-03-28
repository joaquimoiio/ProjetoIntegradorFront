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
			stringBuilder.append("nomeDoCliente VARCHAR NOT NULL, ");
			stringBuilder.append("tipoDePessoa VARCHAR NOT NULL, ");
			stringBuilder.append("cpf VARCHAR, ");
			stringBuilder.append("cnpj VARCHAR, ");
			stringBuilder.append("telefone VARCHAR NOT NULL, ");
			stringBuilder.append("email VARCHAR )");

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
