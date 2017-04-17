package adminPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
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

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public AdFilesEditWindow(Database db) {
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
		
		JButton btnNewButton = new JButton("Uploaduj plik");
		panel_2.add(btnNewButton);
		
		JButton btnUsuPlik = new JButton("Usuń plik");
		panel_2.add(btnUsuPlik);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Typ", "Format", "Rozmiar", "D\u0142ugo\u015B\u0107", "Rozdzielczo\u015B\u0107"
				}
			) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		scrollPane.setViewportView(table);
	}
	
	public AdFilesEditWindow(Database db, ResultSet selectedAd){
		this(db);
		try {
			while(selectedAd.next()){
				Vector<String> data = new Vector<String>(2);
				data.addElement(selectedAd.getString("Plik_ID"));
				data.addElement(selectedAd.getString("Plik_Sciezka"));
				model.addRow(data);
				table.setModel(model);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
