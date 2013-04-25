/**
 * This class is used for members to enter/edit 
 * their personal/billing/driving plan information.
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;

/*****************************************************
 * Generates the Personal Information Page
 * 
 * This allows the users to enter their personal information, select their
 * driving plan, and then enter their payment information.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 * 
 *****************************************************/

// TODO populate if update
public class PersonalInformation {

	private JFrame frame;
	public static final int WIDTH = 600;
	public static final int LENGTH = 450;
	public static final String newLine = "\n";
	private JTextField fname;
	private JTextField mname;
	private JTextField lname;
	private JTextField email;
	private JTextField phone;
	private JTextField nameoncard;
	private JTextField cardnumber;
	private JTextField cvv;
	private JTextField expirydate;
	private JTextField billingaddr;
	private JTextField addr;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnDailyPlan, rdbtnFrequentDriving,
			rdbtnDrivingPlan;
	private String plantype;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalInformation window = new PersonalInformation();
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
	public PersonalInformation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 478, 716);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(SystemColor.textHighlight);
		frame.setForeground(Color.BLUE);
		frame.setPreferredSize(new Dimension(WIDTH, LENGTH));
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

		JLabel lblPersonalInformation = new JLabel("Personal Information");
		lblPersonalInformation.setFont(new Font("Times New Roman", Font.BOLD,
				18));

		JLabel lblGeneralInformation = new JLabel("General Information");
		lblGeneralInformation
				.setFont(new Font("Times New Roman", Font.BOLD, 14));

		JLabel lblUsername = new JLabel("First Name");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 13));

		fname = new JTextField();
		fname.setColumns(10);

		JLabel lblMiddleInital = new JLabel("Middle Initial");
		lblMiddleInital.setFont(new Font("Times New Roman", Font.BOLD, 13));

		mname = new JTextField();
		mname.setColumns(10);

		JLabel lblPassword = new JLabel("Last Name");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 13));

		lname = new JTextField();
		lname.setColumns(10);

		JLabel lblEmailAddress = new JLabel("Email Address");
		lblEmailAddress.setFont(new Font("Times New Roman", Font.BOLD, 13));

		email = new JTextField();
		email.setColumns(10);

		JLabel lblPhoneNumbr = new JLabel("Phone Number");
		lblPhoneNumbr.setFont(new Font("Times New Roman", Font.BOLD, 13));

		phone = new JTextField();
		phone.setColumns(10);

		JLabel lblMembershipInformation = new JLabel("Membership Information");
		lblMembershipInformation.setFont(new Font("Times New Roman", Font.BOLD,
				14));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 102));

		JSeparator separator = new JSeparator();

		JSeparator separator_1 = new JSeparator();

		JLabel lblPaymentInformation = new JLabel("Payment Information");
		lblPaymentInformation
				.setFont(new Font("Times New Roman", Font.BOLD, 14));

		JLabel lblNameOnCard = new JLabel("Name on Card");
		lblNameOnCard.setFont(new Font("Times New Roman", Font.BOLD, 13));

		nameoncard = new JTextField();
		nameoncard.setColumns(10);

		JLabel lblMiddleInitial = new JLabel("Card Number");
		lblMiddleInitial.setFont(new Font("Times New Roman", Font.BOLD, 13));

		cardnumber = new JTextField();
		cardnumber.setColumns(10);

		JLabel lblNewLabel = new JLabel("CVV");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblExpiryDate = new JLabel("Expiry Date");
		lblExpiryDate.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblBillingAddress = new JLabel("Billing Address");
		lblBillingAddress.setFont(new Font("Times New Roman", Font.BOLD, 13));

		cvv = new JTextField();
		cvv.setColumns(10);

		expirydate = new JTextField();
		expirydate.setColumns(10);

		billingaddr = new JTextField();
		billingaddr.setColumns(10);

		JButton btnDone = new JButton("Done");

		/**
		 * This action listener updates the database with the information that
		 * the user has entered.
		 */

		btnDone.addActionListener(new ActionListener() {
			@SuppressWarnings("resource")
			public void actionPerformed(ActionEvent arg0) {
				// check if all fields are filled
				if (fname.getText().isEmpty() || lname.getText().isEmpty()
						|| email.getText().isEmpty()
						|| phone.getText().isEmpty()
						|| nameoncard.getText().isEmpty()
						|| addr.getText().isEmpty()
						|| cardnumber.getText().isEmpty()
						|| cvv.getText().isEmpty()
						|| expirydate.getText().isEmpty()
						|| billingaddr.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Please input values for all fields");
					return;
				}

				if (rdbtnDailyPlan.isSelected()) {
					plantype = "Daily";
				}

				else if (rdbtnFrequentDriving.isSelected()) {
					plantype = "Frequent";
				} else if (rdbtnDrivingPlan.isSelected()) {
					plantype = "Occasional";
				} else {
					JOptionPane.showMessageDialog(frame,
							"Please select a driving plan");
					return;
				}

				if (!phone.getText().matches("\\d{10}+")) {
					JOptionPane.showMessageDialog(frame,
							"Phone Number should be 10 digits");
					return;
				}

				if (!cardnumber.getText().matches("\\d{16}+")) {
					JOptionPane.showMessageDialog(frame,
							"Card Number should be 16 digits");
					return;
				}

				if (!cvv.getText().matches("^\\d{7}|\\d{3}")) {
					JOptionPane.showMessageDialog(frame,
							"CVV Number should be only 7 or 3 digits");
					return;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				try {
					Date expiry_date = sdf.parse(expirydate.getText());
					
					Calendar c = Calendar.getInstance();
										
					if(expiry_date.before(c.getTime())){
						JOptionPane.showMessageDialog(frame, 
								"Expired Card cannot be used...");
						return;
					}
					
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(frame, 
							"Date error...");
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}

				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st = null;

				try {

					// check to see if user info exists

					String sql = "SELECT * FROM dbproject.member WHERE username = ?;";

					st = c.prepareStatement(sql);
					st.setString(1, DataClass.username);

					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						// check to see if card info exists
						sql = "SELECT * FROM dbproject.credit_card WHERE card_number = ? and name_on_card = ? and cvv = ? and expiry_date = ? and billing_address = ?;";

						st = c.prepareStatement(sql);
						st.setLong(1, Long.parseLong(cardnumber.getText()));
						st.setString(2, nameoncard.getText());
						st.setInt(3, Integer.parseInt(cvv.getText()));
						st.setString(4, expirydate.getText());
						st.setString(5, billingaddr.getText());

						rs = st.executeQuery();
						if (!rs.next()) {

							sql = "SELECT * FROM dbproject.credit_card WHERE card_number = ?;";

							st = c.prepareStatement(sql);
							st.setLong(1, Long.parseLong(cardnumber.getText()));

							rs = st.executeQuery();
							if (rs.next()) {
								JOptionPane
										.showMessageDialog(frame,
												"Card information conflict. Update failed...");
								return;
							}
							//

							sql = "INSERT INTO dbproject.Credit_card (card_number, name_on_card, cvv, expiry_date, billing_address) VALUES (?, ?, ?, ?, ?);";

							st = c.prepareStatement(sql);
							st.setLong(1, Long.parseLong(cardnumber.getText()));
							st.setString(2, nameoncard.getText());
							st.setInt(3, Integer.parseInt(cvv.getText()));
							st.setString(4, expirydate.getText());
							st.setString(5, billingaddr.getText());

							if (!(st.executeUpdate() > 0)) {
								JOptionPane.showMessageDialog(frame,
										"Update failed...");
								return;
							}

						}

						sql = "UPDATE dbproject.member SET first_name = ?, middle_name = ?, last_name = ?, email = ?, address = ?, phone = ?, plan_type = ?, card_number = ? WHERE username = ?;";
						st = c.prepareStatement(sql);

						st.setString(1, fname.getText());
						st.setString(2, mname.getText());
						st.setString(3, lname.getText());
						st.setString(4, email.getText());
						st.setString(5, addr.getText());
						st.setLong(6, Long.parseLong(phone.getText()));
						st.setString(7, plantype);
						st.setLong(8, Long.parseLong(cardnumber.getText()));
						st.setString(9, DataClass.username);

						if (st.executeUpdate() > 0) {
							JOptionPane
									.showMessageDialog(frame,
											"Personal information successfully updated...");
							new Homepage();
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(frame,
									"Update failed...");
						}

						return;

					}

					else {
						sql = "SELECT * FROM dbproject.credit_card WHERE card_number = ? and name_on_card = ? and cvv = ? and expiry_date = ? and billing_address = ?;";

						st = c.prepareStatement(sql);
						st.setLong(1, Long.parseLong(cardnumber.getText()));
						st.setString(2, nameoncard.getText());
						st.setInt(3, Integer.parseInt(cvv.getText()));
						st.setString(4, expirydate.getText());
						st.setString(5, billingaddr.getText());

						rs = st.executeQuery();
						if (!rs.next()) {

							sql = "SELECT * FROM dbproject.credit_card WHERE card_number = ?;";

							st = c.prepareStatement(sql);
							st.setLong(1, Long.parseLong(cardnumber.getText()));

							rs = st.executeQuery();
							if (rs.next()) {
								JOptionPane
										.showMessageDialog(frame,
												"Card information conflict. Update failed...");
								return;
							}

							sql = "INSERT INTO dbproject.Credit_card (card_number, name_on_card, cvv, expiry_date, billing_address) VALUES (?, ?, ?, ?, ?);";

							st = c.prepareStatement(sql);
							st.setLong(1, Long.parseLong(cardnumber.getText()));
							st.setString(2, nameoncard.getText());
							st.setInt(3, Integer.parseInt(cvv.getText()));
							st.setString(4, expirydate.getText());
							st.setString(5, billingaddr.getText());

							if (!(st.executeUpdate() > 0)) {
								JOptionPane.showMessageDialog(frame,
										"Update failed...");
								return;
							}

						}

						sql = "INSERT INTO dbproject.Member (username, first_name, middle_name, last_name, email, address, phone, card_number, plan_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
						st = c.prepareStatement(sql);
						st.setString(1, DataClass.username);
						st.setString(2, fname.getText());
						st.setString(3, mname.getText());
						st.setString(4, lname.getText());
						st.setString(5, email.getText());
						st.setString(6, addr.getText());
						st.setLong(7, Long.parseLong(phone.getText()));
						st.setLong(8, Long.parseLong(cardnumber.getText()));
						st.setString(9, plantype);

						if (st.executeUpdate() > 0) {
							JOptionPane
									.showMessageDialog(frame,
											"Personal information successfully updated...");
							new Homepage();
							frame.dispose();
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

		/**
		 * This block of code formats the GUI
		 */

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Times New Roman", Font.BOLD, 13));

		addr = new JTextField();
		addr.setColumns(10);
		GroupLayout gl_generalInfoPanel = new GroupLayout(generalInfoPanel);
		gl_generalInfoPanel
				.setHorizontalGroup(gl_generalInfoPanel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(34)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE,
												394, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(34, Short.MAX_VALUE))
						.addGroup(
								Alignment.LEADING,
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(145)
										.addComponent(lblMembershipInformation,
												GroupLayout.PREFERRED_SIZE,
												167, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(150, Short.MAX_VALUE))
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(145)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				lblGeneralInformation))
														.addComponent(
																lblPersonalInformation))
										.addContainerGap(150, Short.MAX_VALUE))
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(34)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel)
														.addGroup(
																gl_generalInfoPanel
																		.createParallelGroup(
																				Alignment.LEADING,
																				false)
																		.addComponent(
																				lblNameOnCard,
																				GroupLayout.DEFAULT_SIZE,
																				94,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblMiddleInitial,
																				GroupLayout.PREFERRED_SIZE,
																				85,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblExpiryDate,
																GroupLayout.PREFERRED_SIZE,
																71,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblBillingAddress,
																GroupLayout.PREFERRED_SIZE,
																130,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addComponent(
																				lblPaymentInformation,
																				GroupLayout.PREFERRED_SIZE,
																				167,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(130))
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								nameoncard,
																								Alignment.LEADING)
																						.addComponent(
																								cardnumber,
																								Alignment.LEADING)
																						.addComponent(
																								cvv,
																								Alignment.LEADING)
																						.addComponent(
																								expirydate,
																								Alignment.LEADING)
																						.addComponent(
																								billingaddr,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								221,
																								Short.MAX_VALUE))
																		.addContainerGap())))
						.addGroup(
								gl_generalInfoPanel.createSequentialGroup()
										.addContainerGap(363, Short.MAX_VALUE)
										.addComponent(btnDone).addGap(42))
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																separator_1,
																GroupLayout.DEFAULT_SIZE,
																462,
																Short.MAX_VALUE)
														.addComponent(
																separator,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																462,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.LEADING,
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGap(31)
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblPhoneNumbr)
																						.addComponent(
																								lblEmailAddress)
																						.addComponent(
																								lblPassword)
																						.addComponent(
																								lblMiddleInital)
																						.addComponent(
																								lblUsername)
																						.addComponent(
																								lblAddress,
																								GroupLayout.PREFERRED_SIZE,
																								81,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(37)
																		.addGroup(
																				gl_generalInfoPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								addr,
																								GroupLayout.PREFERRED_SIZE,
																								167,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								gl_generalInfoPanel
																										.createSequentialGroup()
																										.addGroup(
																												gl_generalInfoPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																fname,
																																GroupLayout.PREFERRED_SIZE,
																																167,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																mname,
																																GroupLayout.PREFERRED_SIZE,
																																167,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lname,
																																GroupLayout.PREFERRED_SIZE,
																																167,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																email,
																																GroupLayout.PREFERRED_SIZE,
																																167,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																phone,
																																GroupLayout.PREFERRED_SIZE,
																																167,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED,
																												146,
																												Short.MAX_VALUE)))))
										.addContainerGap()));
		gl_generalInfoPanel
				.setVerticalGroup(gl_generalInfoPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_generalInfoPanel
										.createSequentialGroup()
										.addGap(6)
										.addComponent(lblPersonalInformation)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(lblGeneralInformation)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblUsername)
														.addComponent(
																fname,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(6)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																mname,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblMiddleInital))
										.addGap(10)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblPassword)
														.addComponent(
																lname,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(6)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																lblEmailAddress)
														.addComponent(
																email,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_generalInfoPanel
																		.createSequentialGroup()
																		.addGap(2)
																		.addComponent(
																				lblPhoneNumbr))
														.addComponent(
																phone,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																lblAddress,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																addr,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(13)
										.addComponent(separator,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(lblMembershipInformation)
										.addGap(18)
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE,
												109, GroupLayout.PREFERRED_SIZE)
										.addGap(26)
										.addComponent(separator_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(lblPaymentInformation,
												GroupLayout.PREFERRED_SIZE, 17,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNameOnCard,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																nameoncard,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblMiddleInitial,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cardnumber,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNewLabel)
														.addComponent(
																cvv,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblExpiryDate,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																expirydate,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_generalInfoPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblBillingAddress,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																billingaddr,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 19,
												Short.MAX_VALUE)
										.addComponent(btnDone).addGap(19)));

		rdbtnDailyPlan = new JRadioButton("Daily Plan");
		buttonGroup.add(rdbtnDailyPlan);
		rdbtnDailyPlan.setBackground(new Color(255, 204, 102));

		rdbtnFrequentDriving = new JRadioButton("Frequent Driving");
		buttonGroup.add(rdbtnFrequentDriving);
		rdbtnFrequentDriving.setBackground(new Color(255, 204, 102));

		rdbtnDrivingPlan = new JRadioButton("Occasional Driving");
		buttonGroup.add(rdbtnDrivingPlan);
		rdbtnDrivingPlan.setBackground(new Color(255, 204, 102));

		JLabel lblChooseAPlan = new JLabel("Choose a Plan");
		lblChooseAPlan.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseAPlan.setFont(new Font("Times New Roman", Font.BOLD, 15));

		JLabel lblViewPlanType = new JLabel("View Plan Type Descriptions");
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
																.addContainerGap()
																.addComponent(
																		rdbtnDrivingPlan)
																.addGap(28)
																.addComponent(
																		rdbtnFrequentDriving)
																.addGap(31)
																.addComponent(
																		rdbtnDailyPlan,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(130)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						lblViewPlanType)
																				.addComponent(
																						lblChooseAPlan,
																						GroupLayout.PREFERRED_SIZE,
																						126,
																						GroupLayout.PREFERRED_SIZE))))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblChooseAPlan,
										GroupLayout.PREFERRED_SIZE, 27,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblViewPlanType)
								.addGap(10)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(rdbtnDrivingPlan)
												.addComponent(
														rdbtnFrequentDriving)
												.addComponent(rdbtnDailyPlan))
								.addContainerGap(94, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		generalInfoPanel.setLayout(gl_generalInfoPanel);

		MyDBConnector db = new MyDBConnector();
		Connection c = db.openConnection();
		PreparedStatement st = null;

		try {

			// check to see if user info exists

			String sql = "SELECT * FROM member as m, credit_card as c WHERE m.username = ? and c.card_number = m.card_number;";

			st = c.prepareStatement(sql);
			st.setString(1, DataClass.username);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				fname.setText(rs.getString("first_name"));
				mname.setText(rs.getString("middle_name"));
				lname.setText(rs.getString("last_name"));
				email.setText(rs.getString("email"));
				phone.setText(rs.getString("phone"));
				addr.setText(rs.getString("address"));
				nameoncard.setText(rs.getString("name_on_card"));
				cvv.setText(rs.getString("cvv"));
				expirydate.setText(rs.getString("expiry_date"));
				billingaddr.setText(rs.getString("billing_address"));
				cardnumber.setText(rs.getString("card_number"));
				String plan_type = rs.getString("plan_type");

				if (plan_type.equals("Occasional")) {
					rdbtnDrivingPlan.setSelected(true);
				} else if (plan_type.equals("Daily")) {
					rdbtnDailyPlan.setSelected(true);
				} else if (plan_type.equals("Frequent")) {
					rdbtnFrequentDriving.setSelected(true);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
