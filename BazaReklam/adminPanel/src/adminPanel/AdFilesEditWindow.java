package adminPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class AdFilesEditWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private Database db;
	private int selID;
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public AdFilesEditWindow(Database db) {
		setTitle("Pliki reklamy");
		this.db = db;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 456, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Dodaj");
		panel_2.add(btnNewButton);
		
		JButton btnUsuPlik = new JButton("Usuń");
		btnUsuPlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteRow();
			}
		});
		
		JButton btnNewButton_1 = new JButton("Edytuj");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editFile();
			}
		});
		panel_2.add(btnNewButton_1);
		panel_2.add(btnUsuPlik);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addFile();
			}
		});
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Sciezka", "Format", "Rozdzielczo\u015B\u0107"
				}
			) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(1).setPreferredWidth(2);
		table.getColumnModel().getColumn(2).setPreferredWidth(2);
		
	}
	
	public AdFilesEditWindow(Database db, int selID){
		this(db);
		this.selID = selID;
		refreshTable();
	}
	
	private void refreshTable(){
		model.setRowCount(0);
		ResultSet selectedAdFiles = db.getAdFiles(selID);
		try {
			while(selectedAdFiles.next()){
				Vector<String> data = new Vector<String>(5);
				data.addElement(selectedAdFiles.getString("Plik_ID"));
				data.addElement(selectedAdFiles.getString("Plik_Sciezka"));
				data.addElement(selectedAdFiles.getString("Plik_Format"));
				data.addElement(selectedAdFiles.getString("Plik_Rozdziel"));
				model.addRow(data);
				table.setModel(model);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void deleteRow(){
		int selID;
		String selName;
		try{
			selID = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			selName = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
			int pytanie = JOptionPane.showOptionDialog(this, new String("Czy na pewno chcesz usunąć plik \""+ selName +"\"?"), "Uwaga", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if(pytanie == JOptionPane.YES_OPTION){
				db.deleteFile(selID);
				refreshTable();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(this, "Wybierz element!");
			e.printStackTrace();
		}


	}
	
	private void addFile(){
		FileEditDialog e = new FileEditDialog();
		e.setVisible(true);
		db.addFile(e.values.get(0), selID, e.values.get(1), e.values.get(2));
		refreshTable();
	}
	
	private void editFile(){
		try{
			int FselID = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			ResultSet rs = db.getAdFileByID(FselID);
			Vector<String> v = new Vector<String>(4);
			rs.next();
			v.addElement(rs.getString("Plik_Sciezka"));
			v.addElement(rs.getString("Plik_Format"));
			v.addElement(rs.getString("Plik_Rozdziel"));
			FileEditDialog e = new FileEditDialog(v);
			e.setVisible(true);
			db.updateFile(FselID, e.values.get(0), this.selID, e.values.get(1), e.values.get(2));
			refreshTable();
			}
			catch(ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog(this, "Wybierz element!");
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	

}
