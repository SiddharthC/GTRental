/**
 * As the name suggests, this class displays the
 * maintenance history report.
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
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/*****************************************************
 * Generates the Maintenance History Report
 * 
 * This allows the employees to view the Date-Time of
 * the maintenance request, the employee who sent the 
 * request, and the problems with the car. The report
 * is organized by the car that has the greatest number
 * of problems.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class MaintenanceHistoryReport {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaintenanceHistoryReport window = new MaintenanceHistoryReport();
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
	public MaintenanceHistoryReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 754, 428);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Maintenance History Report");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		
		MyDBConnector db = new MyDBConnector();
		Connection c = db.openConnection();
		PreparedStatement st;
		
		/**
		 * The following query generates the report.
		 */
		
		try {

			String sql = "SELECT C.reg_no AS 'Car', M.req_timestamp AS 'Date and Time', M.req_username AS 'Employee', " +
					"M.description AS 'Problem', S.Count as 'Problems' FROM maintenance_req AS M LEFT OUTER" +
					" JOIN Maintenance_History_Count as S on m.car_no = S.car_no, car AS C WHERE " +
					"C.reg_no = M.car_no ORDER BY S.count, C.reg_no; ";


			st = c.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
				
				JScrollPane scrollPane = new JScrollPane();
				
				JButton btnBack = new JButton("Back");
				btnBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new AdminHomePage();
						frame.dispose();
					}
				});
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap(186, Short.MAX_VALUE)
							.addComponent(lblNewLabel)
							.addGap(185))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(38)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 660, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(40, Short.MAX_VALUE))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addGap(51)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addGap(132)
							.addComponent(btnBack)
							.addGap(35))
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
			System.out.println(e.getMessage());
			System.out.println("Please make sure database connection is correct.");
			e.printStackTrace();
		}
		frame.setVisible(true);
	}
}