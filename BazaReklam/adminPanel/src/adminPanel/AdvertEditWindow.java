package adminPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AdvertEditWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private Database db;

	private DefaultComboBoxModel<String> getAdvertisers(){
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ResultSet rs = db.getAdvertisers();
		model.addElement("brak");
		try {
			while(rs.next()){
				model.addElement(rs.getString("Rekd_Nazwa"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * Create the frame.
	 */
	public AdvertEditWindow(Database db) {
		this.db = db;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
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
		
		JLabel lblNazwa = new JLabel("Nazwa:");
		contentPane.add(lblNazwa, "2, 2, right, default");
		
		textField = new JTextField();
		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JLabel lblReklamodawca = new JLabel("Reklamodawca:");
		contentPane.add(lblReklamodawca, "2, 4, right, default");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> model = getAdvertisers();
		comboBox.setModel(model);
		contentPane.add(comboBox, "4, 4, fill, default");
		
		
		JLabel lblPlanowanaIloWywietle = new JLabel("Planowana ilość wyświetleń");
		contentPane.add(lblPlanowanaIloWywietle, "2, 6, right, default");
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, "4, 6, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblTyp = new JLabel("Typ:");
		contentPane.add(lblTyp, "2, 8, right, default");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "4, 8");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ButtonGroup group = new ButtonGroup();
		JRadioButton radioButton = new JRadioButton("Baner");
		panel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Wideo");
		panel.add(radioButton_1);
		group.add(radioButton);
		group.add(radioButton_1);
		
		JButton btnZatwierd = new JButton("Zatwierdz");
		contentPane.add(btnZatwierd, "2, 10, 5, 1, center, default");
	}

}
