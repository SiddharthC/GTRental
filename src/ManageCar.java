/**
 * This class allows employees to add new cars 
 * or change the location of an existing car.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
/*****************************************************
 * Generates the Manage Cars Page
 * 
 * This allows the employees to add a new car or change
 * the car locations. All fields are required to properly
 * process this page, including the Brief Description 
 * section. 
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class ManageCar {

	private JFrame frame;
	private JTextField cregNo;
	@SuppressWarnings("rawtypes")
	private JComboBox cmodel;
	private JTextField chrate;
	private JTextField cdrate;
	private JTextField ccapacity;
	@SuppressWarnings("rawtypes")
	private JComboBox ctype;
	private JTextField type;
	private JTextField color;
	private JTextField capacity;
	private JTextField trans;
	private JTextField ccolor;
	private String car_numbers[];
	@SuppressWarnings("rawtypes")
	private JComboBox car_list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageCar window = new ManageCar();
					// window.frame.pack();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the constructor
	 */
	public ManageCar() {
		initialize();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 681, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(SystemColor.textHighlight);
		frame.setForeground(Color.BLUE);
		frame.setPreferredSize(new Dimension(700, 700));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

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

		JPanel generalInfoPanel = new JPanel();
		generalInfoPanel.setAutoscrolls(true);
		generalInfoPanel.setBackground(new Color(255, 255, 102));
		generalInfoPanel.setSize(new Dimension(300, 160));
		frame.getContentPane().add(generalInfoPanel, BorderLayout.CENTER);

		JLabel lblAddCar = new JLabel("Add Car");
		lblAddCar.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblChangeCarLocation = new JLabel("Change Car Location");
		lblChangeCarLocation
				.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblVehicleSno = new JLabel("Vehicle Sno");
		lblVehicleSno.setFont(new Font("Times New Roman", Font.BOLD, 13));

		cregNo = new JTextField();
		cregNo.setColumns(10);

		JLabel lblNewLabel = new JLabel("Car Model");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));

		cmodel = new JComboBox();
		cmodel.setModel(new DefaultComboBoxModel(new String[] { "Toyota",
				"Honda", "Hyundai", "Lexus", "Saturn", "Kia", "Acura",
				"Mercedez-Benz", "Porsche", "Lamborghini" }));

		JLabel lblCarType = new JLabel("Car Type");
		lblCarType.setFont(new Font("Times New Roman", Font.BOLD, 13));

		final JComboBox clocation = new JComboBox();
		clocation.setModel(new DefaultComboBoxModel(new String[] {"klaus", "tsquare", "sCenter", "varsity"}));

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);

		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblHourlyRate = new JLabel("Hourly Rate");
		lblHourlyRate.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblDailyRate = new JLabel("Daily Rate");
		lblDailyRate.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblSeatingCapacity = new JLabel("Seating Capacity");
		lblSeatingCapacity.setFont(new Font("Times New Roman", Font.BOLD, 13));

		chrate = new JTextField();
		chrate.setColumns(10);

		ccolor = new JTextField();

		cdrate = new JTextField();
		cdrate.setColumns(10);

		ccapacity = new JTextField();
		ccapacity.setColumns(10);

		ctype = new JComboBox();
		ctype.setModel(new DefaultComboBoxModel(new String[] { "SUV", "Coupe",
				"Sedan", "Truck", "Mini-SUV" }));

		JLabel lblTransmissionType = new JLabel("Transmission Type");
		lblTransmissionType.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblBluetoothConnectivity = new JLabel("Bluetooth Connectivity");
		lblBluetoothConnectivity.setFont(new Font("Times New Roman", Font.BOLD,
				13));

		JLabel lblAuxillaryCable = new JLabel("Auxillary Cable");
		lblAuxillaryCable.setFont(new Font("Times New Roman", Font.BOLD, 13));

		final JComboBox ctrans = new JComboBox();
		ctrans.setModel(new DefaultComboBoxModel(new String[] { "Automatic",
				"Manual" }));

		final JComboBox cbtooth = new JComboBox();
		cbtooth.setModel(new DefaultComboBoxModel(new String[] { "Yes", "No" }));

		final JComboBox cauxcable = new JComboBox();
		cauxcable.setModel(new DefaultComboBoxModel(
				new String[] { "Yes", "No" }));

		JLabel lblChooseCurrentLocation = new JLabel("Choose Current Location");
		lblChooseCurrentLocation.setFont(new Font("Times New Roman", Font.BOLD,
				13));

		final JComboBox location = new JComboBox();
		location.setModel(new DefaultComboBoxModel(new String[] {"klaus", "tsquare", "sCenter", "varsity"}));

		/**
		 * This action listener populates the list of cars
		 * at the location chosen.
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

					int i = 0;
					while (rs.next()) {
						car_numbers[i] = rs.getString("reg_no");
						i++;
					}

					car_list.setModel(new DefaultComboBoxModel(car_numbers));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JLabel lblChooseCar = new JLabel("Choose Car");
		lblChooseCar.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 102));

		car_list = new JComboBox();
		
		/**
		 * The following query populates the fields with the 
		 * chosen car's description.
		 */
		
		car_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					String sql = "select car_type, color, capacity, transm_type from car where reg_no = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, (String) car_list.getSelectedItem());

					ResultSet rs = st.executeQuery();

					while (rs.next()) {
						type.setText(rs.getString("car_type"));
						color.setText(rs.getString("color"));
						capacity.setText(Integer.toString(rs.getInt("capacity")));
						trans.setText(rs.getString("transm_type"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		JLabel lblChooseNewLocation = new JLabel("Choose New Location");
		lblChooseNewLocation
				.setFont(new Font("Times New Roman", Font.BOLD, 13));

		final JComboBox new_location = new JComboBox();
		new_location.setModel(new DefaultComboBoxModel(new String[] {"tsquare", "klaus", "sCenter", "varsity"}));

		/**
		 * This action listener attempts to add a new car
		 * to the database once the user presses the 'Add' button.
		 */

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (cregNo.getText().isEmpty()
						|| ccolor.getText().isEmpty()
						|| chrate.getText().isEmpty()
						|| cdrate.getText().isEmpty()
						|| ccapacity.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Please input values for all fields");
					return;
				}

				if (!chrate.getText().matches("[0-9]*\\.?[0-9]+")) {
					JOptionPane.showMessageDialog(frame,
							"Hourly rate should be a float");
					return;
				}

				if (!cdrate.getText().matches("[0-9]*\\.?[0-9]+")) {
					JOptionPane.showMessageDialog(frame,
							"Dailly rate should be a float");
					return;
				}

				if (!ccapacity.getText().matches("\\d+")) {
					JOptionPane.showMessageDialog(frame,
							"capacity can be only integer");
					return;
				}

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st = null;

				try {

					// check to see if user info exists

					String sql = "SELECT * FROM dbproject.car WHERE reg_no = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, cregNo.getText());

					ResultSet rs = st.executeQuery();

					if (rs.next()) {
						JOptionPane
								.showMessageDialog(frame,
										"Registration number already exists!!! Failed...");
						return;
					}
					
					int current_car_no = 0, actual_capacity = 0;

					sql = "select count(*) as current_no from car where loc_name = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, clocation.getSelectedItem().toString());

					rs = st.executeQuery();

					if (rs.next()) {
						current_car_no = rs.getInt("current_no");
					}

					sql = "select capacity from location where loc_name = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, clocation.getSelectedItem().toString());

					rs = st.executeQuery();

					if (rs.next()) {
						actual_capacity = rs.getInt("capacity");
					}

					if (current_car_no >= actual_capacity) {
						JOptionPane.showMessageDialog(frame,
								"Update failed. Location full...");
						return;
						
					}

					sql = "INSERT INTO Car (reg_no, aux_cable, transm_type, capacity, b_tooth, daily_rate,"
							+ " hourly_rate, color, car_type, model, loc_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					st = c.prepareStatement(sql);
					st.setString(1, cregNo.getText());
					st.setString(2, cauxcable.getSelectedItem().toString());
					st.setString(3, (String) ctrans.getSelectedItem());
					st.setInt(4, Integer.parseInt(ccapacity.getText()));
					st.setString(5, cbtooth.getSelectedItem().toString());
					st.setFloat(6, Float.parseFloat(cdrate.getText()));
					st.setFloat(7, Float.parseFloat(chrate.getText()));
					st.setString(8, ccolor.getText());
					st.setString(9, (String) ctype.getSelectedItem());
					st.setString(10, cmodel.getSelectedItem().toString());
					st.setString(11, (String) clocation.getSelectedItem());

					if (st.executeUpdate() > 0) {
						JOptionPane.showMessageDialog(frame,
								"Car information added successfully...");
						return;
					} else {
						JOptionPane.showMessageDialog(frame, "Failed...");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});

		/**
		 * Any location changes the user has made
		 * are finalized by pressing the 'Submit Changes' button.
		 */
		
		JButton btnSubmitChanges = new JButton("Submit Changes");
		btnSubmitChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					int current_car_no = 0, actual_capacity = 0;

					String sql = "select count(*) as current_no from car where loc_name = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, new_location.getSelectedItem().toString());

					ResultSet rs = st.executeQuery();

					if (rs.next()) {
						current_car_no = rs.getInt("current_no");
					}

					sql = "select capacity from location where loc_name = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, new_location.getSelectedItem().toString());

					rs = st.executeQuery();

					if (rs.next()) {
						actual_capacity = rs.getInt("capacity");
					}

					if (current_car_no < actual_capacity) {
						sql = "UPDATE car SET loc_name = ? WHERE reg_no = ?;";

						st = c.prepareStatement(sql);
						st.setString(1, (String) new_location.getSelectedItem());
						st.setString(2, (String) car_list.getSelectedItem());

						if (st.executeUpdate() > 0) {
							JOptionPane.showMessageDialog(frame,
									"Location updated successfully...");
						} else {
							JOptionPane.showMessageDialog(frame,
									"Update failed...");
						}
					} else {
						JOptionPane.showMessageDialog(frame,
								"Update failed. Location full...");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		/**
		 * The rest of the Code is used to format the GUI.
		 */

		JLabel lblManageCar = new JLabel("Manage Car");
		lblManageCar.setFont(new Font("Times New Roman", Font.BOLD, 18));

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AdminHomePage();
				frame.dispose();
			}
		});
		GroupLayout gl_generalInfoPanel = new GroupLayout(generalInfoPanel);
		gl_generalInfoPanel
				.setHorizontalGroup(gl_generalInfoPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(27)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblSeatingCapacity)
														.addComponent(
																lblColor,
																GroupLayout.PREFERRED_SIZE,
																44,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblTransmissionType)
														.addComponent(
																lblBluetoothConnectivity)
														.addComponent(
																lblAuxillaryCable,
																GroupLayout.PREFERRED_SIZE,
																90,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_generalInfoPanel
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblVehicleSno)
																																		.addGap(22))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblNewLabel,
																																				GroupLayout.DEFAULT_SIZE,
																																				82,
																																				Short.MAX_VALUE)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED))
																														.addComponent(
																																lblLocation,
																																GroupLayout.DEFAULT_SIZE,
																																88,
																																Short.MAX_VALUE))
																										.addGroup(
																												gl_generalInfoPanel
																														.createSequentialGroup()
																														.addComponent(
																																lblCarType)
																														.addPreferredGap(
																																ComponentPlacement.RELATED)))
																						.addGroup(
																								gl_generalInfoPanel
																										.createParallelGroup(
																												Alignment.TRAILING,
																												false)
																										.addComponent(
																												lblDailyRate,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												lblHourlyRate,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												70,
																												Short.MAX_VALUE)))
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addComponent(
																												lblAddCar)
																										.addGap(129))
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.TRAILING,
																																false)
																														.addComponent(
																																cmodel)
																														.addComponent(
																																cregNo,
																																GroupLayout.DEFAULT_SIZE,
																																145,
																																Short.MAX_VALUE)
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				ctype,
																																				GroupLayout.PREFERRED_SIZE,
																																				145,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				clocation,
																																				0,
																																				GroupLayout.DEFAULT_SIZE,
																																				Short.MAX_VALUE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				ccolor,
																																				GroupLayout.PREFERRED_SIZE,
																																				145,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				chrate,
																																				GroupLayout.PREFERRED_SIZE,
																																				145,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				cdrate,
																																				GroupLayout.PREFERRED_SIZE,
																																				145,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				ccapacity,
																																				GroupLayout.PREFERRED_SIZE,
																																				145,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addGroup(
																																				gl_generalInfoPanel
																																						.createParallelGroup(
																																								Alignment.LEADING,
																																								false)
																																						.addComponent(
																																								cauxcable,
																																								Alignment.TRAILING,
																																								0,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								ctrans,
																																								Alignment.TRAILING,
																																								0,
																																								GroupLayout.DEFAULT_SIZE,
																																								Short.MAX_VALUE)
																																						.addComponent(
																																								btnAdd,
																																								Alignment.TRAILING)
																																						.addComponent(
																																								cbtooth,
																																								Alignment.TRAILING,
																																								GroupLayout.PREFERRED_SIZE,
																																								99,
																																								GroupLayout.PREFERRED_SIZE))))
																										.addGap(18)))))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE, 2,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGap(18)
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGap(10)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addGroup(
																																				gl_generalInfoPanel
																																						.createParallelGroup(
																																								Alignment.LEADING)
																																						.addComponent(
																																								lblChooseCar,
																																								GroupLayout.PREFERRED_SIZE,
																																								135,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								lblChooseCurrentLocation,
																																								GroupLayout.PREFERRED_SIZE,
																																								160,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addPreferredGap(
																																				ComponentPlacement.UNRELATED)
																																		.addGroup(
																																				gl_generalInfoPanel
																																						.createParallelGroup(
																																								Alignment.LEADING)
																																						.addComponent(
																																								car_list,
																																								GroupLayout.PREFERRED_SIZE,
																																								114,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								location,
																																								GroupLayout.PREFERRED_SIZE,
																																								114,
																																								GroupLayout.PREFERRED_SIZE))
																																		.addGap(18))
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				lblChooseNewLocation,
																																				GroupLayout.PREFERRED_SIZE,
																																				165,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addGap(18)
																																		.addComponent(
																																				new_location,
																																				0,
																																				133,
																																				Short.MAX_VALUE))
																														.addComponent(
																																btnSubmitChanges,
																																Alignment.TRAILING))
																										.addContainerGap(
																												20,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addComponent(
																												panel,
																												GroupLayout.PREFERRED_SIZE,
																												322,
																												GroupLayout.PREFERRED_SIZE)
																										.addContainerGap())))
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				lblChangeCarLocation,
																				GroupLayout.PREFERRED_SIZE,
																				152,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(105))))
						.addGroup(
								gl_generalInfoPanel.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnBack).addGap(169)
										.addComponent(lblManageCar)
										.addContainerGap(301, Short.MAX_VALUE)));
		gl_generalInfoPanel
				.setVerticalGroup(gl_generalInfoPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(btnBack)
														.addComponent(
																lblManageCar))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGap(20)
																										.addComponent(
																												lblAddCar))
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGap(17)
																										.addComponent(
																												lblChangeCarLocation,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(23)
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																lblVehicleSno)
																														.addGroup(
																																gl_generalInfoPanel
																																		.createSequentialGroup()
																																		.addComponent(
																																				cregNo,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addGap(18)
																																		.addGroup(
																																				gl_generalInfoPanel
																																						.createParallelGroup(
																																								Alignment.BASELINE)
																																						.addComponent(
																																								cmodel,
																																								GroupLayout.PREFERRED_SIZE,
																																								GroupLayout.DEFAULT_SIZE,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addComponent(
																																								lblNewLabel))))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																ctype,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblCarType))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																clocation,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblLocation))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblColor)
																														.addComponent(
																																ccolor,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblHourlyRate)
																														.addComponent(
																																chrate,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblDailyRate)
																														.addComponent(
																																cdrate,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblSeatingCapacity)
																														.addComponent(
																																ccapacity,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblTransmissionType)
																														.addComponent(
																																ctrans,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblBluetoothConnectivity)
																														.addComponent(
																																cbtooth,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblAuxillaryCable)
																														.addComponent(
																																cauxcable,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblChooseCurrentLocation)
																														.addComponent(
																																location,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(18)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																lblChooseCar)
																														.addComponent(
																																car_list,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addGap(33)
																										.addComponent(
																												panel,
																												GroupLayout.PREFERRED_SIZE,
																												201,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(42)
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																new_location,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblChooseNewLocation))))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				30,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								btnAdd,
																								Alignment.TRAILING)
																						.addComponent(
																								btnSubmitChanges,
																								Alignment.TRAILING))
																		.addGap(22))
														.addComponent(
																separator,
																GroupLayout.DEFAULT_SIZE,
																536,
																Short.MAX_VALUE))));

		JLabel lblBriefDescription = new JLabel("Brief Description");
		lblBriefDescription.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblCarType_1 = new JLabel("Car Type");
		lblCarType_1.setFont(new Font("Times New Roman", Font.BOLD, 13));

		type = new JTextField();
		type.setEditable(false);
		type.setColumns(10);

		JLabel lblColor_1 = new JLabel("Color");
		lblColor_1.setFont(new Font("Times New Roman", Font.BOLD, 13));

		color = new JTextField();
		color.setEditable(false);
		color.setColumns(10);

		JLabel lblSeatingCapacity_1 = new JLabel("Seating Capacity");
		lblSeatingCapacity_1
				.setFont(new Font("Times New Roman", Font.BOLD, 13));

		capacity = new JTextField();
		capacity.setEditable(false);
		capacity.setColumns(10);

		JLabel lblTransmissionType_1 = new JLabel("Transmission Type");
		lblTransmissionType_1
				.setFont(new Font("Times New Roman", Font.BOLD, 13));

		trans = new JTextField();
		trans.setEditable(false);
		trans.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														lblSeatingCapacity_1,
														GroupLayout.PREFERRED_SIZE,
														101,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(
														Alignment.TRAILING,
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.TRAILING)
																				.addComponent(
																						lblTransmissionType_1,
																						Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						152,
																						Short.MAX_VALUE)
																				.addGroup(
																						gl_panel.createSequentialGroup()
																								.addGroup(
																										gl_panel.createParallelGroup(
																												Alignment.LEADING)
																												.addComponent(
																														lblCarType_1)
																												.addComponent(
																														lblColor_1,
																														GroupLayout.PREFERRED_SIZE,
																														44,
																														GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(
																										ComponentPlacement.RELATED,
																										GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										lblBriefDescription)))
																.addGap(40)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														trans,
														GroupLayout.DEFAULT_SIZE,
														93, Short.MAX_VALUE)
												.addComponent(
														capacity,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														93, Short.MAX_VALUE)
												.addComponent(
														color,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														93, Short.MAX_VALUE)
												.addComponent(
														type,
														Alignment.TRAILING,
														GroupLayout.PREFERRED_SIZE,
														108,
														GroupLayout.PREFERRED_SIZE))
								.addGap(41)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblBriefDescription)
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblCarType_1)
												.addComponent(
														type,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(lblColor_1)
												.addComponent(
														color,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblSeatingCapacity_1)
												.addComponent(
														capacity,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														lblTransmissionType_1)
												.addComponent(
														trans,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addContainerGap(22, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		generalInfoPanel.setLayout(gl_generalInfoPanel);

		JLabel lblPersonalInformation = new JLabel("Personal Information");
		lblPersonalInformation.setFont(new Font("Times New Roman", Font.BOLD,
				18));

		JLabel lblGeneralInformation = new JLabel("General Information");
		lblGeneralInformation
				.setFont(new Font("Times New Roman", Font.BOLD, 14));

		JLabel lblUsername = new JLabel("First Name");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 13));

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
