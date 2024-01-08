package me.ilt.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class Conn {
	@Test
	public static Connection getCon(){
		Connection con= null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhaishop?serverTimezone=UTC","root", "root");
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args) {
		getCon();
	}
}
