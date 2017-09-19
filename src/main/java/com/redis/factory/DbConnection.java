package com.redis.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String driver = "oracle.jdbc.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
			String user = "redis";
			String password = "redis";

			Class.forName(driver);

			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			System.out.println("�������ݿ�ʧ��" + e);
		}
		return conn;
	}

	/**
	 * �ر�conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	// �ر�ResultSet
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
