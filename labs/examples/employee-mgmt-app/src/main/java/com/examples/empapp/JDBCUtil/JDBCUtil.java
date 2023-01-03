package com.examples.empapp.JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Exception occur while loading the class : ");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		Connection con=null;
		String url="jdbc:mysql://localhost:3306/testdb";
		String userName="root";
		String pswd="root";
		try {
			con=DriverManager.getConnection(url, userName, pswd);
		} catch (SQLException e) {
			System.out.println("Exception occur while getting SQL connection : ");
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeResource(Connection con, Statement st) {
			try {
				if(con != null)
				con.close();
				if(st != null)
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
}