import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {
	protected static final int WARNING_MESSAGE = 0;
	static String staffcheck = "";
	JLabel lblTitle, lblEmail, lblPassword, lblkosong;
	JTextField txtEmail;
	JPasswordField Password;
	JButton btnLogin;
	JPanel title, tengahsatu,tengahdua,tengahgabung, semua, login;
	Amorepos con;

	private void InitialFrame() {
		setTitle("Amore POS");
		setSize(380, 315);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	private void initialsetupComponent() {
		lblTitle = new JLabel("Amore POS");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		lblEmail = new JLabel("Email: ", SwingConstants.LEFT);
		lblEmail.setPreferredSize(new Dimension(70,30));
		lblPassword = new JLabel("Password:   ", SwingConstants.LEFT);
		txtEmail = new JTextField();
		txtEmail.setPreferredSize(new Dimension(200,30));
		Password = new JPasswordField();
		Password.setPreferredSize(new Dimension(200,30));
		btnLogin = new JButton("Login");
		btnLogin.setPreferredSize(new Dimension(300, 30));

		title = new JPanel();
		title.add(lblTitle);
		
		
		tengahsatu = new JPanel();
		tengahsatu.add(lblEmail);
		tengahsatu.add(txtEmail);
		
		tengahdua = new JPanel();
		tengahdua.add(lblPassword);
		tengahdua.add(Password);
		
		tengahgabung = new JPanel(new GridLayout(2,1,1,10));
		tengahgabung.add(tengahsatu);
		tengahgabung.add(tengahdua);

		login = new JPanel(new FlowLayout());
		login.add(btnLogin);

		semua = new JPanel(new GridLayout(4, 1, 20, 25));
		semua.add(lblTitle);
		semua.add(tengahsatu);
		semua.add(tengahdua);
		semua.add(login);

		add(semua);

	}

	private void validasi() {
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				String password = String.valueOf(Password.getPassword());
				if (email.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "Email and Password cannot be Empty", "WARN", WARNING_MESSAGE);
//					JOptionPane.showMessageDialog(null, "Email and Password cannot be Empty!");
				} else {
					try {
						String valid = "SELECT * FROM users WHERE email =? AND password =?";
						PreparedStatement st = con.prepareStatement(valid);
						st.setString(1, email);
						st.setString(2, password);
						ResultSet rs = st.executeQuery();

						if (rs.next()) {
							staffcheck = rs.getString(1);
							if (rs.getString(3).equals("Admin")) {
								JOptionPane.showMessageDialog(null, "Welcome " + rs.getString(2));
								dispose();
								Main main = new Main();
							} else if (rs.getString(3).equals("Accountant")) {
								JOptionPane.showMessageDialog(null, "Welcome " + rs.getString(2));
								dispose();
								Main2 main2 = new Main2();
							} else if (rs.getString(3).equals("Cashier")) {
								JOptionPane.showMessageDialog(null, "Welcome " + rs.getString(2));
								dispose();
								Main3 main3 = new Main3();
							}

						} else {
							JOptionPane.showMessageDialog(null, "Incorrect Email or Password");
						}
					} catch (SQLException e1) {
					}
				}

			}
		});
	}

	public Login() {
		con = Amorepos.getConnection();
		initialsetupComponent();
		validasi();
		InitialFrame();

	}

	public static void main(String[] args) {
		new Login();

	}
}
