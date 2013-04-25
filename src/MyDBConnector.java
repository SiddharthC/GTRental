/**
 * This class handles the connection to the database.
 * The user name and password has been hard coded for
 * this implementation.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/*****************************************************
 * Supporter class to connect the database to the 
 * Java GUI 
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class MyDBConnector {
	public Connection openConnection(){
		Properties properties = new Properties();
		properties.put("user", "root");
		properties.put("password", "fire");
		properties.put("characterEncoding", "ISO-8859-1");
		properties.put("useUnicode", "true");
		String url = "jdbc:mysql://127.0.0.1:3306/dbproject";

		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = DriverManager.getConnection(url, properties);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

}