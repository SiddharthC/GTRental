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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*****************************************************
 * Generates the Rental Information Page
 * 
 * This allows the users to view his past rental history and the current
 * reservation if he has one.
 * 
 * The user can update their current reservation to a new return time and any
 * late charges that need to be applied are added to the user's balance.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 * 
 *****************************************************/

public class RentalInformation {

	private JFrame frame;
	private JTextField new_return_date;
	private JTextField new_return_time;
	private ResultSetTable table1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalInformation window = new RentalInformation();
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
	public RentalInformation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 597);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Rental Information");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		MyDBConnector db = new MyDBConnector();
		Connection c = db.openConnection();
		PreparedStatement st;
		try {
			String sql = "SELECT a.regID as 'Reg ID', a.pickup_date as 'Pickup Date', a.pickup_time as 'Pickup Time', a.return_date as 'Return Date', a.return_time as 'Return Time', b.model as 'Model', "
					+ "a.loc_name as 'Location', a.estimated_cost + a.late_fee AS 'Total Cost', b.reg_no as 'Car Number' FROM reservation AS a, car AS b WHERE "
					+ "a.car_no = b.reg_no AND a.mem_username = ? AND cast(concat(a.return_date,' ', a.return_time) as datetime) > cast(NOW() as datetime) and cast(concat(a.pickup_date,' ', a.pickup_time) as datetime) < cast(NOW() as datetime) AND a.cancel_flag = 0; ";

			
			/**
			 * The correct query 
			 * 
			 * "SELECT a.regID as 'Reg ID', a.pickup_date as 'Pickup Date', a.pickup_time as 'Pickup Time', a.return_date as 'Return Date', a.return_time as 'Return Time', b.model as 'Model', "
					+ "a.loc_name as 'Location', a.estimated_cost + a.late_fee AS 'Total Cost', b.reg_no as 'Car Number' FROM reservation AS a, car AS b WHERE "
					+ "a.car_no = b.reg_no AND a.mem_username = ? AND cast(concat(a.return_date,' ', a.return_time) as datetime) > cast(NOW() as datetime) AND a.cancel_flag = 0; ";
			 * 
			 * */
			
			st = c.prepareStatement(sql);
			st.setString(1, DataClass.username);
			ResultSet rs = st.executeQuery();

			JScrollPane scrollPane = new JScrollPane();

			JButton btnGoBack = new JButton("Go Back");
			btnGoBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new Homepage();
					frame.dispose();
				}
			});

			JLabel lblCurrentReservations = new JLabel("Current Reservations");
			lblCurrentReservations.setFont(new Font("Times New Roman",
					Font.BOLD, 13));
			lblCurrentReservations
					.setHorizontalAlignment(SwingConstants.CENTER);

			JScrollPane scrollPane_1 = new JScrollPane();

			JLabel lblPreviousReservations = new JLabel("Previous Reservations");
			lblPreviousReservations
					.setHorizontalAlignment(SwingConstants.CENTER);
			lblPreviousReservations.setFont(new Font("Times New Roman",
					Font.BOLD, 13));

			JLabel lblNewLabel_1 = new JLabel("Choose Return Time:");

			new_return_date = new JTextField();
			new_return_date.setColumns(10);

			new_return_time = new JTextField();
			new_return_time.setColumns(10);

			JButton btnNewButton = new JButton("Update");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (new_return_time.getText().isEmpty()
							|| new_return_date.getText().isEmpty()) {
						JOptionPane
								.showMessageDialog(frame,
										"Please select a resservation and then change the time...");
						return;
					}

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {

						Date pickup_date = sdf.parse(table1.getModel()
								.getValueAt(table1.getSelectedRow(), 1)
								.toString()
								+ " "
								+ table1.getModel()
										.getValueAt(table1.getSelectedRow(), 2)
										.toString());
						
						 Date return_date = sdf.parse(new_return_date.getText() +
						 " "
						 + new_return_time.getText());
						

						Calendar c = Calendar.getInstance();
						Date tmp_date = sdf.parse(sdf.format(new Date()));

						c.setTime(pickup_date);
						c.add(Calendar.DATE, 2);

						if (!pickup_date.before(return_date)) {
							JOptionPane
									.showMessageDialog(frame,
											"Please input correct return date...");
							return;
						} else if (c.getTime().before(return_date)) {
							JOptionPane
									.showMessageDialog(frame,
											"Reservation cannot be made for more than two days at a time...");
							return;
						}else if(tmp_date.after(return_date))
						{
							JOptionPane.showMessageDialog(frame, 
									"Past return dates are not allowed...");
							return;
						}
						
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(frame, "Date error...");
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}

					MyDBConnector db = new MyDBConnector();
					Connection c = db.openConnection();
					PreparedStatement st;
					ResultSet rs;
					String sql;
					try {
						// if someone affected then abort
						sql = "SELECT * FROM reservation as r WHERE r.return_flag = 0 AND" +
								" r.cancel_flag = 0 AND (cast(concat(cast(? AS date), ' ', " +
								"cast(? AS time)) as datetime) BETWEEN  cast(concat(r.pickup_date," +
								" ' ', r.pickup_time) as datetime) AND cast(concat(r.return_date, ' '," +
								" r.return_time) as datetime)) AND r.car_no = ? AND r.regID <> ?;";

						st = c.prepareStatement(sql);
						st.setString(
								1,
								table1.getModel()
										.getValueAt(table1.getSelectedRow(), 3)
										.toString());
						st.setString(
								2,
								table1.getModel()
										.getValueAt(table1.getSelectedRow(), 4)
										.toString());
						st.setString(
								3,
								table1.getModel()
										.getValueAt(table1.getSelectedRow(), 8)
										.toString());
						st.setInt(
								4,
								Integer.parseInt(table1.getModel()
										.getValueAt(table1.getSelectedRow(), 0)
										.toString()));

						rs = st.executeQuery();
						if (rs.next()) {
							JOptionPane
									.showMessageDialog(frame,
											"Cannot extend reservation - Reservation Conflict");
							return;
						} else {
							// make update return time

							sql = "UPDATE reservation as r SET r.return_date = cast(? as date), r.return_time ="
									+ " cast(? as time) WHERE r.regID = ?;";

							st = c.prepareStatement(sql);
							st.setString(1, new_return_date.getText());
							st.setString(2, new_return_time.getText());
							st.setInt(
									3,
									Integer.parseInt(table1
											.getModel()
											.getValueAt(
													table1.getSelectedRow(), 0)
											.toString()));

							if (st.executeUpdate() > 0) {
								JOptionPane.showMessageDialog(frame,
										"Return time updated successfully...");
							} else {
								JOptionPane.showMessageDialog(frame,
										"Update failed...");
							}

						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			});

			JSeparator separator = new JSeparator();
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(94)
						.addComponent(lblNewLabel_1)
						.addGap(32)
						.addComponent(new_return_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
						.addComponent(new_return_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(32)
						.addComponent(btnNewButton)
						.addContainerGap(175, Short.MAX_VALUE))
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
					.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap(460, Short.MAX_VALUE)
						.addComponent(lblPreviousReservations, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGap(214))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(51)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 698, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(48, Short.MAX_VALUE))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(52)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(45, Short.MAX_VALUE))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(319)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblCurrentReservations)
								.addContainerGap())
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(355))))
					.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
						.addContainerGap(545, Short.MAX_VALUE)
						.addComponent(btnGoBack)
						.addGap(181))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(13)
						.addComponent(lblNewLabel)
						.addGap(18)
						.addComponent(lblCurrentReservations)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(lblNewLabel_1)
							.addComponent(new_return_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnNewButton)
							.addComponent(new_return_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(22)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(19)
						.addComponent(lblPreviousReservations, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnGoBack)
						.addGap(33))
			);

			table1 = new ResultSetTable(rs);
			table1.setRowSelectionAllowed(true);
			table1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					new_return_date.setText(table1.getModel()
							.getValueAt(table1.getSelectedRow(), 3).toString());
					new_return_time.setText(table1.getModel()
							.getValueAt(table1.getSelectedRow(), 4).toString());
				}
			});
			scrollPane.setViewportView(table1);
			table1.setFont(new Font("Times New Roman", Font.BOLD, 13));
			table1.setColumnSelectionAllowed(true);
			table1.setCellSelectionEnabled(true);

			table1.setFillsViewportHeight(true);
			table1.setBackground(new Color(255, 255, 102));
			table1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel.setLayout(gl_panel);

			sql = "SELECT a.regID as 'Reg ID', a.pickup_date as 'Pickup Date', a.pickup_time as 'Pickup Time', a.return_date as 'Return Date', a.return_time as 'Return Time', b.model as 'Model', a.loc_name as 'Location', "
					+ "a.estimated_cost + a.late_fee AS 'Total Cost', CASE WHEN a.late_by IS NULL THEN 'On Time' "
					+ "ELSE 'Late - ' || hour(a.late_by) END as 'Return Status', b.reg_no as 'Car Number' FROM reservation AS a, car AS b WHERE a.car_no = b.reg_no "
					+ "AND a.mem_username = ? AND (cast(concat(a.return_date,' ', a.return_time) as datetime) < cast(NOW() as datetime) OR a.cancel_flag = 1);";

			st = c.prepareStatement(sql);
			st.setString(1, DataClass.username);
			ResultSet rs2 = st.executeQuery();
			ResultSetTable table2 = new ResultSetTable(rs2);
			table2.setFont(new Font("Times New Roman", Font.BOLD, 13));
			table2.setFillsViewportHeight(true);
			table2.setColumnSelectionAllowed(true);
			table2.setCellSelectionEnabled(true);
			table2.setBorder(new LineBorder(new Color(0, 0, 0)));
			table2.setBackground(new Color(255, 255, 102));
			scrollPane_1.setViewportView(table2);

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
					JOptionPane
							.showMessageDialog(
									frame,
									"Thank you for using SMS's Georgia Tech Car Rental Service.",
									"Log Out", JOptionPane.OK_OPTION);
					System.exit(0);
				}
			});
			JMenu mnHelp = new JMenu("Help");
			menuBar.add(mnHelp);

			JMenuItem mntmAbout = new JMenuItem("About");
			mnHelp.add(mntmAbout);

			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane
							.showMessageDialog(
									frame,
									"This application is brought to you by Siddarth Choudhary, Mark Owens,\n"
											+ "and Shreyyas Vanarase. SMS Incorporated has generated a fundamental application providing\n"
											+ "Georgia Tech students a rental car service. We hope you enjoy this application.\n\n"
											+ "---------------------------------------------------------------------------------------------------------------------------------\n\n"
											+ "To rent your own car, if you are already a user, simply login. \n"
											+ "Otherwise please click \"Register\"to sign up, log in, and then rent your car!",
									"Georgia Tech Car Rental Service - SMS",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.setVisible(true);

	}
}
