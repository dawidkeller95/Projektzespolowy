package adminPanel;

import java.nio.charset.StandardCharsets;
import java.security.CryptoPrimitive;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DatatypeConverter;

public class Database {

	private Connection conn;
	
	/**
	 * Łączy się z lokalnym serwerem MySQL na porcie 3306, do konkretnej bazy danych.
	 * W przypadku niepowodzenia połączenia, program kończy działanie.
	 * @param name - nazwa bazy danych do połączenia
	 */
	public void connectWithDataBase(){
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ads?UseSSL=false&user=root");
		}
		catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Nie udało sie połączyć z bazą!");
			System.exit(0);
		}
	}
	/**
	 * Wykonuje podaną kwerendę i zwraca obiekt ResultSet.
	 * @param query - kwerenda
	 * @return obiekt ResultSet
	 */
	private ResultSet getResultSet(String query ){
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getAdvertisers(){
		ResultSet rs = getResultSet("select * from reklamodawca");
		return rs;
	}
	
	public ResultSet getAds(){
		ResultSet rs = getResultSet("select * from reklama r "
				+ "left outer join reklamodawca rd on r.Rekd_ID = rd.Rekd_ID");
		return rs;
	}
	
	/**
	 * Wykonuje próbe logowania do systemu.
	 * @param user - login 
	 * @param pass - hasło podane tekstem otwartym
	 * @return true jeśli logowanie zakończy się powodzeniem
	 */
	public boolean login(String user, String pass){
		try {
			ResultSet getPassResult = getResultSet("select Admin_Pass from administrator where Admin_Login = '" + user +"'");
			getPassResult.next();
			byte[] validUserPassHash = getPassResult.getString("Admin_Pass").getBytes();
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] typedUserPassHash = md.digest(pass.getBytes());
			String p1 = new String(validUserPassHash);
			String p2 = DatatypeConverter.printHexBinary(typedUserPassHash);
			if(p1.toUpperCase().compareTo(p2) == 0){
				return true;
			}
		} catch (SQLException e) {
			return false;
		} catch (NoSuchAlgorithmException e) {
			
		}
		return false;
		
	}
	
	
}
