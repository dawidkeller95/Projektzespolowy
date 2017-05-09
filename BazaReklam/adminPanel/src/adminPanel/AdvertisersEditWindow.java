package adminPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setTitle("Reklamodawcy");
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
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addAdvertiser();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Edytuj");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						editAdvertiser();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton btnUsu = new JButton("Usuń");
				btnUsu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deleteAdvertiser();
					}
				});
				buttonPane.add(btnUsu);
			}
		}
		refreshTable();
		table.removeColumn(table.getColumnModel().getColumn(0));
		table.getColumnModel().getColumn(2).setPreferredWidth(8);
	}
	
	private void addAdvertiser(){
		AdvertiserEditDialog e = new AdvertiserEditDialog();
		e.setVisible(true);
		db.addAdvertiser(e.values.get(0), e.values.get(1), e.values.get(2), e.values.get(3));
		refreshTable();
	}
	
	private void editAdvertiser(){
		try{
			int AselID = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			ResultSet rs = db.getAdvertiserByID(AselID);
			Vector<String> v = new Vector<String>(4);
			rs.next();
			v.addElement(rs.getString("Rekd_Nazwa"));
			v.addElement(rs.getString("Rekd_Adres"));
			v.addElement(rs.getString("Rekd_Telefon"));
			v.addElement(rs.getString("Rekd_Email"));
			AdvertiserEditDialog e = new AdvertiserEditDialog(v);
			e.setVisible(true);
			db.updateAdvertiser(AselID, e.values.get(0), e.values.get(1), e.values.get(2), e.values.get(3));
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
	
	private void deleteAdvertiser(){
		int selID;
		String selName;
		try{
			selID = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
			selName = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
			int pytanie = JOptionPane.showOptionDialog(this, new String("Czy na pewno chcesz usunąć reklamodawcę \""+ selName +"\"?"), "Uwaga", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			if(pytanie == JOptionPane.YES_OPTION){
				db.deleteAdvertiser(selID);
				refreshTable();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			JOptionPane.showMessageDialog(this, "Wybierz element!");
			e.printStackTrace();
		}


	}

}
