
/* ********************************************************************************** *\
 * PROGRAM NAME:	Ticket Management System
 * VERSION:			1.0
 * ----------------------------------------------------------------------------------
 * AUTHOR:			Mohammad Zunayed Hassan
 * EMAIL:			zunayed-hassan@live.com
 * 
 * NOTE:			
 * 
 * 
 * 
\* ********************************************************************************** */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class TicketManagementSystem {
	static int loginFrameWidth = 325;
	static int loginFrameHeight = 215;
	static Settings defaultSettings = new Settings();
	
	
	public static void main(String [] args) throws IOException
	{
		showLoginWindow();
		//MainForm mainForm = new MainForm(defaultSettings);
	}
	
	public static final void showLoginWindow() throws IOException {
		// Now opening a Login window for getting User ID and Password. It also displays connection settings as an advanced option.
		final JFrame loginFrame = new JFrame("Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Setting up LOGIN FRAME icon
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image loginFrameIcon = kit.getImage("Icons/dialog-password.png");
		loginFrame.setIconImage(loginFrameIcon);
		
		// Setting up the position of the LOGIN FRAME at the middle of the screen
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenDimension.width - loginFrameWidth) / 2;
       	int y = (screenDimension.height - loginFrameHeight) / 2;    
       	loginFrame.setBounds(x, y, loginFrameWidth, loginFrameHeight);
       	loginFrame.setResizable(false);
       	
       	// Adding a panel with header image (optional)
       	ImagePanel loginPanel = new ImagePanel("Images/banner_bus.jpg", 0, 0);
       	loginPanel.setLayout(null);
       	loginFrame.add(loginPanel);
       	
       	/* Adding components to LOGIN PANEL
       	 * There will be two parts. First one is for User ID and Passwords and other one is for
       	 * advanced option such as Connection string, database name/ID/password or connection string etc.
       	 */
       	Dimension size;
       	Insets insets = loginPanel.getInsets();
       	
       	// Adding Login components
       	JLabel loginUserIdLabel = new JLabel("User ID: ");
       	loginPanel.add(loginUserIdLabel);
       	size = loginUserIdLabel.getPreferredSize();
       	loginUserIdLabel.setBounds(22 + insets.left, 85 + insets.top, size.width, size.height);
       	
       	final JTextField loginUserIdTextField = new JTextField(14);
       	loginPanel.add(loginUserIdTextField);
       	size = loginUserIdTextField.getPreferredSize();
       	loginUserIdTextField.setBounds(125 + insets.left, 85 + insets.top, size.width, size.height);
       	
       	JLabel loginPasswordLabel = new JLabel("Password: ");
       	loginPanel.add(loginPasswordLabel);
       	size = loginPasswordLabel.getPreferredSize();
       	loginPasswordLabel.setBounds(22 + insets.left, 115 + insets.top, size.width, size.height);
       	
       	final JPasswordField loginPassWrdField = new JPasswordField(14);
       	loginPanel.add(loginPassWrdField);
       	size = loginPassWrdField.getPreferredSize();
       	loginPassWrdField.setBounds(125 + insets.left, 115 + insets.top, size.width, size.height);
       	
       	// Now adding MORE/LESS, CLOSE, LOGIN button
       	final JButton moreButton = new JButton("More >>");
       	loginPanel.add(moreButton);
       	size = moreButton.getPreferredSize();
       	moreButton.setBounds(20 + insets.left, 150 + insets.top, size.width, size.height);
       	
       	JButton closeButton = new JButton("Close");
       	loginPanel.add(closeButton);
       	size = closeButton.getPreferredSize();
       	closeButton.setBounds(132 + insets.left, 150 + insets.top, size.width, size.height);
       	
       	final JButton loginButton = new JButton("Login");
       	loginPanel.add(loginButton);
       	size = loginButton.getPreferredSize();
       	loginButton.setBounds(212 + insets.left, 150 + insets .top, size.width, size.height);
       	
       	// Now adding some advanced options
       	JLabel connectionStringLabel = new JLabel("Connection String: ");
       	loginPanel.add(connectionStringLabel);
       	size = connectionStringLabel.getPreferredSize();
       	connectionStringLabel.setBounds(22 + insets.left, 190 + insets.top, size.width, size.height);
       	
       	final JTextField connectionStringTextField = new JTextField(10);
       	connectionStringTextField.setText(defaultSettings.connectionString);
       	loginPanel.add(connectionStringTextField);
       	size = connectionStringTextField.getPreferredSize();
       	connectionStringTextField.setBounds(168 + insets.left, 190 + insets.top, size.width, size.height);
       	
       	loginFrame.setVisible(true);
       	
       	// Adding action listener for each component
       	moreButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			Dimension loginFrameSize = new Dimension();
       			
       			if(loginFrame.getSize().height == loginFrameHeight) {
       				loginFrameSize.width = loginFrameWidth;
           			loginFrameSize.height = loginFrameHeight + 40;
           			
           			loginFrame.setSize(loginFrameSize);
           			
           			moreButton.setText("<< Less");
       			}
       			else {
       				loginFrameSize.width = loginFrameWidth;
           			loginFrameSize.height = loginFrameHeight;
           			
           			loginFrame.setSize(loginFrameSize);
           			
           			moreButton.setText("More >>");
       			}
       		}
       	});
       	
       	// Close operation
       	closeButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			System.exit(JFrame.EXIT_ON_CLOSE);
       		}
       	});
       	
       	
       	// Here is the task of Login Button
       	loginButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			String userId = loginUserIdTextField.getText().trim();
       			String userPassword = new String(loginPassWrdField.getPassword());		// JPasswordField is a character array, so we convert character array to string directly
       			String connectionString = connectionStringTextField.getText().trim();
       			
       			// Now after getting login information, this method returns a settings for user
       			if((connectionString.length() > 12) && ((userId.length() >= defaultSettings.minUserIdLength) || (userPassword.length() >= defaultSettings.minUserPasswordLength))) {
       				defaultSettings.connectionString = connectionString;
       				defaultSettings.userId = userId;
       				defaultSettings.userPassword = userPassword;
       				
       				// Checking connection
       				Connection con = null;
       				
       				try {
       					Class.forName (defaultSettings.driver).newInstance();
       					con = DriverManager.getConnection (defaultSettings.connectionString + defaultSettings.dataBaseName, defaultSettings.dbUserName, defaultSettings.dbPassword);
       					
       					// Now, when connection is established, then its time check User ID and Password
       					defaultSettings.connectionEstablished = true;
       					
       					try {
       						Statement st = con.createStatement();
           					ResultSet res = st.executeQuery("SELECT * FROM  employee_info WHERE employee_id = " + defaultSettings.userId + " AND password = '" + defaultSettings.userPassword + "';");
           					
           					String tempDBUsrId = new String();
           					String tempDBUsrPwd = new String();
           					String tempDBUsrName = new String();
           					String tempDBUsrType = new String();
           					
           					while(res.next()) {
           						tempDBUsrId = res.getString("employee_id");
           						tempDBUsrPwd = res.getString("password");
           						tempDBUsrName = res.getString("employee_name");
           						tempDBUsrType = res.getString("employee_type");
           					}
           					
           					// Checking User ID and Password between Login field and Database Table.
           					if(tempDBUsrId.equals(defaultSettings.userId) == true && tempDBUsrPwd.equals(defaultSettings.userPassword) == true) {
           						loginButton.setEnabled(false);
           						JOptionPane.showMessageDialog(null, "Congratulation " + tempDBUsrName + " " + " !!! You have logged in successfully.", "Login successfull !!!", JOptionPane.INFORMATION_MESSAGE);
           						
           						// Saving settings
           						defaultSettings.userName = tempDBUsrName;
           						defaultSettings.userType = tempDBUsrType;
           						
           						// Once login is successful, now showing Multiple Document Interface (MDI) Form
           						MainForm mainForm = new MainForm(defaultSettings);
           						loginFrame.dispose();			// ... and closing Login Window
           					}
           					else {
           						JOptionPane.showMessageDialog(null, "ERROR: Please check your User ID and Password again.", "Error", JOptionPane.ERROR_MESSAGE);
           					}
           					
       					} catch(SQLException exp) {
       						JOptionPane.showMessageDialog(null, "ERROR: Can't execute SQL statement.", "Error", JOptionPane.ERROR_MESSAGE);
       						exp.printStackTrace();
       					}
       					
       					
       				} catch(Exception exp) {
       					JOptionPane.showMessageDialog(null, "ERROR: Can't establish connection to the database. Please check your Connection string again or make sure that you are connected with the database.", "Error", JOptionPane.ERROR_MESSAGE);
       					exp.printStackTrace();
       				}
       			}
       			else {
       				JOptionPane.showMessageDialog(null, "ERROR: Please type your User ID, Password or Connection string correctly.", "Error", JOptionPane.ERROR_MESSAGE);
       			}
       		}
       	});
       	
       	// Keystroke: If user press ENTER loginButton will perform its action automatically.
       	loginButton.registerKeyboardAction(loginButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
       	loginButton.registerKeyboardAction(loginButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
}
