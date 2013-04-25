/**
 * As the name suggests this class displays what cars are available
 * to rent for the time the user has selected.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

/*****************************************************
 * Generates the Car Availability table. 
 * 
 * Checks to see if there are any cars available given 
 * the overall constraints for time, location, and car
 * type by user.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/

public class CarAvailability {

	private JFrame frame;
	private ResultSetTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarAvailability window = new CarAvailability();
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
	public CarAvailability() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	   and integrates with the database to 
	   generate the car availability search.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 933, 306);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnGoBack = new JButton("Reserve");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {
					
					String sql = "INSERT INTO reservation ( req_timestamp, mem_username, car_no, loc_name, pickup_date, pickup_time,"
							+ " return_date, return_time, estimated_cost) VALUES (NOW(),?, ?, ?, convert(?, date), convert(?, time), "
							+ "convert(?, date), convert(?, time), ?);";

					st = c.prepareStatement(sql);

					if (DataClass.emp_car_search_flag == 1) {
						st.setString(1, DataClass.affec_username);
					} else {
						st.setString(1, DataClass.username);
					}
					
					st.setString(2, table.getModel().getValueAt(table.getSelectedRow(), 0).toString()); // get car number
					st.setString(3, DataClass.carLocation);
					st.setString(4, DataClass.pickupTimestamp);
					st.setString(5, DataClass.pickupTimestamp);
					st.setString(6, DataClass.returnTimestamp);
					st.setString(7, DataClass.returnTimestamp);
					st.setFloat(8, Float.parseFloat(table.getModel().getValueAt(table.getSelectedRow(), 14).toString()));
					
					if (st.executeUpdate() > 0) {
						JOptionPane.showMessageDialog(frame,
								"Reservation done...");
						if(DataClass.emp_car_search_flag == 1)
						{
							new RentalChangeRequest();
							frame.dispose();
						}
						else{
							new Homepage();
							frame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(frame,
								"Reservation failed...");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});

		MyDBConnector db = new MyDBConnector();
		Connection c = db.openConnection();
		PreparedStatement st;
		/**
		 * The following query displays the available cars, their options, locations, and the
		 * estimated costs based upon the user's plan type.
		 */
		try {

			String sql = "(SELECT c.reg_no as 'Car No', c.model as 'Model', c.car_type as 'Type', c.loc_name as 'Location', c.color as 'Color', c.hourly_rate as 'Hourly Rate', " +
			"c.hourly_rate * (SELECT (1 - d.discount) FROM driving_plan d WHERE d.plan_type = 'Frequent') as 'Freq Plan Rate', " +
			"c.hourly_rate * (SELECT (1 - d.discount) FROM driving_plan as d WHERE d.plan_type = 'Daily') as 'Daily Plan Rate', " +
			"c.daily_rate as 'Daily Rate', c.capacity as 'Capacity', c.transm_type as 'Transmission', c.b_tooth as 'Bluetooth', c.aux_cable as" +
			" 'Aux Cable', CASE sign(timestampdiff(day, ?, ?) - 1) WHEN - 1 THEN (SELECT CASE WHEN " +
			"min(r.pickup_time) IS NULL THEN '-NA-' ELSE min(r.pickup_time) END FROM reservation as r WHERE r.pickup_date = cast(? AS date) " +
			"AND r.pickup_time > cast(? AS time) AND r.cancel_flag = 0) ELSE '-NA-' END as 'Available Till', CASE sign(timestampdiff(day, ?, " +
			"?) - 1) WHEN - 1 THEN c.hourly_rate * (SELECT (1-d.discount) FROM member m, driving_plan as d WHERE m.plan_type = " +
			"d.plan_type and m.username= ?) * (case sign(timestampdiff(HOUR, ?, ?) - 1)" +
			" when -1 then 1 else timestampdiff(HOUR, ?, ?) end) ELSE c.daily_rate" +
			" * (timestampdiff(day, ?, ?)) END as 'Estimated Cost' FROM car as c WHERE " +
			"(c.car_type = ? OR c.model = ?) AND c.mflag = 0 AND c.loc_name = ? AND c.reg_no NOT IN (SELECT r.car_no FROM reservation as r " +
			"WHERE r.return_flag = 0 AND r.cancel_flag = 0 AND ((cast(? AS date) + cast(? AS time)) BETWEEN (r.pickup_date + r.pickup_time) " +
			"AND (r.return_date + r.return_time) OR (cast(? AS date) + cast(? AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND " +
			"(r.return_date + r.return_time)))) UNION (SELECT c.reg_no as 'Car No',  c.model as 'model', c.car_type as 'type', c.loc_name as 'location', c.color as 'color', " +
			"c.hourly_rate as 'Hourly Rate', c.hourly_rate * (SELECT (1 - d.discount) FROM driving_plan d WHERE " +
			"d.plan_type = 'Frequent') as 'Freq Plan Rate', c.hourly_rate * (SELECT (1 - d.discount) FROM " +
			"driving_plan as d WHERE d.plan_type = 'Daily') as 'Daily Plan Rate', c.daily_rate" +
			" as 'Daily Rate', c.capacity as 'Capacity', c.transm_type as 'Transmission', c.b_tooth as 'Bluetooth', " +
			"c.aux_cable as 'Aux Cable', CASE sign(timestampdiff(DAY, ?, ?) - 1) " +
			"WHEN - 1 THEN (SELECT CASE WHEN min(r.pickup_time) IS NULL THEN '-NA-' ELSE min(r.pickup_time) END " +
			"FROM reservation as r WHERE r.pickup_date = cast(? AS date) AND r.pickup_time > cast(? AS time) " +
			"AND r.cancel_flag = 0) ELSE '-NA-' END as 'Available Till', CASE sign(timestampdiff(DAY, ?, " +
			"?) - 1) WHEN - 1 THEN c.hourly_rate * (SELECT (1-d.discount) FROM member m, " +
			"driving_plan as d WHERE m.plan_type = d.plan_type and m.username = ?) * (case sign(timestampdiff(HOUR, ?, ?) - 1)" +
			" when -1 then 1 else timestampdiff(HOUR, ?, ?) end) ELSE c.daily_rate * (timestampdiff(DAY," +
			" ?, ?)) END as 'Estimated Cost' FROM car" +
			" as c WHERE (c.car_type = ? OR c.model = ?) AND c.mflag = 0 AND c.loc_name <> ? AND c.reg_no NOT IN (SELECT r.car_no " +
			"FROM reservation as r WHERE r.return_flag = 0 AND r.cancel_flag = 0 AND ((cast(? AS date) + cast" +
			"(? AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time) OR (cast" +
			"(? AS date) + cast(? AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + " +
			"r.return_time))) order by c.loc_name DESC);";			

			st = c.prepareStatement(sql);

			st.setString(1, DataClass.pickupTimestamp);
			st.setString(2, DataClass.returnTimestamp);
			st.setString(3, DataClass.pickupTimestamp);
			st.setString(4, DataClass.pickupTimestamp);
			st.setString(5, DataClass.pickupTimestamp);
			st.setString(6, DataClass.returnTimestamp);
			st.setString(7, DataClass.username);
			st.setString(8, DataClass.pickupTimestamp);
			st.setString(9, DataClass.returnTimestamp);
			st.setString(10, DataClass.pickupTimestamp);
			st.setString(11, DataClass.returnTimestamp);
			st.setString(12, DataClass.pickupTimestamp);
			st.setString(13, DataClass.returnTimestamp);
			st.setString(14, DataClass.carType);
			st.setString(15, DataClass.carModel);
			st.setString(16, DataClass.carLocation);
			st.setString(17, DataClass.pickupTimestamp);
			st.setString(18, DataClass.pickupTimestamp);
			st.setString(19, DataClass.returnTimestamp);
			st.setString(20, DataClass.returnTimestamp);
			
			st.setString(21, DataClass.pickupTimestamp);
			st.setString(22, DataClass.returnTimestamp);
			st.setString(23, DataClass.pickupTimestamp);
			st.setString(24, DataClass.pickupTimestamp);
			st.setString(25, DataClass.pickupTimestamp);
			st.setString(26, DataClass.returnTimestamp);
			st.setString(27, DataClass.username);			
			st.setString(28, DataClass.pickupTimestamp);
			st.setString(29, DataClass.returnTimestamp);
			st.setString(30, DataClass.pickupTimestamp);
			st.setString(31, DataClass.returnTimestamp);
			st.setString(32, DataClass.pickupTimestamp);
			st.setString(33, DataClass.returnTimestamp);
			
			st.setString(34, DataClass.carType);
			st.setString(35, DataClass.carModel);
			st.setString(36, DataClass.carLocation);
			st.setString(37, DataClass.pickupTimestamp);
			st.setString(38, DataClass.pickupTimestamp);
			st.setString(39, DataClass.returnTimestamp);
			st.setString(40, DataClass.returnTimestamp);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				rs.beforeFirst();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "There are no cars available for this"
						                           +"time range. They are all reserved!! ");
				new RentACar();
				frame.dispose();
				return;
			}
			
			table = new ResultSetTable(rs);
			table.setShowVerticalLines(true);
			table.setShowHorizontalLines(true);
			table.setRowSelectionAllowed(true);
			table.setCellSelectionEnabled(true);
			

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Car Availability");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RentACar();
				frame.dispose();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(294)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
							.addComponent(btnGoBack)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBack)
							.addComponent(lblNewLabel))
						.addComponent(btnGoBack))
					.addGap(17)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addGap(19))
		);
		
		table.setBackground(new Color(255, 255, 102));
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		scrollPane.setViewportView(table);
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
