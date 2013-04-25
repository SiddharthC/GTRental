/**
 * As the name implies, this page is the main landing page for
 * employees and administrators.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

/********************************************************
 * Generates the Administrator/GTCR Employee Homepage
 * 
 * This allows the Administrator/GTCR Employee to access
 * the reports of the company's operational statuses,
 * manage cars, add maintenance requests, and handle
 * rental change requests,
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *******************************************************/

public class AdminHomePage {

	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 * @param String[] args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminHomePage window = new AdminHomePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminHomePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 634, 458);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setBackground(SystemColor.textHighlight);
		frame.setForeground(Color.BLUE);
		frame.setPreferredSize(new Dimension(600, 450));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 0));
		panel.setForeground(Color.BLUE);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel title = new JLabel("GaTech Car Rental Service");
		title.setBackground(new Color(255, 255, 0));
		title.setFont(new Font("Georgia", Font.BOLD, 18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(0, 0, 0));
		panel.add(title, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JLabel lblHomepage = new JLabel("GTCR Employee Homepage");
		lblHomepage.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("Manage Cars");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBackground(new Color(255, 255, 102));
		
		final JRadioButton rdbtnEntervirePersonalInformation = new JRadioButton("Maintenance Requests");
		buttonGroup.add(rdbtnEntervirePersonalInformation);
		rdbtnEntervirePersonalInformation.setBackground(new Color(255, 255, 102));
		
		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Rental Request Change");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBackground(new Color(255, 255, 102));
		rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		final JRadioButton rdbtnViewReports = new JRadioButton("View Reports");
		buttonGroup.add(rdbtnViewReports);
		rdbtnViewReports.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnViewReports.setBackground(new Color(255, 255, 102));
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Revenue Generated Report", "Location Preference Report", "Frequent Users Report", "Maintenance History Report"}));
		/**
		 * The action listener tests which radiobutton is selected and sends
		 * the user to the appropriate page.
		 */
		JButton btnNewButton = new JButton("Next >>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Manage Cars Button
				if (rdbtnNewRadioButton.isSelected()){
					new ManageCar();
					frame.dispose();
				}
				//Maintenance Requests Button
				else if (rdbtnEntervirePersonalInformation.isSelected()){
					new MaintenanceRequest();
					frame.dispose();
				}
				//Rental Request Change Button
				else if (rdbtnNewRadioButton_1.isSelected()){
					new RentalChangeRequest();
					frame.dispose();
				}
				//View Reports Button
				else if (rdbtnViewReports.isSelected()){
					String compare = (String)comboBox.getSelectedItem();
					
					if (compare.equals("Revenue Generated Report")){
						new RevenueGeneratedReport();
						frame.dispose();
					}
					
					else if (compare.equals("Location Preference Report")){
						new LocPrefReport();
						frame.dispose();
					}
					
					else if (compare.equals("Frequent Users Report")){
						new FreqUserReport();
						frame.dispose();
					}
					
					else if (compare.equals("Maintenance History Report")){
						new MaintenanceHistoryReport();
						frame.dispose();
					}

				}
				else {
					JOptionPane.showMessageDialog(null, "Please select an option.");
				}
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(82)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(rdbtnNewRadioButton_1, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(rdbtnEntervirePersonalInformation, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(rdbtnViewReports, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(17))
						.addComponent(rdbtnNewRadioButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(107)
					.addComponent(lblHomepage)
					.addContainerGap(285, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHomepage)
					.addGap(29)
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnEntervirePersonalInformation)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNewRadioButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnViewReports)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLogOut = new JMenuItem("LogOut");
		mnFile.add(mntmLogOut);
		
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Thank you for using SMS's Georgia Tech Car Rental Service.","Log Out",JOptionPane.OK_OPTION);
				System.exit(0);
			}
		});
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "This application is brought to you by Siddarth Choudhary, Mark Owens,\n" +
						                             "and Shreyyas Vanarase. SMS Incorporated has generated a fundamental application providing\n"+
						                             "Georgia Tech students a rental car service. We hope you enjoy this application.\n\n"+
													 "---------------------------------------------------------------------------------------------------------------------------------\n\n" +
													 "To rent your own car, if you are already a user, simply login. \n" +
													 "Otherwise please click \"Register\"to sign up, log in, and then rent your car!", "Georgia Tech Car Rental Service - SMS" ,JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		frame.setVisible(true);
	}
}
