import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class IFManageMenu extends JInternalFrame {

	private JTextField nameField;
	private JTextField sellField;
	private JTextField ingredientField;
	private JTable table;
	private DefaultTableModel dtm;
	private JScrollPane scmenu;
	private Amorepos con;

	public IFManageMenu() {

		con = Amorepos.getConnection();
		
		ingredientField = new JTextField();
		sellField = new JTextField();
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		setTitle("Manage Account Menu");
		setSize(700, 550);
		setClosable(true);
		setResizable(true);
		setVisible(true);

		JPanel uppPnl = new JPanel();
		getContentPane().add(uppPnl, BorderLayout.NORTH);
		uppPnl.setLayout(new GridLayout(4, 3, 0, 0));

		JPanel idLblPnl = new JPanel();
		uppPnl.add(idLblPnl);
		idLblPnl.setLayout(new BorderLayout(0, 0));

		JLabel idLbl = new JLabel("ID");
		idLblPnl.add(idLbl);

		JPanel idFieldPnl = new JPanel();
		uppPnl.add(idFieldPnl);

		JLabel idField = new JLabel("");
		idFieldPnl.add(idField);

		JPanel emptyPnl = new JPanel();
		uppPnl.add(emptyPnl);

		JPanel nameLblPnl = new JPanel();
		uppPnl.add(nameLblPnl);
		nameLblPnl.setLayout(new BorderLayout(0, 0));

		JLabel nameLbl = new JLabel("Name");
		nameLblPnl.add(nameLbl);

		JPanel nameFieldPnl = new JPanel();
		uppPnl.add(nameFieldPnl);

		nameField = new JTextField();
		nameFieldPnl.add(nameField);
		nameField.setColumns(20);

		JPanel insertBtnPnl = new JPanel();
		uppPnl.add(insertBtnPnl);

		JButton insertBtn = new JButton("Insert");
		insertBtn.setPreferredSize(new Dimension(160,25));
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().equals("")||sellField.getText().equals("")||ingredientField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fields must be filled");
					
				}else {
					if(isNumeric(sellField.getText()) == false ||isNumeric(ingredientField.getText()) == false){
						JOptionPane.showMessageDialog(null, "Prices must be a number");
					}else {
						String id = generateId();
						PreparedStatement pstat = con.prepareStatement(
								"INSERT INTO menu (menuid,name,sellprice,ingredientprice) values (?,?,?,?)");
						try {
							pstat.setString(1, id);
							pstat.setString(2, nameField.getText());
							pstat.setString(3, sellField.getText());
							pstat.setString(4, ingredientField.getText());
							pstat.execute();
							idField.setText(null);
							nameField.setText(null);
							ingredientField.setText(null);
							sellField.setText(null);
							refresh();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Insert Success!");
					}
				}
			}
		});
		insertBtnPnl.add(insertBtn);

		JPanel sellPricePnl = new JPanel();
		uppPnl.add(sellPricePnl);
		sellPricePnl.setLayout(new BorderLayout(0, 0));

		JLabel sellPriceLbl = new JLabel("Sell Price");
		sellPricePnl.add(sellPriceLbl);

		JPanel sellFieldPnl = new JPanel();
		uppPnl.add(sellFieldPnl);

	
		sellFieldPnl.add(sellField);
		sellField.setColumns(20);

		JPanel updateBtnPnl = new JPanel();
		uppPnl.add(updateBtnPnl);

		JButton updateBtn = new JButton("Update");
		updateBtn.setPreferredSize(new Dimension(160,25));
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select data to be updated first");
				} else {
					
					if (nameField.getText().equals("")|| sellField.getText().equals("")||ingredientField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Fields must be filled");
					
					}else {
						if(isNumeric(sellField.getText()) == false ||isNumeric(ingredientField.getText()) == false){
							JOptionPane.showMessageDialog(null, "Prices must be a number");
						}else {
							PreparedStatement pstat = con.prepareStatement("UPDATE menu SET name=?,sellprice=?,ingredientprice=? where menuid=?");
							try {
								pstat.setString(1, nameField.getText());
								pstat.setString(2, sellField.getText());
								pstat.setString(3, ingredientField.getText());
								pstat.setString(4, idField.getText());
								pstat.execute();
								JOptionPane.showMessageDialog(null, "Update Success!");
								idField.setText(null);
								nameField.setText(null);
								ingredientField.setText(null);
								sellField.setText(null);
								refresh();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
					
				}
			}
		});
		updateBtnPnl.add(updateBtn);

		JPanel ingredientLblPnl = new JPanel();
		uppPnl.add(ingredientLblPnl);
		ingredientLblPnl.setLayout(new BorderLayout(0, 0));

		JLabel ingredientPriceLbl = new JLabel("Ingerient Price");
		ingredientLblPnl.add(ingredientPriceLbl);

		JPanel ingredientFieldPnl = new JPanel();
		uppPnl.add(ingredientFieldPnl);

	
		ingredientFieldPnl.add(ingredientField);
		ingredientField.setColumns(20);

		JPanel deleteBtnPnl = new JPanel();
		
		uppPnl.add(deleteBtnPnl);

		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setPreferredSize(new Dimension(160,25));
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!table.getSelectionModel().isSelectionEmpty()) {
					PreparedStatement pstat=con.prepareStatement("DELETE FROM menu WHERE menuid=?");
					try {
						pstat.setString(1, idField.getText());
						pstat.execute();
						JOptionPane.showMessageDialog(null, "Delete Success");
						idField.setText(null);
						nameField.setText(null);
						ingredientField.setText(null);
						sellField.setText(null);
						refresh();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select data to be deleted first");
				}
			}
		});
		deleteBtnPnl.add(deleteBtn);

		JPanel btmPnl = new JPanel();
		btmPnl.setLayout(new GridLayout());

		dtm = new DefaultTableModel();

		JPanel PanelTable = new JPanel();
		table = new JTable();

		scmenu = new JScrollPane();

		dtm.addColumn("ID");
		dtm.addColumn("Name");
		dtm.addColumn("Sell Price");
		dtm.addColumn("Ingredient Price");

		try {
			String sql = "SELECT * FROM `menu`";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				dtm.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), });
				table.setModel(dtm);
			}

		} catch (Exception e) {

		}

		scmenu.getViewport().add(table);
		scmenu.setPreferredSize(new Dimension(600, 500));
		PanelTable.add(scmenu);

		getContentPane().add(PanelTable, BorderLayout.CENTER);

		setVisible(true);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				idField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				nameField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				sellField.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
				ingredientField.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
			}
		});
	}

	static boolean isNumeric(String num) {
	    if (num == null) {
	        return false;
	    }
	    try {
	        int d = Integer.parseInt(num);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	private String generateId() {

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

		return hasilid;
	}

	private void refresh() throws IllegalArgumentException {

		dtm.setRowCount(0);

		try {
			String sql = "SELECT * FROM `menu`";
			Statement stt = con.createStatement();
			ResultSet rss = stt.executeQuery(sql);
			while (rss.next()) {
				dtm.addRow(new Object[] { rss.getString(1), rss.getString(2), rss.getString(3), rss.getString(4), });
				table.setModel(dtm);
			}

		} catch (Exception e) {

		}

		scmenu.getViewport().add(table);
		scmenu.setPreferredSize(new Dimension(600, 500));

	}
}
