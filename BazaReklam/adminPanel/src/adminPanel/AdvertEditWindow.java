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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class AdvertEditWindow extends JDialog {

	private JPanel contentPane;
	private JTextField AdNameField;
	private JTextField planViewCountField;
	private JRadioButton videoRButton;
	private JRadioButton bannerRButton;
	private JComboBox<String> AdvertiserComboBox;
	private Database db;
	private ArrayList<Integer> AdvertisersIDs;
	private ResultSet rowRS;
	private boolean editMode;
	private JLabel label;
	private JCheckBox chckbxAktywna;
	
	private DefaultComboBoxModel<String> getAdvertisers(){
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ResultSet rs = db.getAdvertisers();
		try {
			while(rs.next()){
				AdvertisersIDs.add(rs.getInt("Rekd_ID"));
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
	 * @wbp.parser.constructor
	 */
	public AdvertEditWindow(Database db) {
		setModal(true);
		this.db = db;
		setTitle("Dodaj reklamę");
		AdvertisersIDs = new ArrayList<Integer>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 226);
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNazwa = new JLabel("Nazwa:");
		contentPane.add(lblNazwa, "2, 2, right, default");
		
		AdNameField = new JTextField();
		contentPane.add(AdNameField, "4, 2, fill, default");
		AdNameField.setColumns(10);
		
		JLabel lblReklamodawca = new JLabel("Reklamodawca:");
		contentPane.add(lblReklamodawca, "2, 4, right, default");
		
		AdvertiserComboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> model = getAdvertisers();
		AdvertiserComboBox.setModel(model);
		contentPane.add(AdvertiserComboBox, "4, 4, fill, default");
		
		
		JLabel lblPlanowanaIloWywietle = new JLabel("Planowana ilość wyświetleń");
		contentPane.add(lblPlanowanaIloWywietle, "2, 6, right, default");
		
		planViewCountField = new JTextField();
		contentPane.add(planViewCountField, "4, 6, fill, default");
		planViewCountField.setColumns(10);
		
		JLabel lblTyp = new JLabel("Typ:");
		contentPane.add(lblTyp, "2, 8, right, default");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "4, 8");
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ButtonGroup group = new ButtonGroup();
		bannerRButton = new JRadioButton("Baner");
		panel.add(bannerRButton);
		
		videoRButton = new JRadioButton("Wideo");
		panel.add(videoRButton);
		group.add(bannerRButton);
		group.add(videoRButton);
		
		JButton btnZatwierdz = new JButton("Zatwierdz");
		btnZatwierdz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Zatwierdz();
			}
		});
		
		label = new JLabel("");
		contentPane.add(label, "2, 10");
		
		chckbxAktywna = new JCheckBox("Aktywna");
		contentPane.add(chckbxAktywna, "4, 10");
		contentPane.add(btnZatwierdz, "2, 12, 3, 1, center, default");
		editMode = false;
	}
	
	public AdvertEditWindow(Database db, ResultSet rowData){
		this(db);
		setTitle("Edytuj reklamę");
		try {
			rowData.next();
			rowRS = rowData;
			AdNameField.setText(rowData.getString("Rek_Nazwa"));
			planViewCountField.setText(rowData.getString("Rek_PlanowanaIloscWysw"));
			int index = AdvertisersIDs.indexOf(rowData.getInt("Rekd_ID"));
			AdvertiserComboBox.setSelectedIndex(index);
			if(rowData.getInt("Rek_Typ") == 1) videoRButton.setSelected(true);
			else if(rowData.getInt("Rek_Typ") == 2) bannerRButton.setSelected(true);
			chckbxAktywna.setSelected(rowData.getBoolean("Rek_CzyAktywna"));
			editMode = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void Zatwierdz(){
		int typ = 0;
		if(videoRButton.isSelected()) typ = 1;
		else if(bannerRButton.isSelected()) typ = 2;
		
		
		if(editMode){
			try {
				db.updateAdbyID(rowRS.getInt("Rek_ID"), AdNameField.getText(),AdvertisersIDs.get(AdvertiserComboBox.getSelectedIndex()), Integer.parseInt(planViewCountField.getText()), typ, chckbxAktywna.isSelected());
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			db.addAd(AdNameField.getText(),AdvertisersIDs.get(AdvertiserComboBox.getSelectedIndex()), Integer.parseInt(planViewCountField.getText()), typ, chckbxAktywna.isSelected());
		}
		this.dispose();
	}
		

}
