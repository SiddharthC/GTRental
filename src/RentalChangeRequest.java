/**
 * This class permits the employee to make a change
 * to an existing reservation in the event that
 * a member is going to be late returning a car.
 * The class also retrieves the reservation information of any
 * other member who may be impacted by the car being returned late. 
 */

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*****************************************************
 * Generates the Rental Request Change Page
 * 
 * This allows the employees to manually change the rental request a user has
 * made. Employees need to use this option when a member calls the GTCR customer
 * care and inform them that he is late. This class provides the functionality
 * if the user cannot access the system to extend the reservation.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 * 
 *****************************************************/

public class RentalChangeRequest {

	private JFrame frame;
	private JTextField username;
	private JTextField affec_username;
	private JTextField affec_email;
	private JTextField affec_phone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalChangeRequest window = new RentalChangeRequest();
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
	public RentalChangeRequest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 843, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		final JTextField orig_return_date = new JTextField();
		final JComboBox location = new JComboBox();
		final JTextField orig_return_time = new JTextField();
		final JTextField model = new JTextField();
		final JTextField new_return_date = new JTextField();
		final JTextField new_return_time = new JTextField();
		final JTextField affec_orig_pickup_date = new JTextField();
		final JTextField affec_orig_return_date = new JTextField();
		final JTextField affec_orig_pickup_time = new JTextField();
		final JTextField affec_orig_return_time = new JTextField();

		JButton btnGoBack = new JButton("Update");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (username.getText().isEmpty()
						|| orig_return_time.getText().isEmpty()
						|| orig_return_date.getText().isEmpty()
						|| new_return_date.getText().isEmpty()
						|| new_return_time.getText().isEmpty()
						|| model.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Please search for reservation first...");
					return;
				}

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					if (DataClass.affec_user_flag == 1) {
						// late fee
						String sql = "UPDATE reservation as r SET r.late_fee = r.late_fee + 50 * (timestampdiff(hour, cast(concat(r.return_date,"
								+ " ' ', r.return_time) as datetime), cast(concat(cast(? AS date), ' ', cast(? AS time)) as datetime))), r.late_by "
								+ "= r.late_by + timestampdiff(hour, cast(concat(r.return_date, ' ', r.return_time) as datetime), cast(concat(cast(? AS date),"
								+ " ' ', cast(? AS time)) as datetime)), r.return_date = ?, r.return_time = ? WHERE r.regID = ?;";

						st = c.prepareStatement(sql);
						st.setString(1, new_return_date.getText());
						st.setString(2, new_return_time.getText());
						st.setString(3, new_return_date.getText());
						st.setString(4, new_return_time.getText());
						st.setString(5, new_return_date.getText());
						st.setString(6, new_return_time.getText());
						st.setInt(7, DataClass.username_reg_id);

						if (st.executeUpdate() > 0) {
							JOptionPane
									.showMessageDialog(frame,
											"Return time successfully updated for user's reservation. Late fee charged....");
						} else {
							JOptionPane.showMessageDialog(frame,
									"Return time update Failed...");
							return;
						}
					} else {

						String sql = "UPDATE reservation as r SET r.late_by = r.late_by + timestampdiff(hour, cast(concat(r.return_date,"
								+ " ' ', r.return_time) as datetime), cast(concat(cast(? AS date), ' ', cast(? AS time)) as datetime)), "
								+ "r.return_date = ?, r.return_time = ? WHERE r.regID = ?;";

						st = c.prepareStatement(sql);
						st.setString(1, new_return_date.getText());
						st.setString(2, new_return_time.getText());
						st.setString(3, new_return_date.getText());
						st.setString(4, new_return_time.getText());
						st.setInt(5, DataClass.username_reg_id);

						if (st.executeUpdate() > 0) {
							JOptionPane
									.showMessageDialog(
											frame,
											"Return time successfully updated for user's reservation. No Late fee charged....");
						} else {
							JOptionPane.showMessageDialog(frame,
									"Return time update Failed...");
							return;
						}
					}

