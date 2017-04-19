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
	 * Ĺ�Ä…czy siÄ™ z lokalnym serwerem MySQL na porcie 3306, do konkretnej bazy danych.
	 * W przypadku niepowodzenia poĹ‚Ä…czenia, program koĹ„czy dziaĹ‚anie.
	 * @param name - nazwa bazy danych do poĹ‚Ä…czenia
	 */
	public void connectWithDataBase(){
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ads?UseSSL=false&user=root");
		}
		catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Nie udało się połączyć z bazą!");
			System.exit(0);
		}
	}
	/**
	 * Wykonuje podanÄ… kwerendÄ™ i zwraca obiekt ResultSet.
	 * @param query - kwerenda
	 * @return obiekt ResultSet
	 */
	ResultSet getResultSet(String query ){
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
	
	public ResultSet getAdByID(int id){
		ResultSet rs = getResultSet("select * from reklama r "
				+ "left outer join reklamodawca rd on r.Rekd_ID = rd.Rekd_ID "
				+ "where Rek_ID = "+id);
		return rs;
	}
	
	public ResultSet getAdFiles(int id){
		ResultSet rs = getResultSet("select * from pliki r "
				+ "where Rek_ID = "+id);
		return rs;
	}
	/**
	 * Aktualizuje dane wiersza o podanym ID
	 * @param rowID
	 * @param nazwa
	 * @param RekdID
	 * @param RekPlanWysw
	 * @param RekTyp
	 */
	public void updateAdbyID(int rowID, String nazwa, int RekdID, int RekPlanWysw, int RekTyp, boolean RekAkt){
		try {
			Statement stm = conn.createStatement();
			int RekAktI = (RekAkt) ? 1 : 0;
			stm.execute("UPDATE reklama "
					+ "SET Rek_Nazwa = '"+ nazwa +"', "
					+ "Rekd_ID = " +RekdID+", "
					+ "Rek_PlanowanaIloscWysw = " + RekPlanWysw +", "
					+ "Rek_Typ = " +RekTyp+", "
					+ "Rek_CzyAktywna = "+RekAktI+" "
					+ "WHERE Rek_ID = "+rowID+";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addAd(String nazwa, int RekdID, int RekPlanWysw, int RekTyp, boolean RekAkt){
		try {
			Statement stm = conn.createStatement();
			int RekAktI = (RekAkt) ? 1 : 0;
			stm.execute("INSERT INTO reklama (Rek_ID, Rek_Nazwa, Rekd_ID, Rek_PlanowanaIloscWysw, Rek_Typ, Rek_IloscWyswietlen, Rek_CzyAktywna) "
					+ "VALUES (NULL, '"+ nazwa +"', "
					+ "" +RekdID+", "
					+ "" + RekPlanWysw +", "
					+ "" +RekTyp+", 0, "
					+ "" + RekAktI+ ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteAd(int id){
		try {
			Statement stm = conn.createStatement();
			stm.execute("DELETE FROM reklama WHERE Rek_ID = "+ id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Wykonuje prĂłbe logowania do systemu.
	 * @param user - login 
	 * @param pass - hasĹ‚o podane tekstem otwartym
	 * @return true jeĹ›li logowanie zakoĹ„czy siÄ™ powodzeniem
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
