package datalayer;

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
			p.load(new FileInputStream("C:\\1004\\db_info.txt"));
			
			
			Class.forName(p.getProperty("driver"));
			con = DriverManager.getConnection(p.getProperty("url"),p.getProperty("user"), p.getProperty("pw"));

			
			
			
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
