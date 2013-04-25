/**
 * This class provides the login screen and 
 * is the main driver for the application.
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**********************************************************
 * Generates the Login Page for the User and Employees
 * 
 * This allows the users and the employees to enter login information to access
 * the car rental system and select from their user type: Member, Administrator,
 * & Employee.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 * 
 ***********************************************************/
public class Login2 {

	private JFrame frame;
	public static final int WIDTH = 600;
	public static final int LENGTH = 450;
	public static final String newLine = "\n";
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login2 window = new Login2();
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
	public Login2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This large block of code deals
	 * mostly with creating the GUI.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
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
		title.setBackground(new Color(255, 255, 102));
		title.setFont(new Font("Georgia", Font.BOLD, 18));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(0, 0, 0));
		panel.add(title, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel_1.add(lblUsername, "12, 6, left, default");

		username = new JTextField();
		panel_1.add(username, "14, 6, fill, default");
		username.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel_1.add(lblPassword, "12, 8, left, default");

		password = new JPasswordField();
		panel_1.add(password, "14, 8");

		JButton btnNewButton = new JButton("Login");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(UIManager.getColor("Button.background"));
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			/**
			 * The following query is executed once the user hits the
			 * Enter button. The database is queried to determine if the 
			 * user is in the system and, if so, they are directed to either
			 * the members home page or the admin/employee homepage depending
			 * on member type.
			 */
			public void actionPerformed(ActionEvent arg0) {
				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st;
				try {

					String sql = "SELECT * FROM dbproject.login WHERE username = ? and user_passwd = ? ;";

					st = c.prepareStatement(sql);

					st.setString(1, username.getText());
					st.setString(2, password.getText());

					ResultSet rs = st.executeQuery();

					if (rs.next()) {

						DataClass.username = rs.getString("username");
						DataClass.userType = rs.getString("user_type");

						if (DataClass.userType.equals("member")) {
							sql = "SELECT * FROM dbproject.member WHERE username = ?;";

							st = c.prepareStatement(sql);

							st.setString(1, username.getText());

							rs = st.executeQuery();

							if (rs.next()) {
								new Homepage();
							} else {
								new PersonalInformation();
							}

						} else {
							new AdminHomePage();
						}

						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame,
								"Record not found...");

					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});
		panel_1.add(btnNewButton, "12, 12, 3, 1");

		JButton label = new JButton("Register");
		label.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CreateAccount();
				frame.dispose();
			}
		});
		label.setBackground(UIManager.getColor("Button.background"));
		label.setForeground(Color.BLACK);
		panel_1.add(label, "12, 14, right, default");

		JButton label_1 = new JButton("About");
		label_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								frame,
								"This application is brought to you by Siddarth Choudhary,\n"
										+ "Mark Owens, and Shreyyas Vanarase. SMS Incorporated has\n"
										+ "generated a fundamental application providing\n"
										+ "Georgia Tech students a rental car service. We hope you enjoy\n"
										+ "this application.",
								"Georgia Tech Car Rental Service - SMS",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		label_1.setBackground(UIManager.getColor("Button.background"));
		label_1.setForeground(Color.BLACK);
		panel_1.add(label_1, "14, 14, fill, default");

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
