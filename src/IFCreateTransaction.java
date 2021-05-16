import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

public class IFCreateTransaction extends JInternalFrame {
	Amorepos con;
	Login login;
	String staffname = login.staffcheck;
	JLabel lblid, lblisiid, lblisiname, lblname, lblqty, lblmenu, lblcart, lbltotal, lblisitotal, lblisiprice;
	JSpinner spinqty;
	JButton btnadd, btnupdate, btnremove, btncancel, btnfinish;
	JTable tblmenu, tblcart;
	JScrollPane scpmenu, scpcart;
	SpinnerModel modelspinner = new SpinnerNumberModel(1, 1, 99, 1);
	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel modeldua = new DefaultTableModel();
	Cart cart;
	String id, name="";
	Integer price, qty=0, totalprice, finaltotalprice=0, finaltotalpriceup=0;
	Integer check = 0;
	
	
	ArrayList<Cart> cartlist = new ArrayList<>();
	ArrayList<Integer> pricelist = new ArrayList<Integer>();

	private void initComponent() {
		lblid = new JLabel("ID:");
		lblisiid = new JLabel();
		lblisiname = new JLabel();
		lblname = new JLabel("Name:");
		lblqty = new JLabel("Quantity:");
		lblmenu = new JLabel("Menu");
		lblcart = new JLabel("Cart");
		lbltotal = new JLabel("Total: ");
		lblisitotal = new JLabel();
		lblisiprice = new JLabel();
				
		scpmenu = new JScrollPane();
		scpcart = new JScrollPane();

		spinqty = new JSpinner(modelspinner);

		btnadd = new JButton("Add");
		btnupdate = new JButton("Update");
		btnremove = new JButton("Remove");
		btncancel = new JButton("Cancel");
		btncancel.setPreferredSize(new Dimension(410,30));
		btnfinish = new JButton("Finish");
		btnfinish.setPreferredSize(new Dimension(410, 30));

		tblmenu = new JTable();
		tblcart = new JTable();

	}
	
	JPanel panelakhir = new JPanel(new GridLayout(3, 3, 5, 5));
	private void setupsatu() {

		panelakhir.add(lblid);
		panelakhir.add(lblisiid);
		panelakhir.add(btnadd);
		panelakhir.add(lblname);
		panelakhir.add(lblisiname);
		panelakhir.add(btnupdate);
		panelakhir.add(lblqty);
		panelakhir.add(spinqty);
		panelakhir.add(btnremove);

	}
	
	JPanel paneldua = new JPanel(new GridLayout(1, 2, 5, 5));
	private void setupdua() {
		
		paneldua.add(lblmenu);
		paneldua.add(lblcart);

	}
	
	private void setupsatudua() {
		JPanel panelsatudua = new JPanel(new GridLayout(2,1));
		
		setupsatu();
		setupdua();
		
		panelsatudua.add(panelakhir);
		panelsatudua.add(paneldua);
		
		add(panelsatudua, BorderLayout.NORTH);
	}