					// insert into extended time table

					String sql = "INSERT INTO extended_time (regID, ext_date, ext_time) VALUES (?, convert(?, date),"
							+ " convert(?, time));";

					st = c.prepareStatement(sql);
					st.setInt(1, DataClass.username_reg_id);
					st.setString(2, orig_return_date.getText());
					st.setString(3, orig_return_time.getText());

					st.executeUpdate();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JLabel lblNewLabel = new JLabel("Rent A Car");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		location.setEditable(true);
		location.setModel(new DefaultComboBoxModel(new String[] { "klaus",
				"tsquare", "sCenter", "varsity" }));

		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblPickupTime = new JLabel("Original Return Time");
		lblPickupTime.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblReturnTime = new JLabel("New Arrival Time");
		lblReturnTime.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblCars = new JLabel("Car Model");
		lblCars.setFont(new Font("Times New Roman", Font.BOLD, 13));

		model.setEditable(false);
		model.setToolTipText("");

		JLabel lblEnterUsername = new JLabel("Enter Username");

		username = new JTextField();

		username.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);

		JLabel lblUserAffected = new JLabel("User Affected");
		lblUserAffected.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblUername = new JLabel("Username");
		lblUername.setFont(new Font("Times New Roman", Font.BOLD, 13));

		affec_username = new JTextField();
		affec_username.setEditable(false);
		affec_username.setColumns(10);

		JLabel lblOriginalPickupTime = new JLabel("Original Pickup Time");
		lblOriginalPickupTime
				.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel label_1 = new JLabel("Original Return Time");
		label_1.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.BOLD, 13));

		affec_orig_pickup_date.setEditable(false);

		affec_orig_return_date.setEditable(false);

		affec_email = new JTextField();
		affec_email.setEditable(false);
		affec_email.setColumns(10);

		affec_phone = new JTextField();
		affec_phone.setEditable(false);
		affec_phone.setColumns(10);

		affec_orig_pickup_time.setEditable(false);

		affec_orig_return_time.setEditable(false);

		JButton btnCancelReservation = new JButton("Cancel Reservation");
		btnCancelReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!affec_username.getText().isEmpty()) {
					MyDBConnector db = new MyDBConnector();
					Connection c = db.openConnection();
					PreparedStatement st;
					try {

						String sql = "UPDATE reservation r SET r.cancel_flag = 1 WHERE r.regID = ?;";

						st = c.prepareStatement(sql);
						st.setInt(1, DataClass.affec_username_regID);

						if (st.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(frame,
									"Affected User's reservation cancelled...");
							new AdminHomePage();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame,
									"Submission Failed...");
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frame,
							"No user is affected so cancel not allowed");
				}
			}
		});

		JButton btnShowCarAvailability = new JButton("Show Car Availability");
		btnShowCarAvailability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!affec_username.getText().isEmpty()) {
					DataClass.affec_username = affec_username.getText();
					DataClass.emp_car_search_flag = 1;
					new RentACar();
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame,
							"No user is affected so reshedule not allowed");
				}
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AdminHomePage();
				frame.dispose();
			}
		});

		JButton btnSearchReservation = new JButton("Search Reservation");
		btnSearchReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (username.getText().isEmpty()
						|| orig_return_date.getText().isEmpty()
						|| orig_return_time.getText().isEmpty()
						|| new_return_date.getText().isEmpty()
						|| new_return_time.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									frame,
									"Please provide Username, original return time values, new return time values and then search.");
					return;
				}

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					String sql = "select r.loc_name as loc, c.model as model, r.regID as regID from reservation r, car c where "
							+ "r.mem_username = ? and r.return_date = ? and r.return_time = ? "
							+ "and r.car_no = c.reg_no and cast(NOW() as datetime) between cast(concat(r.pickup_date,' ', r.pickup_time) as datetime) and cast(concat(r.return_date,' ', r.return_time) as datetime);";

					st = c.prepareStatement(sql);
					st.setString(1, username.getText());
					st.setString(2, orig_return_date.getText());
					st.setString(3, orig_return_time.getText());

					ResultSet rs = st.executeQuery();

					if (rs.next()) {
						location.setSelectedItem(rs.getString("loc"));
						model.setText(rs.getString("model"));
						DataClass.username_reg_id = rs.getInt("regID");

						sql = "SELECT r.regID as regID, r.mem_username as name, r.pickup_date as pickup_date, "
								+ "r.pickup_time as pickup_time, r.return_date as return_date, r.return_time as "
								+ "return_time, m.email as email, m.phone as phone FROM reservation r, member m WHERE "
								+ "r.return_flag = 0 AND r.cancel_flag = 0 AND r.mem_username = m.username AND m.username " +
								"<> ? AND cast(concat(cast(? AS date),' ', cast(? AS time)) as datetime) between " +
								"cast(concat(r.pickup_date,' ', r.pickup_time) as datetime) and cast(concat(r.return_date,' '," +
								" r.return_time) as datetime);";

						st = c.prepareStatement(sql);
						st.setString(1, username.getText());
						st.setString(2, new_return_date.getText());
						st.setString(3, new_return_time.getText());

						rs = st.executeQuery();

						if (rs.next()) {
							DataClass.affec_username = rs.getString("name");
							DataClass.affec_user_flag = 1;
							DataClass.affec_username_regID = rs.getInt("regID");
							affec_username.setText(rs.getString("name"));
							affec_orig_pickup_date.setText(rs
									.getString("pickup_date"));
							affec_orig_pickup_time.setText(rs
									.getString("pickup_time"));
							affec_orig_pickup_date.setText(rs
									.getString("return_date"));
							affec_orig_pickup_date.setText(rs
									.getString("pickup_date"));
							affec_email.setText(rs.getString("email"));
							affec_phone.setText(rs.getString("phone"));
						} else {
							DataClass.affec_user_flag = 0;
							DataClass.affec_username_regID = 0;
							DataClass.affec_username = "";
							affec_username.setText("");
							affec_orig_pickup_date.setText("");
							affec_orig_pickup_time.setText("");
							affec_orig_pickup_date.setText("");
							affec_orig_pickup_date.setText("");
							affec_email.setText("");
							affec_phone.setText("");
						}
					} else {
						JOptionPane
								.showMessageDialog(frame,
										"No Reservation with the provided values exists...");
						return;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		/**
		 * All of the remaining code is used to format the GUI.
		 */

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(36)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						btnBack,
																						GroupLayout.PREFERRED_SIZE,
																						80,
																						GroupLayout.PREFERRED_SIZE)
																				.addGroup(
																						gl_panel.createParallelGroup(
																								Alignment.LEADING,
																								false)
																								.addComponent(
																										lblPickupTime,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										lblLocation)
																								.addComponent(
																										lblCars)
																								.addComponent(
																										lblReturnTime,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)))
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.TRAILING)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGap(83)
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.LEADING)
																												.addGroup(
																														gl_panel.createSequentialGroup()
																																.addComponent(
																																		orig_return_date,
																																		GroupLayout.PREFERRED_SIZE,
																																		100,
																																		GroupLayout.PREFERRED_SIZE)
																																.addGap(18)
																																.addComponent(
																																		orig_return_time,
																																		GroupLayout.PREFERRED_SIZE,
																																		76,
																																		GroupLayout.PREFERRED_SIZE))
																												.addComponent(
																														model,
																														194,
																														194,
																														194)
																												.addGroup(
																														gl_panel.createSequentialGroup()
																																.addComponent(
																																		new_return_date,
																																		GroupLayout.PREFERRED_SIZE,
																																		100,
																																		GroupLayout.PREFERRED_SIZE)
																																.addGap(18)
																																.addComponent(
																																		new_return_time,
																																		GroupLayout.PREFERRED_SIZE,
																																		75,
																																		GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		ComponentPlacement.RELATED))
																												.addComponent(
																														location,
																														0,
																														194,
																														Short.MAX_VALUE)))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										btnGoBack)
																								.addGap(1))))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		lblEnterUsername)
																.addGap(18)
																.addComponent(
																		username,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		153,
																		Short.MAX_VALUE)
																.addComponent(
																		btnSearchReservation)
																.addGap(1))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(184)
																.addComponent(
																		lblNewLabel)))
								.addGap(5)
								.addComponent(separator,
										GroupLayout.PREFERRED_SIZE, 11,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.TRAILING)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addComponent(
																										lblEmailAddress,
																										GroupLayout.PREFERRED_SIZE,
																										121,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED,
																										22,
																										Short.MAX_VALUE)
																								.addComponent(
																										affec_email,
																										GroupLayout.PREFERRED_SIZE,
																										130,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.LEADING)
																												.addComponent(
																														lblPhoneNumber,
																														GroupLayout.PREFERRED_SIZE,
																														121,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														btnCancelReservation))
																								.addPreferredGap(
																										ComponentPlacement.UNRELATED)
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.TRAILING)
																												.addComponent(
																														affec_phone,
																														GroupLayout.PREFERRED_SIZE,
																														130,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														btnShowCarAvailability,
																														GroupLayout.DEFAULT_SIZE,
																														138,
																														Short.MAX_VALUE)))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.LEADING)
																												.addComponent(
																														lblOriginalPickupTime,
																														GroupLayout.PREFERRED_SIZE,
																														121,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														lblUername,
																														GroupLayout.PREFERRED_SIZE,
																														77,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														label_1,
																														GroupLayout.PREFERRED_SIZE,
																														121,
																														GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(
																										ComponentPlacement.RELATED,
																										22,
																										Short.MAX_VALUE)
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.LEADING)
																												.addComponent(
																														affec_orig_return_date,
																														GroupLayout.PREFERRED_SIZE,
																														100,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														affec_username,
																														GroupLayout.PREFERRED_SIZE,
																														130,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														affec_orig_pickup_date,
																														GroupLayout.PREFERRED_SIZE,
																														100,
																														GroupLayout.PREFERRED_SIZE))))
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGap(5)
																								.addComponent(
																										affec_orig_pickup_time,
																										GroupLayout.PREFERRED_SIZE,
																										72,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										affec_orig_return_time,
																										GroupLayout.PREFERRED_SIZE,
																										72,
																										GroupLayout.PREFERRED_SIZE)))
																.addGap(20))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(129)
																.addComponent(
																		lblUserAffected)
																.addContainerGap()))));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap(20, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblEnterUsername)
												.addComponent(
														username,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														btnSearchReservation))
								.addGap(26)
								.addComponent(lblNewLabel)
								.addGap(26)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblPickupTime)
												.addComponent(
														orig_return_date,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														orig_return_time,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblReturnTime,
														GroupLayout.PREFERRED_SIZE,
														16,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														new_return_date,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														new_return_time,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblLocation)
												.addComponent(
														location,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblCars)
												.addComponent(
														model,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED,
										16, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(btnGoBack)
												.addComponent(btnBack))
								.addGap(25))
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(17)
								.addComponent(lblUserAffected,
										GroupLayout.PREFERRED_SIZE, 18,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblUername)
												.addComponent(
														affec_username,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblOriginalPickupTime,
														GroupLayout.PREFERRED_SIZE,
														16,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_orig_pickup_date,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_orig_pickup_time,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														label_1,
														GroupLayout.PREFERRED_SIZE,
														16,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_orig_return_date,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_orig_return_time,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblEmailAddress,
														GroupLayout.PREFERRED_SIZE,
														16,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_email,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblPhoneNumber,
														GroupLayout.PREFERRED_SIZE,
														16,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(
														affec_phone,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED,
										34, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														btnCancelReservation)
												.addComponent(
														btnShowCarAvailability))
								.addGap(26))
				.addComponent(separator, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE));
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

		frame.setVisible(true);
	}
}
