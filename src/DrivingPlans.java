/**
 * Simply displays the different driving plans.
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*****************************************************
 * Generates the Driving Plans table. 
 * 
 * Provides the rate for each duration the user rents a
 * car (Occasional, Frequent, Daily). These values are 
 * used to calculate the estimated costs for renting a 
 * GT car.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class DrivingPlans {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrivingPlans window = new DrivingPlans();
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
	public DrivingPlans() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. The majority of the code
	 * of this class is to build the GUI.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 575, 317);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Driving Plans");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		
		MyDBConnector db = new MyDBConnector();
		Connection c = db.openConnection();
		PreparedStatement st;
		try {
			String sql = "SELECT plan_type as 'Driving Plan', discount as 'Discount', montly_payment as 'Monthly Payment', annual_payment as 'Annual Payment' FROM dbproject.driving_plan;";

			st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
				JScrollPane scrollPane = new JScrollPane();
				
				JButton btnGoBack = new JButton("Go Back");
				btnGoBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new Homepage();
						frame.dispose();
					}
				});
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(224)
									.addComponent(lblNewLabel))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(52)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnGoBack)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(55, Short.MAX_VALUE))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addGap(32)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addGap(53)
							.addComponent(btnGoBack)
							.addGap(133))
				);
				
			ResultSetTable table = new ResultSetTable(rs);
		scrollPane.setViewportView(table);
		table.setFont(new Font("Times New Roman", Font.BOLD, 13));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		
		table.setFillsViewportHeight(true);
		table.setBackground(new Color(255, 255, 102));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
				panel.setLayout(gl_panel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(255, 255, 0));
		titlePanel.setForeground(Color.BLUE);
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel title = new JLabel("GaTech Car Rental Service");
		title.setBackground(new Color(255, 255, 102));
		title.setFont(new Font("Georgia", Font.BOLD, 18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(0, 0, 0));
		titlePanel.add(title, BorderLayout.NORTH);

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
		

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
