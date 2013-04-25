/**
 * This class is used by employees to request
 * maintenance on a particular automobile.  The majority
 * of the code simply formats the GUI.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*****************************************************
 * Generates the Maintenance Request Page
 * 
 * This allows the employees to add in maintenance
 * requests. They need to choose a location, which pulls
 * up all the cars in that location. They are able to
 * select the car and write a brief description of the 
 * problem with this car. Once this maintenance request 
 * has been submitted, this car does not show up in the 
 * search results for the members.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/

public class MaintenanceRequest {

	private JFrame frame;
	private JTextArea problemDescription;
	@SuppressWarnings("rawtypes")
	private JComboBox car_list;
	@SuppressWarnings("rawtypes")
	private JComboBox location;
	private String[] car_numbers;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaintenanceRequest window = new MaintenanceRequest();
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
	public MaintenanceRequest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 449, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(450, 404));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		/**
		 * This action listener triggers the SQL queries 
		 * once a user submits a request.
		 */
		
		JButton btnGoBack = new JButton("Submit Request");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(car_list.getSelectedItem().toString().equals("--Select--")||problemDescription.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Please select a car and provide the problem description...");
					return;
				}
				
				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					String sql = "INSERT INTO Maintenance_req (req_timestamp, car_no, req_username, description) " +
							"VALUES(NOW(), ?, ?, ?);";

					st = c.prepareStatement(sql);
					st.setString(1, (String) car_list.getSelectedItem());
					st.setString(2, DataClass.username);
					st.setString(3, problemDescription.getText());
					
					if(st.executeUpdate()>0){
						JOptionPane.showMessageDialog(frame, "Maintenance request submitted successfully...");
					}
					else{
						JOptionPane.showMessageDialog(frame, "Submission Failed...");
						return;
					}
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}


			}
		});
		
		JLabel lblNewLabel = new JLabel("Maintenance Request");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JLabel lblLocation = new JLabel("Brief Description of Problem:");
		lblLocation.setFont(new Font("Times New Roman", Font.BOLD, 13));
		
		JLabel lblPickupTime = new JLabel("Choose Location:");
		lblPickupTime.setFont(new Font("Times New Roman", Font.BOLD, 13));
		
		JLabel lblReturnTime = new JLabel("Choose Car:");
		lblReturnTime.setFont(new Font("Times New Roman", Font.BOLD, 13));
		
		car_list = new JComboBox();
		car_list.setModel(new DefaultComboBoxModel(new String [] {"--Select--"}));
		
		location = new JComboBox();
		
		/**
		 * The following code retrieves cars that are
		 * located at the given location
		 */
		
		location.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					String sql = "select reg_no from car where loc_name = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, (String) location.getSelectedItem());
					
					ResultSet rs = st.executeQuery();
					
					rs.last();
					int n = rs.getRow();
					rs.beforeFirst();
					
					car_numbers = new String[n];
					
					int i=0;
					while(rs.next()){
						car_numbers[i] = rs.getString("reg_no");
						i++;
					}
					
					car_list.setModel(new DefaultComboBoxModel(car_numbers));
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}


				
			}
		});
		
		/**
		 * Combo box created with all locations given as options.
		 * 
		 */
		location.setModel(new DefaultComboBoxModel(new String[] {"tsquare", "klaus", "sCenter", "varsity"}));
		
		JLabel timeLabel = new JLabel("New label");
		Date now = new Date();
        //Set date format as you want
        SimpleDateFormat sf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm"); 
		timeLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
        timeLabel.setText("          Today's date and time: " +sf.format(now));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdminHomePage();
				frame.dispose();
			}
		});
        
		/**
		 * The remaining code is used to format the GUI
		 */
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(142, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(148))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(58)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(timeLabel)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblReturnTime, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPickupTime)
								.addComponent(lblLocation))
							.addGap(39)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(car_list, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(location, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnGoBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(8)
					.addComponent(timeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPickupTime)
						.addComponent(location, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReturnTime, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(car_list, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblLocation)
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnGoBack))
					.addGap(18)
					.addComponent(btnBack)
					.addGap(34))
		);
		
		problemDescription = new JTextArea();
		problemDescription.setWrapStyleWord(true);
		scrollPane.setViewportView(problemDescription);
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
		
		frame.setVisible(true);
	}
}