	JPanel paneltiga = new JPanel(new GridLayout(1, 2, 5, 5));
	private void setuptiga() {
		

		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Sell Price");

		try {
			String sql = "SELECT * FROM `menu`";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3) });
				tblmenu.setModel(model);
			}

		} catch (Exception e) {
		}

		scpmenu.getViewport().add(tblmenu);
		paneltiga.add(scpmenu);

		modeldua.addColumn("ID");
		modeldua.addColumn("Name");
		modeldua.addColumn("Price");
		modeldua.addColumn("Quantity");
		
		tblcart.setModel(modeldua);
		scpcart.getViewport().add(tblcart);
		paneltiga.add(scpcart);

		add(paneltiga, BorderLayout.CENTER);

	}
	
	JPanel panelempat = new JPanel(new GridLayout(1, 2, 5, 5));
	private void setupempat() {
		

		panelempat.add(lbltotal);
		panelempat.add(lblisitotal);


	}
	
	JPanel panellima = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private void setuplima() {
		
		panellima.add(btncancel);
		panellima.add(btnfinish);
		
	}
	
	private void setupempatlima() {
		JPanel panelempatlima = new JPanel(new GridLayout(2,1));
		setupempat();
		setuplima();
		
		panelempatlima.add(panelempat);
		panelempatlima.add(panellima);
		
		add(panelempatlima, BorderLayout.SOUTH);
	}
	
	private void setupadd() {
		btnadd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tblmenu.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select menu to be inserted!");
				}else {
					if(!cartlist.isEmpty()) {
						
						for(int a = 0; a<cartlist.size(); a++) {
							if(lblisiid.getText().equals(tblcart.getValueAt(a,0))) {
								
								tblcart.setRowSelectionInterval(a, a);
								int qtyy=  (int) tblcart.getValueAt(a, 3);
								qty = (Integer) (spinqty.getValue()) + qtyy;
								cart = new Cart(id, name, price, qty);
								cartlist.set(tblcart.getSelectedRow(), cart);
								JOptionPane.showMessageDialog(null, "Item Added !");
								lblisiid.setText(null);
								lblisiname.setText(null);
								tblmenu.getSelectionModel().clearSelection();
								tblcart.getSelectionModel().clearSelection();
								
								modeldua.setRowCount(0);
								
								finaltotalprice = 0;
								for(int i = 0; i< cartlist.size(); i++) {
									modeldua.addRow(new Object[] { cartlist.get(i).getId(), cartlist.get(i).getName(), cartlist.get(i).getPrice(), 
									cartlist.get(i).getQty() });
									
									totalprice = (cartlist.get(i).getPrice() * cartlist.get(i).getQty());
									finaltotalprice += totalprice;
								}
								
								lblisitotal.setText(finaltotalprice.toString());
								check = 1;
								break;
							}else {
								check = 2;
							}
						}
						
						if(check ==2) {
							try {
								spinqty.commitEdit();
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							qty = (Integer) (spinqty.getValue());
							cart = new Cart(id, name, price, qty);
							JOptionPane.showMessageDialog(null, "Item Added !");
							cartlist.add(cart);
							lblisiid.setText(null);
							lblisiname.setText(null);
							tblmenu.getSelectionModel().clearSelection();
							tblcart.getSelectionModel().clearSelection();
							
							modeldua.setRowCount(0);
							finaltotalprice = 0;
							
							for(int i = 0; i< cartlist.size(); i++) {
								modeldua.addRow(new Object[] { cartlist.get(i).getId(), cartlist.get(i).getName(), cartlist.get(i).getPrice(), 
										cartlist.get(i).getQty() });
								
								totalprice = (cartlist.get(i).getPrice() * cartlist.get(i).getQty());
								finaltotalprice +=totalprice;
							}
							
							lblisitotal.setText(finaltotalprice.toString());
							
						}
						
					
					}else if(cartlist.isEmpty()) {
						try {
							spinqty.commitEdit();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						qty = (Integer) (spinqty.getValue());
						cart = new Cart(id, name, price, qty);
						JOptionPane.showMessageDialog(null, "Item Added !");
						cartlist.add(cart);
						lblisiid.setText(null);
						lblisiname.setText(null);
						tblmenu.getSelectionModel().clearSelection();
						tblcart.getSelectionModel().clearSelection();
						
						modeldua.setRowCount(0);
						finaltotalprice = 0;
						
						for(int i = 0; i< cartlist.size(); i++) {
							modeldua.addRow(new Object[] { cartlist.get(i).getId(), cartlist.get(i).getName(), cartlist.get(i).getPrice(), 
									cartlist.get(i).getQty() });
							
							totalprice = (cartlist.get(i).getPrice() * cartlist.get(i).getQty());
							finaltotalprice +=totalprice;
						}
						
						lblisitotal.setText(finaltotalprice.toString());
					
					}

				}
			}
		});
	}
	
	private void setupmouse() {
		tblmenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblmenu.rowAtPoint(e.getPoint());
				id = tblmenu.getValueAt(row, 0).toString();
				lblisiid.setText(id);
				name = tblmenu.getValueAt(row, 1).toString();
				lblisiname.setText(name);
				price = (Integer) tblmenu.getValueAt(row, 2);
				
				
				

			}
		});
	}
	
	private void setupmousedua() {
		tblcart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row2 = tblcart.rowAtPoint(e.getPoint());
				id = tblcart.getValueAt(row2, 0).toString();
				lblisiid.setText(id);
				name = tblcart.getValueAt(row2, 1).toString();
				lblisiname.setText(name);
				price = (Integer) tblcart.getValueAt(row2, 2);
			}
			
		});
		
	}
	
	private void setupUpdate() {
		btnupdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblcart.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select item to be updated");
				}else {
					try {
						spinqty.commitEdit();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					qty = (Integer) (spinqty.getValue());
					cart = new Cart(id, name, price, qty);
					finaltotalprice = 0;
					JOptionPane.showMessageDialog(null, "Item Updated !");
					cartlist.set(tblcart.getSelectedRow(), cart);
					lblisiid.setText(null);
					lblisiname.setText(null);
					tblmenu.getSelectionModel().clearSelection();
					tblcart.getSelectionModel().clearSelection();
					
					modeldua.setRowCount(0);
				
					
					for(int i = 0; i< cartlist.size(); i++) {
						modeldua.addRow(new Object[] { cartlist.get(i).getId(), cartlist.get(i).getName(), cartlist.get(i).getPrice(), 
						cartlist.get(i).getQty() });
						
						totalprice = (cartlist.get(i).getPrice() * cartlist.get(i).getQty());
						finaltotalprice += totalprice;
					}
					
					
					lblisitotal.setText(finaltotalprice.toString());
				}
				
			}
		});
	}
	
	private void setupRemove() {
		btnremove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tblcart.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select item to be removed");
				}else {
					try {
						spinqty.commitEdit();
					} catch (ParseException e1) {
						
						e1.printStackTrace();
					}
					qty = (Integer) (spinqty.getValue());
					cart = new Cart(id, name, price, qty);
					finaltotalprice = 0;
					JOptionPane.showMessageDialog(null, "Item Removed !");
					cartlist.remove(tblcart.getSelectedRow());
					lblisiid.setText(null);
					lblisiname.setText(null);
					tblmenu.getSelectionModel().clearSelection();
					tblcart.getSelectionModel().clearSelection();
					
					modeldua.setRowCount(0);
				
					
					for(int i = 0; i< cartlist.size(); i++) {
						modeldua.addRow(new Object[] { cartlist.get(i).getId(), cartlist.get(i).getName(), cartlist.get(i).getPrice(), 
						cartlist.get(i).getQty() });
						
						totalprice = (cartlist.get(i).getPrice() * cartlist.get(i).getQty());
						finaltotalprice += totalprice;
					}
					
					
					lblisitotal.setText(finaltotalprice.toString());
				}
				
			}
		});
	}
	
	private void setupCancel() {
		btncancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	}
	
	private void setupFinish() {
		btnfinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cartlist.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Cart is empty !");
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
					
					
					
					String masuk1 = "INSERT INTO transactionheader(transactionid, staffid, transactiondate) VALUES('"+hasilid+"', '"+staffname+"',"
							+ "current_timestamp())";
					Statement stmasuk1 = con.createStatement();
					try {
						stmasuk1.executeUpdate(masuk1);
						stmasuk1.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					String masuk2 = "";
					PreparedStatement psmasuk2;
					for(int i = 0; i<cartlist.size(); i++) {
						try {
							masuk2 = "INSERT INTO transactiondetail(transactionid, menuid, quantity) VALUES(?, ?, ?)";
							psmasuk2 = con.prepareStatement(masuk2);
							psmasuk2.setString(1, hasilid);
							psmasuk2.setString(2, cartlist.get(i).getId());
							psmasuk2.setInt(3, cartlist.get(i).getQty());
							psmasuk2.execute();
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					}
					
					JOptionPane.showMessageDialog(null, "Transaction Created !");
					lblisitotal.setText(null);
					cartlist.removeAll(cartlist);
					modeldua.setRowCount(0);
					lblisiid.setText(null);
					lblisiname.setText(null);
					tblmenu.getSelectionModel().clearSelection();
					tblcart.getSelectionModel().clearSelection();
					
				}
				
				
			}
		});
	}
	

	
	private void initFrame() {
		setTitle("Transaction");
		setSize(850, 600);
		setClosable(true);
		setResizable(true);
		setVisible(true);
	}

	public IFCreateTransaction() {
		con = Amorepos.getConnection();
		initComponent();
		setupsatudua();
		setuptiga();
		setupempatlima();
		setupmouse();
		setupmousedua();
		setupadd();
		setupUpdate();
		setupRemove();
		setupCancel();
		setupFinish();
		initFrame();
	}

}
