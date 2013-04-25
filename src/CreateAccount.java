/**
 * The page for the user to enter their personal information if
 * they are a new user, or to review/change their current information.
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
/*****************************************************
 * Generates the Create Account Page. 
 * 
 * A Create Account Page initializes when the Register
 * Button is clicked in Login. Goes back to the Login 
 * Page once registration is done.
 * 
 * @author ShreyyasV, Mark Owens, Siddharth Choudhary
 *
 *****************************************************/

public class CreateAccount {

	private JFrame frame;
	private JTextField username;
	@SuppressWarnings("rawtypes")
	private JComboBox usertype;
	private JPasswordField password;
	private JPasswordField password2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount window = new CreateAccount();
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
	public CreateAccount() {
		
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 331);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel createAccount = new JLabel("Create Account");
		createAccount.setFont(new Font("Georgia", Font.BOLD, 18));
		createAccount.setBackground(Color.YELLOW);
		panel.add(createAccount);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 102));
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		FormLayout fl_panel_1 = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("117px"),
				ColumnSpec.decode("109px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),});
		panel_1.setLayout(fl_panel_1);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblUsername.setForeground(new Color(0, 0, 0));
		panel_1.add(lblUsername, "1, 4, center, center");

		username = new JTextField();
		panel_1.add(username, "2, 4, fill, default");
		username.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_1.add(lblPassword, "1, 6, center, center");
		
		password = new JPasswordField();
		panel_1.add(password, "2, 6, fill, default");
		
		JLabel lblNewLabel = new JLabel("Confirm Password:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_1.add(lblNewLabel, "1, 8, center, center");
		
		password2 = new JPasswordField();
		panel_1.add(password2, "2, 8, fill, default");
		
		JLabel lblNewLabel_1 = new JLabel("Type of User:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_1.add(lblNewLabel_1, "1, 10, center, center");
		
		usertype = new JComboBox();
		usertype.setModel(new DefaultComboBoxModel(new String[] {"member", "admin", "employee"}));
		panel_1.add(usertype, "2, 10, fill, default");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 102));
		panel_1.add(panel_2, "2, 12, fill, fill");
		
		JButton btnRegister = new JButton("Register!");
		btnRegister.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				if(password.getText().isEmpty()|| password2.getText().isEmpty()|| username.getText().isEmpty()){
					JOptionPane.showMessageDialog(frame, "Please input values for all fields");
					return;
				}
				
				if(!(password.getText()).equals(password2.getText())){
					JOptionPane.showMessageDialog(frame, "Passwords did not match!!!");
					return;
				}
				
				MyDBConnector db = new MyDBConnector();
				Connection c = db.openConnection();
				PreparedStatement st = null;
				try {
					String sql = "SELECT * FROM dbproject.login WHERE username = ?;";
					
					st = c.prepareStatement(sql);
					st.setString(1, username.getText());

					ResultSet rs = st.executeQuery();
					if(rs.next()){
						JOptionPane.showMessageDialog(frame, "Cannot create User. User already exists...");
						return;
					}

					sql = "INSERT INTO dbproject.login (username, user_passwd, user_type) VALUES (?, ?, ?);";

					st = c.prepareStatement(sql);
					st.setString(1, username.getText());
					st.setString(2, password.getText());
					st.setString(3, (String)usertype.getSelectedItem());
							
					if(st.executeUpdate() > 0){
						
						if(!usertype.getSelectedItem().toString().equals("member"))
						{
							sql = "INSERT INTO dbproject.admin_employee (username, user_type) VALUES (?, ?);";

							st = c.prepareStatement(sql);
							st.setString(1, username.getText());
							st.setString(2, (String)usertype.getSelectedItem());
							
							if(st.executeUpdate()>0)
							{
								JOptionPane.showMessageDialog(frame, "User created!!! Please login...");
							}
							else{
								JOptionPane.showMessageDialog(frame, "Failed...");
								return;
							}
						}
						else{
							JOptionPane.showMessageDialog(frame, "User created!!! Please login...");
						}
						
						new Login2();
						frame.dispose();
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnRegister.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_2.add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Login2();
				frame.dispose();
				
			}
		});
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText(null);
				password.setText(null);
				password2.setText(null);
				
			}
		});
		btnReset.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_2.add(btnReset);
		btnCancel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		panel_2.add(btnCancel);
		
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
