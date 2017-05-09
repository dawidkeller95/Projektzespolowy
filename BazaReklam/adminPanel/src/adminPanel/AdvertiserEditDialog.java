package adminPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AdvertiserEditDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nazwa;
	private JTextField adres;
	private JTextField tel;
	private JTextField email;
	public Vector<String> values;
	/**
	 * Create the dialog.
	 */
	public AdvertiserEditDialog() {
		values = new Vector<String>(4);
		setModal(true);
		setTitle("Dodaj reklamodawcę");
		setBounds(100, 100, 373, 198);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Nazwa");
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			nazwa = new JTextField();
			contentPanel.add(nazwa, "4, 2, left, default");
			nazwa.setColumns(25);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Adres");
			contentPanel.add(lblNewLabel_1, "2, 4, right, default");
		}
		{
			adres = new JTextField();
			adres.setPreferredSize(new Dimension(500, 20));
			contentPanel.add(adres, "4, 4, fill, default");
			adres.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Telefon");
			contentPanel.add(lblNewLabel_2, "2, 6, right, default");
		}
		{
			tel = new JTextField();
			contentPanel.add(tel, "4, 6, left, default");
			tel.setColumns(12);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Email");
			contentPanel.add(lblNewLabel_3, "2, 8, right, default");
		}
		{
			email = new JTextField();
			contentPanel.add(email, "4, 8, left, default");
			email.setColumns(25);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						values.addElement(nazwa.getText());
						values.addElement(adres.getText());
						values.addElement(tel.getText());
						values.addElement(email.getText());
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public AdvertiserEditDialog(Vector<String> v){
		this();
		nazwa.setText(v.get(0));
		adres.setText(v.get(1));
		tel.setText(v.get(2));
		email.setText(v.get(3));
	}

}
