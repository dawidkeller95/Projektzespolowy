package adminPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField loginField;
	private JPasswordField passwordField;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void login(){
		if(db.login(loginField.getText(), new String(passwordField.getPassword()))){
			new MainWindow(db, loginField.getText()).setVisible(true);
			this.dispose();
		}
		else{
			JOptionPane.showMessageDialog(this, "Błąd logowania");
		}
	}
	
	AbstractAction btnZalogujAction = new AbstractAction() {
		public void actionPerformed(ActionEvent arg0) {
			login();
		}
	};
	
	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("Logowanie do bazy danych");
		setResizable(false);
		db = new Database();
		db.connectWithDataBase();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 133);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				FormSpecs.PREF_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("300px:grow"),},
			new RowSpec[] {
				RowSpec.decode("10px"),
				RowSpec.decode("20px"),
				RowSpec.decode("8px"),
				RowSpec.decode("20px"),
				RowSpec.decode("10px"),
				RowSpec.decode("23px"),}));
		
		JLabel lblLogin = new JLabel("Login:");
		contentPane.add(lblLogin, "2, 2, center, center");
		
		loginField = new JTextField();
		loginField.setSize(new Dimension(300, 20));
		loginField.setMinimumSize(new Dimension(300, 20));
		loginField.setPreferredSize(new Dimension(100, 20));
		contentPane.add(loginField, "4, 2, fill, top");
		loginField.setColumns(10);
		
		JLabel lblHaso = new JLabel("Hasło:");
		contentPane.add(lblHaso, "2, 4, right, center");
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField, "4, 4, fill, default");
		
		JButton btnZaloguj = new JButton("Zaloguj");
		btnZaloguj.setMnemonic(KeyEvent.VK_ENTER);
		btnZaloguj.addActionListener(btnZalogujAction);
		btnZaloguj.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "pressed");
        btnZaloguj.getActionMap().put("pressed", btnZalogujAction);
		contentPane.add(btnZaloguj, "1, 6, 4, 1, center, top");
	}

}
