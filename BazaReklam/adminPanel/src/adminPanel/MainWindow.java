package adminPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ListSelectionModel;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Database db;
	private JLabel loggedUsername;
	private DefaultTableModel model;

	private void logout(){
		int pytanie = JOptionPane.showOptionDialog(this, new String("Czy na pewno chcesz się wylogować?"), "Uwaga", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
		if(pytanie == JOptionPane.YES_OPTION){
				new LoginWindow().setVisible(true);
				this.dispose();
		}
	}
	
	private void refreshTable(){
		model.setRowCount(0);
		try {
			
			ResultSet rs = db.getAds();
			while (rs.next()) { //powtarzaj dla wszystkich wierszy tabeli
			    Vector data = new Vector(9);
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
			    data.add(rs.getInt(6));
			    //plan. ilosc wysw
			    data.add(rs.getInt(7));
			    //reklamodawnca
			    data.add(rs.getString("Rekd_Nazwa"));
			    
			    model.addRow(data);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		table.setModel(model);
	}
	
	private void openAddAdDialog(){
		new AdvertEditWindow(db).setVisible(true);
		refreshTable();
	}
	
	private void openAdFilesDialog(){
		try{
			String selID = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
			ResultSet rs = db.getAdFiles(Integer.parseInt(selID));
			new AdFilesEditWindow(db,rs).setVisible(true);
			}
			catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(this, "Wybierz element!");
				e.printStackTrace();
			}
	}
	
	private void openAdvertisers(){
		new AdvertisersEditWindow(db).setVisible(true);
	}
	
	private void openEditAdDialog(){
		
		try{
		String selID = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
		ResultSet rs = db.getAdByID(Integer.parseInt(selID));
		new AdvertEditWindow(db,rs).setVisible(true);
		refreshTable();
		}
		catch(ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(this, "Wybierz element!");
			e.printStackTrace();
		}
	}
	
	private void deleteRow(){
		int selID;
		String selName;
		try{
			selID = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			selName = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
			int pytanie = JOptionPane.showOptionDialog(this, new String("Czy na pewno chcesz usunąć reklamę \""+ selName +"\"?"), "Uwaga", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if(pytanie == JOptionPane.YES_OPTION){
				db.deleteAd(selID);
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(this, "Wybierz element!");
			e.printStackTrace();
		}


	}

	/**
	 * Create the frame.
	 */
	public MainWindow(Database db, String loggedAs) {
		setTitle("Reklamy");
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 964, 684);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();

		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.EAST);
		
		JButton btnWyloguj = new JButton("Wyloguj");
		btnWyloguj.setForeground(Color.BLUE);
		btnWyloguj.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnWyloguj.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnWyloguj.setContentAreaFilled(false);
		btnWyloguj.setBorder(null);
		btnWyloguj.setBorderPainted(false);
		btnWyloguj.setOpaque(false);
		btnWyloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logout();
			}
		});
		panel_3.add(btnWyloguj);
		btnWyloguj.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_4, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Jesteś zalogowany jako:");
		panel_4.add(lblNewLabel);
		
		loggedUsername = new JLabel(loggedAs);
		panel_4.add(loggedUsername);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("200px"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton btnNewButton = new JButton("Dodaj reklamę");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddAdDialog();
			}
		});
		panel_1.add(btnNewButton, "2, 2, fill, top");
		
		JButton btnUsuReklam = new JButton("Usuń reklamę");
		btnUsuReklam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteRow();
				refreshTable();
			}
		});
		panel_1.add(btnUsuReklam, "2, 4, fill, top");
		
		JButton btnEdytujReklam = new JButton("Edytuj reklamę");
		btnEdytujReklam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openEditAdDialog();
			}
		});
		panel_1.add(btnEdytujReklam, "2, 6");
		
		JButton btnWywietlPlikiWideo = new JButton("Pliki wideo zazn. reklamy");
		btnWywietlPlikiWideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAdFilesDialog();
			}
		});
		btnWywietlPlikiWideo.setActionCommand("");
		panel_1.add(btnWywietlPlikiWideo, "2, 14");
		
		JButton btnWywietlListReklamodawcw = new JButton("Wyświetl listę reklamodawców");
		btnWywietlListReklamodawcw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAdvertisers();
			}
		});
		panel_1.add(btnWywietlListReklamodawcw, "2, 16");
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ID","Nazwa", "Typ", "Aktywna", "Wy\u015Bwietle\u0144", "Planowane wy\u015Bwietlenia", "Reklamodawca"
			}
		) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		refreshTable();
		table.removeColumn(table.getColumnModel().getColumn(0));
		scrollPane.setViewportView(table);
	}

}
