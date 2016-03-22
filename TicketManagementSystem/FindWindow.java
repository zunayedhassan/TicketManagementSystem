import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;


public class FindWindow extends JFrame {
	int findWidth = 360,
		findHeight = 120;
	
	String[] category = { "Ticket ID",
						  "Customer Name",
						  "From",
						  "To" };
	
	public String searchKeyword = new String();
	public String sqlSyntex = new String();
	
	public FindWindow(JTable customerInfoTable) {
		this.setTitle("Find");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(findWidth, findHeight);
		this.setResizable(false);
		this.setIconImage((Toolkit.getDefaultToolkit()).getImage("Icons/find.png"));
		
		// Setting up the position of the SEARCH FRAME at the middle of the screen
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenDimension.width - findWidth) / 2;
       	int y = (screenDimension.height - findHeight) / 2;    
       	this.setBounds(x, y, findWidth, findHeight);
       	
       	// Adding components
       	JPanel searchPanel = new JPanel();
       	searchPanel.setLayout(null);
       	
       	Dimension size;
	    Insets insets = searchPanel.getInsets();
	    
	    final CustomComboBox categoryComboBox = new CustomComboBox(category, insets, 10, 10, searchPanel);
	    CustomLabel searchLabel = new CustomLabel("Search:", insets, 140, 15, searchPanel);
	    final CustomTextField searchTextField = new CustomTextField(12, insets, 200, 15, searchPanel);
       	
       	JButton searchButton = new JButton("Search");
       	size = searchButton.getPreferredSize();
       	searchButton.setBounds(260 + insets.left, 50 + insets.top, size.width, size.height);
       	searchPanel.add(searchButton);
       	
       	// Keystroke: If user press ENTER searchButton will perform its action automatically.
       	searchButton.registerKeyboardAction(searchButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_IN_FOCUSED_WINDOW);
       	searchButton.registerKeyboardAction(searchButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
	    this.add(searchPanel);
		this.setVisible(true);
		
		final JTable table = customerInfoTable;
		
		searchButton.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent event) {
       			if(!searchTextField.getText().trim().equals("")) {
       				searchKeyword = "%" + searchTextField.getText().trim() + "%";
       				
       				// Ticket ID
           			if(categoryComboBox.getSelectedItem() == "Ticket ID") {
           				sqlSyntex = "SELECT * FROM customer_info WHERE ticket_id LIKE '" + searchKeyword + "';";
           			}
           			// Customer Name
           			else if (categoryComboBox.getSelectedItem() == "Customer Name") {
           				sqlSyntex = "SELECT * FROM customer_info WHERE customer_name LIKE '" + searchKeyword + "';";
           			}
           			else if (categoryComboBox.getSelectedItem() == "From") {
           				sqlSyntex = "SELECT * FROM customer_info WHERE purchase_location LIKE '" + searchKeyword + "';";
           			}
           			else if (categoryComboBox.getSelectedItem() == "To") {
           				sqlSyntex = "SELECT * FROM customer_info WHERE destination LIKE '" + searchKeyword + "';";
           			}
           			
           			// Executing SQL Statement
           			try {
           				Settings currentUserSettings = new Settings();
           				
           				Class.forName(currentUserSettings.driver);
           				Connection conn = DriverManager.getConnection(currentUserSettings.connectionString + currentUserSettings.dataBaseName, currentUserSettings.dbUserName, currentUserSettings.dbPassword);
           				
           				try {
           					final Statement st = conn.createStatement();
           					ResultSet result = st.executeQuery(sqlSyntex);
           					ResultSetMetaData md = result.getMetaData();
           					
           					int columnCount = md.getColumnCount();
           					Vector columns = new Vector(columnCount);
           					
           					//store column names
           					for(int i = 1; i <= columnCount; i++)
           						columns.add(md.getColumnName(i));

           					Vector data = new Vector();
           					Vector row;
           					
           					//store row data
           					while(result.next()) {
           						row = new Vector(columnCount);
           						
           						for(int i = 1; i <= columnCount; i++) {
           							row.add(result.getString(i));
           						}
           						
           						data.add(row);
           					}
           					
           					DefaultTableModel currentTableModel = new DefaultTableModel(data, columns);
           					table.setModel(currentTableModel);
           					
           				} catch (SQLException exp) {
           					JOptionPane.showMessageDialog(null, "ERROR: Can't execute SQL statement.", "Error", JOptionPane.ERROR_MESSAGE);
           				}
           			} catch(Exception exp) {
           				JOptionPane.showMessageDialog(null, "ERROR: Can't connect to the database. Please, check your connection again.", "Error", JOptionPane.ERROR_MESSAGE);
           			} finally {
           				searchKeyword = null;
           				sqlSyntex = null;
           			}
       			}
       			else {
       				JOptionPane.showMessageDialog(null, "ERROR: Please, type something on search box.", "Error", JOptionPane.ERROR_MESSAGE);
       			}
       		}
       	});
	}
}
