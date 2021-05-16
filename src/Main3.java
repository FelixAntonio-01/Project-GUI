
import java.awt.event.*;
import javax.swing.*;

public class Main3 extends JFrame{
	JMenu Account;
	JMenu Transaction;
	JMenuItem miCreateTransaction;
	JMenuItem miLogOut3;
	
	JMenuBar mb3;
	JDesktopPane dp3;
	IFCreateTransaction Transactions = new IFCreateTransaction();
	
	private void ALLogOut() {
		miLogOut3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			new Login();
			dispose();
			}
		});
	}
	
	private void InitialSetupCashier() {
		Account = new JMenu("Account");
		Transaction = new JMenu("Transaction");
		
		miLogOut3 = new JMenuItem("Logout");
		miCreateTransaction = new JMenuItem("Create Transaction");
		
		mb3 = new JMenuBar();
		dp3 = new JDesktopPane();
		
		setJMenuBar(mb3);
		mb3.add(Account);
		mb3.add(Transaction);
		Account.add(miLogOut3);
		Transaction.add(miCreateTransaction);
		
		add(dp3);	
		
	}
	
	private void ALTransaction() {
		miCreateTransaction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Transactions.isClosed()) {
					IFCreateTransaction Transactions = new IFCreateTransaction();
					dp3.add(Transactions);
					setContentPane(dp3);
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
	
	
	public Main3() {
		InitialSetupCashier();
		ALTransaction();
		InitialFrame();
		ALLogOut();
	}

}
