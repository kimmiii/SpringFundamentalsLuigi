package be.vdab.Luigi.domain;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {
	private static final String URL = "jdbc:mysql://localhost/luigi?useSSL=false&allowPublicKeyRetrieval=true"; 
	private static final String USER = "cursist";
	private static final String PASSWORD = "cursist";

	public static void main(String[] args) {
				
		try  (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){ 
			
			System.out.println("Connectie geopend");
		} catch (SQLException ex) { 
			ex.printStackTrace(System.err); 
		}	
		
	}

}
