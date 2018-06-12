package datalayer;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCutil {
	
	public static Connection getConnection() {
		
		Connection con = null;
		String driver = "";
		String url = "";
		String user = "";
		String pw = "";
		
		try {
			Properties p = new Properties();
	         
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","scott", "tiger");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return con;
	}

	public static void close(Connection con, Statement ps,ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
