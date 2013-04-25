/**
 * This is the members' home page.  It simply offers
 * a choice of three radio buttons and once the selection
 * is made the program opens the appropriate window.
 */

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*****************************************************
 * Generates the User Homepage
 * 
 * The user is directed to this homepage after logging
 * in. From here, the user can decide to view their
 * reservations, add a new reservation, or edit their 
 * personal information.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class Homepage {

	private JFrame frame;
	public static final int WIDTH      = 600;
	public static final int LENGTH     = 450;
	public static final String newLine = "\n";
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage window = new Homepage();
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
	public Homepage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. Outside of the
	 * actionlistener, the bulk of the code for this class
	 * is for building the GUI.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 571, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setBackground(SystemColor.textHighlight);
		frame.setForeground(Color.BLUE);
		frame.setPreferredSize(new Dimension(WIDTH, LENGTH));
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
		
		JLabel lblHomepage = new JLabel("Homepage");
		lblHomepage.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("Rent a Car");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBackground(new Color(255, 255, 102));
		
		final JRadioButton rdbtnEntervirePersonalInformation = new JRadioButton("Enter/View Personal Information");
		buttonGroup.add(rdbtnEntervirePersonalInformation);
		rdbtnEntervirePersonalInformation.setBackground(new Color(255, 255, 102));
		
		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("View Rental Information");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBackground(new Color(255, 255, 102));
		rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		/**
		 * The action listener determines which selection
		 * has been made by the user and opens the appropriate
		 * window.
		 */
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//to rent a car
				if (rdbtnNewRadioButton.isSelected()){
					new RentACar();
					frame.dispose();
				}
				//to enter personal info
				else if(rdbtnEntervirePersonalInformation.isSelected()){
					new PersonalInformation();
					frame.dispose();
				}
				//to view rental info
				else if (rdbtnNewRadioButton_1.isSelected()){
					new RentalInformation();
					frame.dispose();
				}
				
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(163)
							.addComponent(lblHomepage))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(115)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(rdbtnNewRadioButton_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(rdbtnEntervirePersonalInformation, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 183, Short.MAX_VALUE)
								.addComponent(rdbtnNewRadioButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnter))))
					.addGap(136))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHomepage)
					.addGap(40)
					.addComponent(rdbtnNewRadioButton)
					.addGap(18)
					.addComponent(rdbtnEntervirePersonalInformation)
					.addGap(18)
					.addComponent(rdbtnNewRadioButton_1)
					.addGap(29)
					.addComponent(btnEnter)
					.addGap(76))
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

