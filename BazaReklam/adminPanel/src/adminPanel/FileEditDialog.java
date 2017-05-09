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
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.Normalizer.Form;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FileEditDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField sciezkaField;
	private JTextField formatField;
	private JTextField rozField;
    public Vector<String> values;

	/**
	 * Create the dialog.
	 */
	public FileEditDialog() {
		values = new Vector<String>(3);
		setModal(true);
		setTitle("Dodaj plik");
		setResizable(false);
		setBounds(100, 100, 450, 145);
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblcieka = new JLabel("Ścieżka");
			contentPanel.add(lblcieka, "2, 2, right, default");
		}
		{
			sciezkaField = new JTextField();
			sciezkaField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					getExt();
				}
			});
			sciezkaField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					getExt();
					
				}
			});
			contentPanel.add(sciezkaField, "4, 2, fill, default");
			sciezkaField.setColumns(10);
		}
		{
			JLabel lblFormat = new JLabel("Format");
			contentPanel.add(lblFormat, "2, 4, right, default");
		}
		{
			formatField = new JTextField();
			contentPanel.add(formatField, "4, 4, left, default");
			formatField.setColumns(10);
		}
		{
			JLabel lblRozdzielco = new JLabel("Rozdzielcość");
			contentPanel.add(lblRozdzielco, "2, 6, right, default");
		}
		{
			rozField = new JTextField();
			contentPanel.add(rozField, "4, 6, left, default");
			rozField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						values.addElement(sciezkaField.getText());
						values.addElement(formatField.getText());
						values.addElement(rozField.getText());
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
	public FileEditDialog(Vector<String> val){
		this();
		sciezkaField.setText(val.get(0));
		formatField.setText(val.get(1));
		rozField.setText(val.get(2));
		
	}
	
	private void getExt(){
		String extension = "";
		String sciezka = sciezkaField.getText();
		int i = sciezka.lastIndexOf('.');
		if (i > 0) {
		    extension = sciezka.substring(i+1);
		}
		formatField.setText(extension);
	}

}
