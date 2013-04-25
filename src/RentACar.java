/**
 * This class allows the user to select
 * the type of car, date, time and location 
 * of a rental request he would like to make.
 */


import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
/*****************************************************
 * Generates the Location Preference Report
 * 
 * This allows the users to actually make the
 * reservation for a car. The user needs to specify a 
 * pick up time, a return time, location and car type.
 * 
 * Any car cannot be reserved for more than 2 days.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/
public class RentACar {

	private JFrame frame;
	private JTextField returnDate;

	private JTextField returnTime;

	private JTextField pickupDate;

	private JTextField pickupTime;
	@SuppressWarnings("rawtypes")
	private JComboBox carLoc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentACar window = new RentACar();
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
	public RentACar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 525, 393);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		returnDate = new JTextField();
		returnTime = new JTextField();
		pickupDate = new JTextField();
		pickupTime = new JTextField();

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		final JComboBox choice = new JComboBox();
		final JComboBox selector = new JComboBox();
		selector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selector.getSelectedItem().toString().equals("Choose By Model")){
					choice.setModel(new DefaultComboBoxModel(new String[] { "Toyota",
					"Honda", "Hyundai", "Lexus", "Saturn", "Kia", "Acura",
					"Mercedez-Benz", "Porsche", "Lamborghini" }));
				}
				else{
					choice.setModel(new DefaultComboBoxModel(new String[] { "SUV",
							"Coupe", "Sedan", "Truck", "Mini-SUV" }));
				}
				
			}
		});
		JButton Search = new JButton("Search");
		
		/**
		 * This action listener queries the database
		 * to see what cars are available for the given
		 * date and time and creates an instance of the 
		 * CarAvailability class to display the results.
		 */
		
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				try {
					Date pickup_date = sdf.parse(pickupDate.getText() + " "
							+ pickupTime.getText());
					
					Date return_date = sdf.parse(returnDate.getText() + " "
						+ returnTime.getText());
					
					Calendar c = Calendar.getInstance();
					Date tmp_date = sdf.parse(sdf.format(new Date()));
					
					c.setTime(pickup_date);
					c.add(Calendar.DATE, 2);
					
					if(!pickup_date.before(return_date)){
						JOptionPane.showMessageDialog(frame, 
								"Please input correct pickup date and return date...");
						return;
					}else if(c.getTime().before(return_date)){
						JOptionPane.showMessageDialog(frame, 
								"Reservation cannot be made for more than two days at a time...");
						return;						
					}else if(tmp_date.after(pickup_date))
					{
						JOptionPane.showMessageDialog(frame, 
								"Past reservations cannot be made...");
						return;
					}
					
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(frame, 
							"Date error...");
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				
				DataClass.pickupTimestamp = pickupDate.getText() + " "
						+ pickupTime.getText();
				DataClass.returnTimestamp = returnDate.getText() + " "
						+ returnTime.getText();
				DataClass.carLocation = (String) carLoc.getSelectedItem();
				if(selector.getSelectedItem().toString().equals("Choose By Model")){
					DataClass.carModel = choice.getSelectedItem().toString();
					DataClass.carType = "";					
				}else{
					DataClass.carType = choice.getSelectedItem().toString();
					DataClass.carModel = "";
				}
				new CarAvailability();
				frame.dispose();
			}
		});

		JLabel lblNewLabel = new JLabel("Rent A Car");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));

		carLoc = new JComboBox();
		carLoc.setModel(new DefaultComboBoxModel(new String[] {"klaus", "tsquare", "varsity", "sCenter"}));

		JLabel lblLocation = new JLabel("Location");
		lblLocation.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblPickupTime = new JLabel("Pickup Time (YYYY/MM/DD)");
		lblPickupTime.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblReturnTime = new JLabel("Return Time (YYYY/MM/DD)");
		lblReturnTime.setFont(new Font("Times New Roman", Font.BOLD, 13));

		JLabel lblCars = new JLabel("Cars");
		lblCars.setFont(new Font("Times New Roman", Font.BOLD, 13));

		selector.setModel(new DefaultComboBoxModel(new String[] { "Choose By Type",
				"Choose By Model"}));
		
		choice.setModel(new DefaultComboBoxModel(new String[] { "SUV",
				"Coupe", "Sedan", "Truck", "Mini-SUV" }));
		
		//Back button does exactly what one would expect.
		JButton btnB = new JButton("Back");
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(DataClass.emp_car_search_flag == 1)
				{
					new RentalChangeRequest();
					frame.dispose();	
				}
				else{
					new Homepage();
					frame.dispose();
				}
				
			}
		});
		
		/**
		 * The rest of the code formats the GUI
		 */
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(165)
							.addComponent(lblNewLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(36)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLocation)
								.addComponent(lblPickupTime)
								.addComponent(lblReturnTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(2)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(btnB, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblCars))
									.addGap(79)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
									.addComponent(Search))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(83)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(carLoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(returnDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(returnTime, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(pickupDate, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(pickupTime, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(selector, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(choice, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))))))
					.addGap(51))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPickupTime)
						.addComponent(pickupDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickupTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReturnTime, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(returnDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(returnTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(carLoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLocation))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCars)
						.addComponent(selector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(choice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(Search)
						.addComponent(btnB))
					.addGap(68))
		);
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
