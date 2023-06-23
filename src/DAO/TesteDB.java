package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TesteDB {
		
	public static void main(String[] args) {
		File f = new File("./config.properties");
		System.out.println(f.getAbsolutePath());
		try (InputStream input = new FileInputStream(f)) {
            Properties prop = new Properties();

            prop.load(input);

            Class.forName(prop.getProperty("db.driver"));
			System.out.println("Driver Mariadb carregado");
			Connection con = 
					DriverManager.getConnection(
							prop.getProperty("db.url"), 
							prop.getProperty("db.user"), 
							prop.getProperty("db.pass"));
			System.out.println("Conectado no banco de dados");

		} catch(ClassNotFoundException e) { 
			System.out.println("Erro ao carregar a classe:");
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
