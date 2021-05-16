import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.OptionPaneUI;
import javax.swing.table.DefaultTableModel;

public class IFFinanceReport extends JInternalFrame{
	Amorepos con;
	JLabel lblmonth, lblyear;
	JTextField txtmonth, txtyear;
	JButton btnview;
	JTable tblfinance;
	JScrollPane scpfinance;
	int isimonth = 0;
	int isiyear = 0;
	char check;
	DefaultTableModel modell = new DefaultTableModel();
	
	
	private void initFrame() {
		setTitle("Finance Report");
		setSize(700, 550);
		setClosable(true);
		setResizable(true);
		setVisible(true);
//		setLayout(new GridLayout(2, 1, 5, 5));
	}
	
	private void initcomponent() {
	lblmonth = new JLabel("Month:");
	lblyear = new JLabel("Year:");
	
	txtmonth = new JTextField();
	txtyear = new JTextField();
	
	btnview = new JButton("View");
	
	tblfinance = new JTable();
	scpfinance = new JScrollPane();
	
	
	}
	
	private void setupatas() {
		JPanel panelatas = new JPanel(new GridLayout(2,3,5,5));
		
		panelatas.add(lblmonth);
		panelatas.add(txtmonth);
		panelatas.add(btnview);
		panelatas.add(lblyear);
		panelatas.add(txtyear);
		
		add(panelatas, BorderLayout.NORTH);
	}
	
	JPanel paneltable = new JPanel(new GridLayout(1,1));
	private void refresh() {
		modell.setRowCount(0);
		
		String sqlread = "select transactionheader.transactionid, SUM(ingredientprice*quantity) as 'modal', SUM(sellprice*quantity) as 'earn', (SUM(sellprice*quantity) - SUM(ingredientprice*quantity)) as 'gain'\r\n" + 
				"from transactionheader join transactiondetail\r\n" + 
				"on transactionheader.transactionid = transactiondetail.transactionid join menu\r\n" + 
				"on transactiondetail.menuid = menu.menuid\r\n" + 
				"where month(transactionheader.transactiondate) = '"+isimonth+"' and year(transactionheader.transactiondate) = '"+isiyear+"'\r\n" + 
				"group by transactionheader.transactionid";
		
		
		try {
			Statement stread = con.createStatement();
			ResultSet rs = stread.executeQuery(sqlread);
			while (rs.next()) {
				modell.addRow(new Object[] { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
				tblfinance.setModel(modell);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		scpfinance.getViewport().add(tblfinance);
		paneltable.add(scpfinance);
		add(paneltable, BorderLayout.SOUTH);
	}
	
	private void setuptable() {
		
		modell.addColumn("Transaction ID");
		modell.addColumn("Modal");
		modell.addColumn("Earn");
		modell.addColumn("Gain");
		
		scpfinance.getViewport().add(tblfinance);
		paneltable.add(scpfinance);
		add(paneltable, BorderLayout.SOUTH);

		btnview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isNumeric(txtmonth.getText())==false || isNumeric(txtyear.getText())==false){
					JOptionPane.showMessageDialog(null, "Month and Year must be a number !");
				}else {
					isimonth = Integer.parseInt(txtmonth.getText());
					if(isimonth<1 || isimonth>12) {
						JOptionPane.showMessageDialog(null, "Month must be between 1-12");
					}else {
						
						isiyear = Integer.parseInt(txtyear.getText());
						
						refresh();
					}
				}
				
				
				
				
				
				
				
			}
		});
		
	}
	
	static boolean isNumeric(String a) {
	    if (a == null) {
	        return false;
	    }
	    try {
	        int d = Integer.parseInt(a);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public IFFinanceReport() {
		con = Amorepos.getConnection();
		initcomponent();
		setupatas();
		setuptable();
		initFrame();
	}

}
