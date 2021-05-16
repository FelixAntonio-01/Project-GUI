
import java.awt.event.*;
import javax.swing.*;

public class Main2 extends JFrame{
	JMenu Account;
	JMenu Finance;
	JMenuItem miViewMonthlyReport;
	JMenuItem miLogOut2;
	
	JMenuBar mb;
	JDesktopPane dp;
	
	IFFinanceReport financereport = new IFFinanceReport();
	
	private void ALLogOut() {
		miLogOut2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			new Login();
			dispose();
			}
		});
	}
	
	private void InitialSetupAccountant() {
		Account = new JMenu("Account");
		Finance = new JMenu("Finance");
		
		miLogOut2 = new JMenuItem("Logout");
		miViewMonthlyReport = new JMenuItem("View Monthly Report");
		
		mb = new JMenuBar();
		dp = new JDesktopPane();
		
		setJMenuBar(mb);
		mb.add(Account);
		mb.add(Finance);
		Account.add(miLogOut2);
		Finance.add(miViewMonthlyReport);
		
		add(dp);
		
	}
	
	private void ALFinance() {
		miViewMonthlyReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!financereport.isClosed()) {
					IFFinanceReport financereport = new IFFinanceReport();
					dp.add(financereport);
					setContentPane(dp);
				}
				
				
			}
		});
	}
	
	private void InitialFrame() {
		setTitle("Amore POS");
		setSize(900,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public Main2() {
		InitialSetupAccountant();
		ALFinance();
		InitialFrame();
		ALLogOut();
		
	}



}
