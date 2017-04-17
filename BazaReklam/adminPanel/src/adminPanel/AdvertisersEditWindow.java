package adminPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdvertisersEditWindow extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Database db;

	private void refreshTable(){
		model.setRowCount(0);
		try {
			
			ResultSet rs = db.getAdvertisers();
			while (rs.next()) { //powtarzaj dla wszystkich wierszy tabeli
			    Vector data = new Vector(5);
			    //id
			    data.add(rs.getString(1));
			    //nazwa reklamodawcy
			    data.add(rs.getString(2));
			    //adres reklamodawcy
			    data.add(rs.getString(3));
			    //telefon
			    data.add(rs.getString(4));
			    //email
			    data.add(rs.getString(5));
			
			    
			    model.addRow(data);
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		model.removeRow(0);
		table.setModel(model);
	}
	
	/**
	 * Create the dialog.
	 */
	public AdvertisersEditWindow(Database db) {
		this.db = db;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				model = new DefaultTableModel(
					new Object[][] {
						{null, null, null, null, null},
					},
					new String[] {
						"ID", "Nazwa", "Adres", "Telefon", "E-mail"
					}
				) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				table.setFillsViewportHeight(true);
				scrollPane.setViewportView(table);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Dodaj");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Edytuj");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton btnUsu = new JButton("Usuń");
				buttonPane.add(btnUsu);
			}
		}
		refreshTable();
	}
	

}
