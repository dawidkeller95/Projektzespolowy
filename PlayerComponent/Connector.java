package adminPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Connector {
	
private Connection conn;
	
/**
 * Łaczy sie z lokalnym serwerem MySQL na porcie 3306, do bazy danych ads
 * 
 * Kodowanie jest ustawiane na utf-8
 * W przypadku niepowodzenia połączenia, program kończy działanie.
 * 
 * przykładowe parametry: connectWithDatabase("localhost", "root", "");
 * @param host - nazwa hosta na którym stoi serwer SQL
 * @param user - nazwa użytkownika bazy danych (niezwiązane z panelem admina)
 * @param pass - hasło
 */
	public void connectWithDataBase(String host, String user, String pass){
		try{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/ads?UseSSL=false&user="+user+"&password="+pass+"characterEncoding=utf8");
		}
		catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Nie udało się połączyć z bazą!");
			System.exit(0);
		}
	}
	
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
	
	/**
	 * Zwraca liste reklam. Reklama jest w postaci wektora z następującymi danymi:
	 * data[0] = id reklamy (int)
	 * data[1] = nazwa reklamy (string)
	 * data[2] = typ reklamy (string)
	 * data[3] = status aktywności reklamy (int)
	 * data[4] = ilość wyświetleń reklamy (int)
	 * @return lista reklam
	 */
	public ArrayList<Vector<Object>> getAds(){
		ArrayList<Vector<Object>> lista = new ArrayList<Vector<Object>>();
		ResultSet rs = getResultSet("select * from reklama r "
				+ "left outer join reklamodawca rd on r.Rekd_ID = rd.Rekd_ID");
		try{
		while (rs.next()) { //powtarzaj dla wszystkich wierszy tabeli
		    Vector<Object> data = new Vector(5);
		    //id
		    data.add(rs.getInt("Rek_ID"));
		    //nazwa reklamy
		    data.add(rs.getString(3));
		    //typ reklamy
		    int typ = rs.getInt(4);
		    if(typ == 1) data.add("Wideo");
		    else if(typ == 2) data.add("Baner");
		    else data.add("Nieznany");
		    //czy aktywna
		    int czyakt = rs.getInt(5);
		    if(czyakt == 1) data.add("TAK");
		    else  data.add("NIE");
		    //ilosc wyswietlen
		    lista.add(data);
		} 
		return lista;
	}
    catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}	
		
	}
	/**
	 * Pobiera listę plików reklamy
	 * @param id - id reklamy
	 * @return
	 */
	public ArrayList<String> getAdFiles(int id){
		ArrayList<String> lista = new ArrayList<String>();
		ResultSet rs = getResultSet("select * from reklama r "
				+ "left outer join reklamodawca rd on r.Rekd_ID = rd.Rekd_ID "
				+ "where Rek_ID = "+id);
		try {
			while(rs.next()){
				lista.add(rs.getString("Plik_Sciezka"));
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
}
