package dao;

import java.sql.*;

public class DBConnection {

	public Connection initConnection() throws SQLException {

		String url = "jdbc:postgresql://localhost:5432/my_db";
		String userName = "postgres";
		String password = "111111";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = DriverManager.getConnection(url, userName, password);
		return connection;
	}
}
