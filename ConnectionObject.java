package travels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionObject {
	private static Connection connectings;
	
	
	
	public static Connection getConnecting () throws SQLException
	{
		
		
		if(connectings==null)
		{
			
			Connection connecting=DriverManager.getConnection(ConstantValues.dburl,ConstantValues.dbUSer,ConstantValues.dbpassword);	
			connectings=connecting;
			return connectings;
		}
		return connectings;
		
	}

	private ConnectionObject() {
	
	}

	
}
