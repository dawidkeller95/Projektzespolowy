package adminPanel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.DatatypeConverter;

public class Administrator {

	private Database db;
	
	public Administrator(Database db){
		this.db = db;
	}
	/**
	 * Wykonuje próbe logowania do systemu.
	 * @param user - login 
	 * @param pass - hasło podane tekstem otwartym
	 * @return true jeśli logowanie zakończy się powodzeniem, inaczej false
	 */
	public boolean login(String user, String pass){
		try {
			ResultSet getPassResult = db.getResultSet("select Admin_Pass from administrator where Admin_Login = '" + user +"'");
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
