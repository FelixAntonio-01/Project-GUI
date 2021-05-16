
import java.awt.event.*;

import javax.swing.*;

public class Main extends JFrame {
	
	ImageIcon userimage;
	JLabel labelimage;
	JMenu Account, Manage;
	JMenuItem miLogOut, miAccounts, miRestaurantMenu;
	JMenuBar mbAdmin;
	IFManageAccounts manageAccounts = new IFManageAccounts();
	JDesktopPane dpmanageAccount, dpmanageMenu;

	IFManageMenu manageMenu = new IFManageMenu();
	
	private void bguser() {
		userimage = new ImageIcon(getClass().getResource("emailmarketing.png"));
		labelimage = new JLabel(userimage);
	}

	private void InitialFrame() {
		setTitle("Amore POS");
		setSize(900, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
//		setContentPane(labelimage);
		
	}

	public void InitialSetupComponent() {
		Account = new JMenu("Account");
		Manage = new JMenu("Manage");

		miLogOut = new JMenuItem("Logout");
		miAccounts = new JMenuItem("Accounts");
		miRestaurantMenu = new JMenuItem("Restaurant Menu");

		mbAdmin = new JMenuBar();
		dpmanageAccount = new JDesktopPane();
		dpmanageMenu = new JDesktopPane();

		setJMenuBar(mbAdmin);
		mbAdmin.add(Account);
		mbAdmin.add(Manage);
		Account.add(miLogOut);
		Manage.add(miAccounts);
		Manage.add(miRestaurantMenu);
	


	}

	private void ALLogOut() {
		miLogOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				dispose();
			}
		});
	}

	private void ALAccounts() {
		miAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!manageAccounts.isClosed()) {
					IFManageAccounts manageAccounts = new IFManageAccounts();
					dpmanageAccount.add(manageAccounts);
//					add(manageAccounts);
					setContentPane(dpmanageAccount);
				}
			}
		});
	}

	private void AlMenu() {
		miRestaurantMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!manageMenu.isClosed()) {
					IFManageMenu manageMenu = new IFManageMenu();
					dpmanageMenu.add(manageMenu);
//					add(manageMenu);
					
					manageMenu.setVisible(true);
					setContentPane(dpmanageMenu);

				}

			}
		});
	}

	public Main() {
		InitialSetupComponent();
		ALAccounts();
		AlMenu();
		ALLogOut();
		bguser();
		InitialFrame();

	}

}
