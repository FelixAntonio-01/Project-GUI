import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import javax.management.modelmbean.ModelMBean;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IFManageAccounts extends JInternalFrame {
	JLabel lblIDTitle, lblUserID, lblFullname, lblRole, lblEmail, lblPassword;
	JTextField txtFullname, txtEmail, txtPassword;
	JComboBox cboRole;
	JButton btnInsert, btnUpdate, btnDelete;
	JPanel Panel, PanelID, PanelTengah, PanelPassword, PanelTable;
	JTable tblusers;
	JScrollPane scpusers;
	Amorepos con;
	Vector<String> vrole = new Vector<>();

	private void InitialComponent() {
		lblIDTitle = new JLabel("ID: ");
		lblUserID = new JLabel();
		lblFullname = new JLabel("Fullname: ");
		lblRole = new JLabel("Role: ");
		lblEmail = new JLabel("Email: ");
		lblPassword = new JLabel("Password ");

		txtFullname = new JTextField();
		txtEmail = new JTextField();
		txtPassword = new JTextField();

		vrole.add("Accountant");
		vrole.add("Admin");
		vrole.add("Cashier");

		cboRole = new JComboBox(vrole);

		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");

	}

	private void InitialFrame() {
		setTitle("Manage Account Menu");
		setSize(700, 550);
		setClosable(true);
		setResizable(true);
		setVisible(true);
	}

	private void SetupAtas() {

		PanelTengah = new JPanel(new GridLayout(5, 1, 5, 5));
		PanelTengah.add(lblIDTitle);
		PanelTengah.add(lblUserID);
		PanelTengah.add(new JLabel(""));
		
		PanelTengah.add(lblFullname);
		PanelTengah.add(txtFullname);
		PanelTengah.add(btnInsert);

		PanelTengah.add(lblRole);
		PanelTengah.add(cboRole);
		PanelTengah.add(btnUpdate);

		PanelTengah.add(lblEmail);
		PanelTengah.add(txtEmail);
		PanelTengah.add(btnDelete);
		
		PanelTengah.add(lblPassword);
		PanelTengah.add(txtPassword);
		PanelTengah.add(new JLabel(""));



	}

	private void gabungAtas() {
		Panel = new JPanel(new GridLayout(1, 1, 5, 5));

		Panel.add(PanelTengah);
	
	}

	DefaultTableModel model = new DefaultTableModel();

	private void SetupTable() {
		PanelTable = new JPanel();
		tblusers = new JTable();
		scpusers = new JScrollPane();

		model.addColumn("ID");
		model.addColumn("Fullname");
		model.addColumn("Role");
		model.addColumn("Email");
		model.addColumn("Password");

		try {
			String sql = "SELECT * FROM `users`";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5) });
				tblusers.setModel(model);
			}

		} catch (Exception e) {
		}

		scpusers.getViewport().add(tblusers);
		scpusers.setPreferredSize(new Dimension(600, 500));
		PanelTable.add(scpusers);

	}

	private void setupRefresh() {
		model.setRowCount(0);
		;
		try {
			String sql = "SELECT * FROM `users`";
			Statement stt = con.createStatement();
			ResultSet rss = stt.executeQuery(sql);
			while (rss.next()) {
				model.addRow(new Object[] { rss.getString(1), rss.getString(2), rss.getString(3), rss.getString(4),
						rss.getString(5) });
				tblusers.setModel(model);
			}

		} catch (Exception e2) {
		}

		scpusers.getViewport().add(tblusers);
		scpusers.setPreferredSize(new Dimension(600, 500));
		PanelTable.add(scpusers);
	}

	private void setupRead() {
		tblusers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblusers.rowAtPoint(e.getPoint());
				String id = tblusers.getValueAt(row, 0).toString();
				lblUserID.setText(id);
				String fullname = tblusers.getValueAt(row, 1).toString();
				txtFullname.setText(fullname);
				String role = tblusers.getValueAt(row, 2).toString();
				cboRole.setSelectedItem(role);
				String email = tblusers.getValueAt(row, 3).toString();
				txtEmail.setText(email);
				String password = tblusers.getValueAt(row, 4).toString();
				txtPassword.setText(password);

			}
		});
	}

	private void setupInsert() {

		btnInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String isiEmail = txtEmail.getText();
				String isiFullname = txtFullname.getText();
				String isiPassword = txtPassword.getText();
				char checkpass ;
				String isiRole = (String) cboRole.getSelectedItem();
				
				int check = 0;
				int check2 = 0;
				int countHuruf = 0;
				int countAngka = 0;
				
				char email = '@';
				char email2 = '.';
				int position1 = isiEmail.indexOf(email);
				int position2 = isiEmail.indexOf(email2);
				int hasil = position1 - position2;
				
				for(int i=0;i<isiEmail.length();i++) {
					if(isiEmail.charAt(i) == '@') {
						check++;	
					}
				}
				
				for(int a=0;a<isiPassword.length();a++) {
					checkpass = isiPassword.charAt(a);
					
					if(Character.isAlphabetic(checkpass)) {
						countHuruf++;
					}else if(Character.isDigit(checkpass)) {
						countAngka++;
					}
					
					if(!Character.isAlphabetic(checkpass) && !Character.isDigit(checkpass)) {
						check2 = 1;
					}
				}

				if (isiEmail.equals("") | isiFullname.equals("") | isiPassword.equals("")) {
					JOptionPane.showMessageDialog(null, "All Fields must be filled!");
				} else {
					if (isiEmail.startsWith("@") || isiEmail.endsWith("@")
							|| (!isiEmail.contains(".")) || (!isiEmail.contains("@")) || isiEmail.endsWith(".")
									|| Math.abs(hasil) == 1 || check>1) {
						JOptionPane.showMessageDialog(null, "Wrong email format!");
					} else {
						try {
							String valid = "SELECT * FROM users WHERE email =?";
							PreparedStatement st = con.prepareStatement(valid);
							st.setString(1, isiEmail);
							ResultSet rs = st.executeQuery();
							if (rs.next()) {
								if (!rs.getString(4).equals("")) {
									JOptionPane.showMessageDialog(null, "Email already used!");
								}
							} else {
								if (countAngka == 0 || countHuruf ==0) {
									JOptionPane.showMessageDialog(null, "Password must be Alphanumeric");
								} else {
									if(check2 ==1) {
										JOptionPane.showMessageDialog(null, "Password must be Alphanumeric");
									}else {
										Random rand = new Random();
										String randomdata = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
										int length = 10;
										char[] hasilrandom = new char[length];
										String hasilid = "";
										for (int i = 0; i < length; i++) {
											hasilrandom[i] = randomdata.charAt(rand.nextInt(randomdata.length()));
										}

										for (int i = 0; i < hasilrandom.length; i++) {
											hasilid += hasilrandom[i];
										}
										String masuk2 = "INSERT INTO users(userid, fullname, role, email, password) VALUES('"+hasilid+"', '"+isiFullname+"',"
												+ "'"+isiRole+"', '"+isiEmail+"', '"+isiPassword+"')";
										Statement stcoba = con.createStatement();
										stcoba.executeUpdate(masuk2);
										stcoba.close();
											JOptionPane.showMessageDialog(null, "Insert Success!");
											lblUserID.setText(null);
											txtFullname.setText(null);
											txtEmail.setText(null);
											txtPassword.setText(null);
											setupRefresh();
									}
									

								}
							}
						} catch (SQLException e1) {
						}

					}
				}
			}
		});
	}

	private void setupUpdate() {
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String isiid = lblUserID.getText();
				String isiEmail = txtEmail.getText();
				String isiFullname = txtFullname.getText();
				String isiPassword = txtPassword.getText();
				String isiRole = (String) cboRole.getSelectedItem();
				char checkpass;
				
				int check = 0;
				int check2 = 0;
				int countHuruf = 0;
				int countAngka = 0;
				
				char email = '@';
				char email2 = '.';
				int position1 = isiEmail.indexOf(email);
				int position2 = isiEmail.indexOf(email2);
				int hasil = position1 - position2;
				
				
				for(int i=0;i<isiEmail.length();i++) {
					if(isiEmail.charAt(i) == '@') {
						check++;	
					}
				}
				
				for(int a=0;a<isiPassword.length();a++) {
					checkpass = isiPassword.charAt(a);
					
					if(Character.isAlphabetic(checkpass)) {
						countHuruf++;
					}else if(Character.isDigit(checkpass)) {
						countAngka++;
					}
					
					if(!Character.isAlphabetic(checkpass) && !Character.isDigit(checkpass)) {
						check2 = 1;
					}
				}

				if (tblusers.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select data to be updated first!");
				} else {
					if (isiEmail.equals("") || isiFullname.equals("") || isiPassword.equals("")) {
						JOptionPane.showMessageDialog(null, "All fields must be filled");
					} else {
						if (isiEmail.startsWith("@") || isiEmail.endsWith("@")
								|| (!isiEmail.contains(".")) || (!isiEmail.contains("@")) || isiEmail.endsWith(".")
										|| Math.abs(hasil) == 1 || check>1) {
							JOptionPane.showMessageDialog(null, "Wrong email format!");
						} else {
							String valid = "SELECT * FROM users WHERE email =?";
							PreparedStatement st;
							try {
								st = con.prepareStatement(valid);
								st.setString(1, isiEmail);
								ResultSet rs = st.executeQuery();
								if (rs.next()) {
									if (!rs.getString(4).equals("")) {
										JOptionPane.showMessageDialog(null, "Email already used!");
									}
								}else {
									if (countHuruf ==0 || countAngka == 0 ) {
										JOptionPane.showMessageDialog(null, "Password must be Alphanumeric");
									}else {
										if(check2 == 1) {
											JOptionPane.showMessageDialog(null, "Password must be Alphanumeric");
										}else {
											String sqlupdate = "UPDATE users SET fullname = ?, role = ?, email = ?, password = ? where userid = ? ";
											try {
												PreparedStatement ps = con.prepareStatement(sqlupdate);
												ps.setString(1, isiFullname);
												ps.setString(2, isiRole);
												ps.setString(3, isiEmail);
												ps.setString(4, isiPassword);
												ps.setString(5, isiid);
												ps.executeUpdate();
												JOptionPane.showMessageDialog(null, "Update Success!");
												ps.close();
												lblUserID.setText(null);
												txtFullname.setText(null);
												txtEmail.setText(null);
												txtPassword.setText(null);
												tblusers.getSelectionModel().clearSelection();
												setupRefresh();
											} catch (SQLException e1) {
												e1.printStackTrace();
											}
										}
										
									}
									
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						}
					}
				}

			}
		});
	}

	private void setupDelete() {
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String isiid = lblUserID.getText();
				
				if(tblusers.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select data to be deleted first!");
				}else {
					String sqldelete = "DELETE FROM users WHERE userid = ?";
					try {
						PreparedStatement ps = con.prepareStatement(sqldelete);
						ps.setString(1, isiid);
						ps.execute();
						JOptionPane.showMessageDialog(null, "Delete Success!");
						ps.close();
						lblUserID.setText(null);
						txtFullname.setText(null);
						txtEmail.setText(null);
						txtPassword.setText(null);
						tblusers.getSelectionModel().clearSelection();
						setupRefresh();

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
	}

	public IFManageAccounts() {
		con = Amorepos.getConnection();
		InitialComponent();
		SetupAtas();
		SetupTable();
		gabungAtas();
		setupRead();
		setupInsert();
		setupUpdate();
		setupDelete();
		add(Panel, BorderLayout.NORTH);
		add(PanelTable, BorderLayout.CENTER);
		InitialFrame();

	}

}
