import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.swing.table.*;
import java.awt.print.*;
import java.text.*;
import com.toedter.calendar.*;

public class MainForm extends JFrame {
	static int mainFrameWidth = 800,
			   mainFrameHeight = 600;
	
	public Settings currentUserSettings;
	
	public JMenu printMenu;
	
	public JMenuItem addEntryMenuItem,
					 saveMenuItem,
					 exitMenuItem,
					 editMenuItem,
					 deleteMenuItem,
					 findMenuItem,
					 refreshTableMenuItem,
					 aboutMenuItem;
	
	final public ToolbarButton addButton,
						 	   printButton,
						 	   refreshButton,
						 	   findButton,
						 	   editButton,
						 	   saveButton,
						 	   deleteButton,
						 	   aboutButton;
	
	public JLabel employeeInfoLabel;
	
	public JTable customerInfoTable = new JTable();
	
	public JPanel ticketEntryInfoPanel;
	
	public CustomTextField ticketIdTextField,
						   customerNameTextField,
						   telephoneTextField,
						   seatNoTextField,
						   costTextField;
	
	public JTextArea addressTextArea;
	
	public CustomComboBox purchaseComboBox,
						  destinitionComboBox;
	
	public JSpinner purchaseTimeSpinner,
					departureTimeSpinner;
	
	public JDateChooser purchaseTimeDateChooser,
						departureTimeDateChooser;
	
	public String[] location = { "Dhaka",
								 "Feni",
								 "Bramonbaria",
								 "Kumilla",
								 "Chittagong" };
	
	public String currentOperationStatus = "null";
	
	public MainForm(Settings currentUserSettings) {
		this.currentUserSettings = currentUserSettings;
		
		final JFrame mainFrame = new JFrame("[ " + currentUserSettings.userName + " ] - Ticket Management System");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(mainFrameWidth, 600);
		mainFrame.setIconImage((Toolkit.getDefaultToolkit()).getImage("Icons/Travel_Bus.png"));			// Setting up icon
		mainFrame.setResizable(false);
		
		// Setting up the position of the LOGIN FRAME at the middle of the screen
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = ((Toolkit.getDefaultToolkit().getScreenSize()).width - mainFrameWidth) / 2;
       	int y = (screenDimension.height - mainFrameHeight) / 2;    
       	mainFrame.setBounds(x, y, mainFrameWidth, mainFrameHeight);
       	
       	// Creating Menubar
       	JMenuBar menuBar = new JMenuBar();
       	mainFrame.setJMenuBar(menuBar);
       	
       	JMenu fileMenu = new JMenu("File");
       	JMenu editMenu = new JMenu("Edit");
       	JMenu viewMenu = new JMenu("View");
       	JMenu helpMenu = new JMenu("Help");
       	
       	menuBar.add(fileMenu);
       	menuBar.add(editMenu);
       	menuBar.add(viewMenu);
       	menuBar.add(helpMenu);
       	
       	// Adding short-cut key for menu
       	fileMenu.setMnemonic('F');
       	editMenu.setMnemonic('E');
       	viewMenu.setMnemonic('v');
       	helpMenu.setMnemonic('H');
       	
       	/* Adding Menu Item in Menu bar */
       	// File --> Add Entry, Save | Print [Ticket Entry, Table] | Exit
       	addEntryMenuItem = new JMenuItem("Add Entry");	// Add Entry
       	fileMenu.add(addEntryMenuItem);
       	
       	saveMenuItem = new JMenuItem("Save");
       	fileMenu.add(saveMenuItem);
       	
       	fileMenu.addSeparator();									// Separator
       	
       	printMenu = new JMenu("Print");						// Print
       	fileMenu.add(printMenu);
       	
       	final JMenuItem printTicketEntryMenuItem = new JMenuItem("Ticket Entry");		// Print Ticket Entry
       	printMenu.add(printTicketEntryMenuItem);
       	
       	final JMenuItem printTableMenuItem = new JMenuItem("Table");					// Print Table
       	printMenu.add(printTableMenuItem);
       	
       	fileMenu.addSeparator();									// Separator
       	
       	exitMenuItem = new JMenuItem("Exit");				// Exit
       	fileMenu.add(exitMenuItem);
       	
       	// Edit --> Edit, Delete | User Information | Find
       	editMenuItem = new JMenuItem("Edit");				// Edit
       	editMenu.add(editMenuItem);
       	
       	deleteMenuItem = new JMenuItem("Delete");			// Delete
       	editMenu.add(deleteMenuItem);
       	
       	editMenu.addSeparator();									// Separator
       	
       	findMenuItem = new JMenuItem("Find");				// Find
		editMenu.add(findMenuItem);
		
		refreshTableMenuItem = new JMenuItem("Refresh Table");			// Refresh Table
		viewMenu.add(refreshTableMenuItem);
		
		// Help --> About
		aboutMenuItem = new JMenuItem("About");			// About
		helpMenu.add(aboutMenuItem);
		
		/* Now adding Toolbar and its buttons */
		JToolBar toolbar = new JToolBar("MDI Main Toolbar");
       	mainFrame.add(toolbar, BorderLayout.NORTH);
       	
       	addButton = new ToolbarButton("Icons/add.png", "Add new entry");			// Add
       	toolbar.add(addButton);
       	
       	printButton = new ToolbarButton("Icons/print.png", "Print table");		// Print
       	toolbar.add(printButton);
       	
       	toolbar.addSeparator();																	// Separator
       	
       	refreshButton = new ToolbarButton("Icons/refresh.png", "Refresh table");	// Refresh
       	toolbar.add(refreshButton);
       	
       	findButton = new ToolbarButton("Icons/find.png", "Find entry");			// find
       	toolbar.add(findButton);
       	
       	toolbar.addSeparator();	
       	
       	editButton = new ToolbarButton("Icons/edit.png", "Edit entry");			// Edit
       	toolbar.add(editButton);
       	
       	saveButton = new ToolbarButton("Icons/save.png", "Save entry");			// Save
       	toolbar.add(saveButton);
       	
       	deleteButton = new ToolbarButton("Icons/delete.png", "Delete entry");		// Delete
       	toolbar.add(deleteButton);
       	
       	toolbar.addSeparator();																	// Separator
       	
       	aboutButton = new ToolbarButton("Icons/about.png", "About Ticket Management System");		// About
       	toolbar.add(aboutButton);
       	
       	/* Now adding a split pane to divide the window into two parts. Left half is for showing table at tablePanel and the right one (panel) is for showing data of selected row. */
       	JPanel tablePanel = new JPanel();									// tablePanel
       	tablePanel.setMinimumSize(new Dimension(800-300, 200));
       	tablePanel.setLayout(new BorderLayout());
       	
       	JScrollPane scrollPane = new JScrollPane(customerInfoTable);		// scrollPane
       	scrollPane.getViewport().setBackground(new Color(212, 212, 212));
       	
       	tablePanel.add(scrollPane);
       	
       	JPanel panel = new JPanel();										// panel
       	panel.setLayout(new BorderLayout());
       	panel.setMaximumSize(new Dimension(300, 200));
       	panel.setMinimumSize(new Dimension(200, 200));
       	
       	JSplitPane spltPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, panel);		// SplitPane
       	mainFrame.add(spltPane, BorderLayout.CENTER);
       	
       	/* On the right side there will be two panel added. Top panel is for showing employee name and status. Bottom panel is for showing current ticket entry */
       	JPanel employeeInfoPanel = new JPanel();						// employeeInfoPanel
       	employeeInfoPanel.setBackground(new Color(220, 230, 245));
       	employeeInfoPanel.setBorder(BorderFactory.createLineBorder(new Color(156, 20, 20, 255), 2));
       	panel.add(employeeInfoPanel, BorderLayout.NORTH);
       	
       	employeeInfoLabel = new JLabel(currentUserSettings.userName + " (" + currentUserSettings.userType + ")");		// employeeInfoLabel
       	employeeInfoPanel.add(employeeInfoLabel);
       	
       	ticketEntryInfoPanel = new JPanel();						// ticketEntryInfoPanel
       	ticketEntryInfoPanel.setLayout(null);
       	ticketEntryInfoPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
       	panel.add(ticketEntryInfoPanel, BorderLayout.CENTER);
       	
       	/* Adding components for ticketEntryInfoPanel */
       	Dimension size;
	    Insets insets = ticketEntryInfoPanel.getInsets();
	    
	    CustomLabel ticketIdLabel = new CustomLabel("TICKET ID:", insets, 10, 10 + (0 * 25), ticketEntryInfoPanel);
	    CustomLabel customerNameLabel = new CustomLabel("CUSTOMER NAME:", insets, 10, 10 + (2 * 25), ticketEntryInfoPanel);
       	CustomLabel telephoneLabel = new CustomLabel("TELEPHONE:", insets, 10, 10 + (3 * 25), ticketEntryInfoPanel);
       	CustomLabel addressLabel = new CustomLabel("ADDRESS:", insets, 10, 10 + (4 * 25), ticketEntryInfoPanel);
       	CustomLabel purchaseLabel = new CustomLabel("FROM:", insets, 10, 10 + (7 * 25) + 10, ticketEntryInfoPanel);
       	CustomLabel destinationLabel = new CustomLabel("DESTINITION:", insets, 10, 10 + (9 * 25) - 7, ticketEntryInfoPanel);
       	CustomLabel seatNoLabel = new CustomLabel("SEAT NO:", insets, 10, 10 + (10 * 25), ticketEntryInfoPanel);
       	CustomLabel purchaseTimeLabel = new CustomLabel("PURCHASE TIME:", insets, 10, 10 + (11 * 25), ticketEntryInfoPanel);
       	CustomLabel departureTimeLabel = new CustomLabel("DEPARTURE TIME:", insets, 10, 10 + (13 * 25), ticketEntryInfoPanel);
       	CustomLabel costLabel = new CustomLabel("COST (BDT):", insets, 10, 10 + (16 * 25), ticketEntryInfoPanel);
       	
       	ticketIdTextField = new CustomTextField(11, insets, 125, 10 + (0 * 25), ticketEntryInfoPanel);
       	customerNameTextField = new CustomTextField(11, insets, 125, 10 + (2 * 25), ticketEntryInfoPanel);
       	telephoneTextField = new CustomTextField(11, insets, 125, 10 + (3 * 25), ticketEntryInfoPanel);
       	
       	addressTextArea = new JTextArea();
       	addressTextArea.setLineWrap(true);
       	addressTextArea.setWrapStyleWord(true);
       	
       	JScrollPane addressScrollPane = new JScrollPane(addressTextArea);
       	size = addressScrollPane.getPreferredSize();
       	addressScrollPane.setBounds(125 + insets.left, (4 * 25) + 12 + insets.top, size.width, size.height);
       	addressScrollPane.setSize(125, 65);
       	ticketEntryInfoPanel.add(addressScrollPane);
       	
       	purchaseComboBox = new CustomComboBox(location, insets, 125, 10 + (7 * 25) + 10, ticketEntryInfoPanel);
       	destinitionComboBox = new CustomComboBox(location, insets, 125, 10 + (9 * 25) - 7, ticketEntryInfoPanel);
       	
       	seatNoTextField = new CustomTextField(11, insets, 125, 10 + (10 * 25), ticketEntryInfoPanel);
       	
       	purchaseTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
       	purchaseTimeSpinner.setEditor(new JSpinner.DateEditor(purchaseTimeSpinner, "hh:mm"));
       	size = purchaseTimeSpinner.getPreferredSize();
       	purchaseTimeSpinner.setBounds(125 + insets.left, 10 + (11 * 25) + insets.top, size.width, size.height);
       	ticketEntryInfoPanel.add(purchaseTimeSpinner);
       	
       	purchaseTimeDateChooser = new JDateChooser();
       	purchaseTimeDateChooser.setBounds(125 + insets.left, 10 + (12 * 25) + insets.top, 125, 20);
       	ticketEntryInfoPanel.add(purchaseTimeDateChooser);
       	
       	departureTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
       	departureTimeSpinner.setEditor(new JSpinner.DateEditor(departureTimeSpinner, "hh:mm"));
       	size = departureTimeSpinner.getPreferredSize();
       	departureTimeSpinner.setBounds(125 + insets.left, 10 + (13 * 25) + insets.top, size.width, size.height);
       	ticketEntryInfoPanel.add(departureTimeSpinner);
       	
       	departureTimeDateChooser = new JDateChooser();
       	departureTimeDateChooser.setBounds(125 + insets.left, 10 + (14 * 25) + insets.top, 125, 20);
       	ticketEntryInfoPanel.add(departureTimeDateChooser);
       	
       	costTextField = new CustomTextField(11, insets, 125, 10 + (16 * 25), ticketEntryInfoPanel);
       	
       	// ... and an additional panel is also added at the south to show operational information
       	JPanel infoPanel = new JPanel();
       	infoPanel.setBackground(Color.LIGHT_GRAY);
       	mainFrame.add(infoPanel, BorderLayout.SOUTH);
       	
       	JLabel infoLabel = new JLabel();
       	infoLabel.setText(currentUserSettings.authorName + ". " + currentUserSettings.email);
       	infoPanel.add(infoLabel);
       	
       	/* Initializing Components for first time run */
       	refreshAll();
		mainFrame.setVisible(true);
		
		/* Adding Action Listener */
		// Adding Action Listener for addEntryMenuItem
		addEntryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				initializeAddOperation();
			}
		});
		
		// Adding Action Listener for saveMenuItem
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (currentOperationStatus == "Add") {
						performAddOperation();
					}
					else if (currentOperationStatus == "Edit") {
						performEditOperation();
					}
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
		});
		
		// Adding Action Listener for printTicketEntryMenuItem
		printTicketEntryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				(new PrintUtilities(ticketEntryInfoPanel)).print();
				refreshAll();
			}
		});
		
		// Adding Action Listener for printMenuItem
		printTableMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					customerInfoTable.print();
					refreshAll();
				} catch (PrinterException exp) {
					exp.printStackTrace();
				}
			}
		});
		
		// Adding Action Listener for exitMenuItem
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(mainFrame.EXIT_ON_CLOSE);
			}
		});
		
		// Adding ActionListener for editMenuItem
		editMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				initializeEditOperation();
			}
		});
		
		// Adding Action Listener for deleteMenuItem
		deleteMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				performDeleteOperation();
			}
		});
		
		// Adding Action Listener for refreshTableMenuItem
		refreshTableMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				refreshAll();
			}
		});
		
		// Adding Action Listener for aboutMenuItem
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				About aboutWindow = new About();
			}
		});
		
		// Adding Action Listener for addButton
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				initializeAddOperation();
			}
		});
		
		// These are for print button
       	// creating popup menu for printButton
       	final JPopupMenu printPopup = new JPopupMenu();
       	
       	printPopup.add(new JMenuItem(new AbstractAction("Ticket Entry") {
            public void actionPerformed(ActionEvent e) {
       			(new PrintUtilities(ticketEntryInfoPanel)).print();
       			
       			refreshAll();
            }
        }));
       	
       	printPopup.add(new JMenuItem(new AbstractAction("Print Table") {
            public void actionPerformed(ActionEvent event) {
                try {
                	customerInfoTable.print();
                } catch(PrinterException exp) {
                	exp.printStackTrace();
                } finally {
                	refreshAll();
                }
            }
        }));
       	
       	// Adding Action Listener for printButton
       	printButton.addMouseListener(new MouseAdapter() {
       		public void mousePressed(MouseEvent event) {
       			printPopup.show(event.getComponent(), event.getX(), event.getY());
       		}
       	});
		
		// Adding Action Listener for refreshTableButton
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				refreshAll();
			}
		});
		
		// Adding Action Listener for findButton
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				FindWindow find = new FindWindow(customerInfoTable);
			}
		});
		
		// Adding Action Listener for editButton
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				initializeEditOperation();
			}
		});
		
		// Adding Action Listener for saveButton
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (currentOperationStatus == "Add") {
						performAddOperation();
					}
					else if (currentOperationStatus == "Edit") {
						performEditOperation();
					}
				} catch (SQLException exp) {
					exp.printStackTrace();
				}
			}
		});
		
		// Adding Action Listener for deleteButton
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				performDeleteOperation();
			}
		});
		
		// Adding Action Listener for aboutButton
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				About aboutWindow = new About();
			}
		});
		
		// Adding Action Listener for customerInfoTable
		customerInfoTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if(mouseEvent.getClickCount() == 1) {
					// Enabling Edit and Save button/menuItem
					if (currentOperationStatus == "null") {
						enableOrDisableMenuItem(true, false, true, true, true, true);
				       	enableOrDisableToolbarButton(true, true, true, true, false, true);
					}
					
					// Getting Information from Table
					int rowIndex = customerInfoTable.getSelectedRow();
					
					if (currentOperationStatus != "Add") {
						ticketIdTextField.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 0)));
						customerNameTextField.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 1)));
						telephoneTextField.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 2)));
						addressTextArea.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 3)));
						purchaseComboBox.setSelectedItem(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 4)));
						destinitionComboBox.setSelectedItem(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 6)));
						seatNoTextField.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 5)));
						purchaseTimeSpinner.setValue((new MySQLDateTimeConverter()).getConvertedTime(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 7))));
						purchaseTimeDateChooser.setDate((new MySQLDateTimeConverter()).getConvertedDate(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 7))));
						departureTimeSpinner.setValue((new MySQLDateTimeConverter()).getConvertedTime(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 8))));
						departureTimeDateChooser.setDate((new MySQLDateTimeConverter()).getConvertedDate(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 8))));
						costTextField.setText(String.valueOf(customerInfoTable.getModel().getValueAt(rowIndex, 9)));
					}
				}
			}
		});
	}
	
	public void refreshCustomerInfoTable() {
		try {
			Class.forName(currentUserSettings.driver);
			Connection conn = DriverManager.getConnection(currentUserSettings.connectionString + currentUserSettings.dataBaseName, currentUserSettings.dbUserName, currentUserSettings.dbPassword);
			
			try {
				final Statement st = conn.createStatement();
				ResultSet result = st.executeQuery("SELECT * FROM customer_info;");
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
				customerInfoTable.setModel(currentTableModel);
				
			} catch (SQLException exp) {
				JOptionPane.showMessageDialog(null, "ERROR: Can't execute SQL statement.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch(Exception exp) {
			JOptionPane.showMessageDialog(null, "ERROR: Can't connect to the database. Please, check your connection again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void enableOrDisableMenuItem(boolean addEntryMenuItemStatus, boolean saveMenuItemStatus, boolean editMenuItemStatus, boolean deleteMenuItemStatus, boolean findMenuItemStatus, boolean refreshTableMenuItemStatus) {
		addEntryMenuItem.setEnabled(addEntryMenuItemStatus);
		saveMenuItem.setEnabled(saveMenuItemStatus);
		editMenuItem.setEnabled(editMenuItemStatus);
		deleteMenuItem.setEnabled(deleteMenuItemStatus);
		findMenuItem.setEnabled(findMenuItemStatus);
		refreshTableMenuItem.setEnabled(refreshTableMenuItemStatus);
	}
	
	public void enableOrDisableToolbarButton(boolean addButtonStatus, boolean refreshButtonStatus, boolean findButtonStatus, boolean editButtonStatus, boolean saveButtonStatus, boolean deleteButtonStatus) {
		addButton.setEnabled(addButtonStatus);
		refreshButton.setEnabled(refreshButtonStatus);
		findButton.setEnabled(findButtonStatus);
		editButton.setEnabled(editButtonStatus);
		saveButton.setEnabled(saveButtonStatus);
		deleteButton.setEnabled(deleteButtonStatus);
	}
	
	public void enableOrDisableTicketEntryInfoPanel(boolean status) {
		ticketIdTextField.setEditable(false);
		customerNameTextField.setEditable(status);
		telephoneTextField.setEditable(status);
		addressTextArea.setEditable(status);
		
		if (!status)
			addressTextArea.setBackground(new Color(238, 238, 238));
		else
			addressTextArea.setBackground(Color.WHITE);
		
		seatNoTextField.setEditable(status);
		costTextField.setEditable(status);
	}
	
	public void clearTicketEntryInfoPanel() {
		String clear = new String();
		
		ticketIdTextField.setText(clear);
		customerNameTextField.setText(clear);
		telephoneTextField.setText(clear);
		addressTextArea.setText(clear);
		purchaseComboBox.setSelectedIndex(0);
		destinitionComboBox.setSelectedIndex(0);
		seatNoTextField.setText(clear);
		purchaseTimeSpinner.setValue(new Date());
		purchaseTimeDateChooser.setDate(new Date());
		departureTimeSpinner.setValue(new Date());
		departureTimeDateChooser.setDate(new Date());
		costTextField.setText(clear);
	}
	
	public void refreshAll() {
		refreshCustomerInfoTable();										// read data from database
       	enableOrDisableMenuItem(true, false, false, false, true, true);
       	enableOrDisableToolbarButton(true, true, true, false, false, false);
       	enableOrDisableTicketEntryInfoPanel(false);
       	clearTicketEntryInfoPanel();
       	
       	currentOperationStatus = "null";
	}
	
	public void initializeAddOperation() {
		enableOrDisableMenuItem(false, true, false, false, true, true);
		enableOrDisableToolbarButton(false, true, true, false, true, false);
		enableOrDisableTicketEntryInfoPanel(true);
		clearTicketEntryInfoPanel();
		
		currentOperationStatus = "Add";
	}
	
	public void performAddOperation() throws SQLException {
		int response = JOptionPane.showConfirmDialog(null, "Are you sure to add this entry ?" , "Confirmation Message", JOptionPane.YES_NO_OPTION);
		
		if(response == JOptionPane.YES_OPTION) {
			Connection conn = null;
			
			try {
				
				Class.forName (currentUserSettings.driver).newInstance();
				conn = DriverManager.getConnection (currentUserSettings.connectionString + currentUserSettings.dataBaseName, currentUserSettings.dbUserName, currentUserSettings.dbPassword);
				
				try {
					Statement st = conn.createStatement();
					
					st.executeUpdate("INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('" + customerNameTextField.getText().trim() + "', '" + telephoneTextField.getText().trim() + "', '" + addressTextArea.getText().trim() + "', '" + purchaseComboBox.getSelectedItem() + "', '"  + seatNoTextField.getText().trim() + "', '" + destinitionComboBox.getSelectedItem() + "', '" + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(purchaseTimeDateChooser.getDate())) + " " + String.valueOf(purchaseTimeSpinner.getValue()).substring(11, 19) + "', '" + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(departureTimeDateChooser.getDate())) + " " + String.valueOf(purchaseTimeSpinner.getValue()).substring(11, 19) + "', " + costTextField.getText().trim() + ");");
					
				} catch (SQLException exp) {
					JOptionPane.showMessageDialog(null, "ERROR: Unable to add this entry.", "Error", JOptionPane.ERROR_MESSAGE);
					exp.printStackTrace();
				} finally {
					refreshAll();
					currentOperationStatus = "null";
				}
				
			} catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "ERROR: Can't connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
				exp.printStackTrace();
			}
		}
		else {
			refreshAll();
			currentOperationStatus = "null";
		}
	}
	
	public void initializeEditOperation() {
		enableOrDisableMenuItem(false, true, false, false, true, true);
		enableOrDisableToolbarButton(false, true, true, false, true, false);
		enableOrDisableTicketEntryInfoPanel(true);
		
		currentOperationStatus = "Edit";
	}
	
	public void performEditOperation() {
		int response = JOptionPane.showConfirmDialog(null, "Are you sure to update this entry ?" , "Confirmation Message", JOptionPane.YES_NO_OPTION);
		
		if(response == JOptionPane.YES_OPTION) {
			Connection conn = null;
			
			try {
				
				Class.forName (currentUserSettings.driver).newInstance();
				conn = DriverManager.getConnection (currentUserSettings.connectionString + currentUserSettings.dataBaseName, currentUserSettings.dbUserName, currentUserSettings.dbPassword);
				
				try {
					Statement st = conn.createStatement();
					
					st.executeUpdate("UPDATE customer_info SET customer_name = '" + customerNameTextField.getText().trim() + "', telephone_no = '" + telephoneTextField.getText().trim() + "', address = '" + addressTextArea.getText().trim() + "', purchase_location = '" + purchaseComboBox.getSelectedItem() + "', seat_no = '" + seatNoTextField.getText().trim() + "', destination = '" + destinitionComboBox.getSelectedItem() + "', purchase_time = '" + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(purchaseTimeDateChooser.getDate())) + " " + String.valueOf(purchaseTimeSpinner.getValue()).substring(11, 19) + "', departure_time = '" + String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(departureTimeDateChooser.getDate())) + " " + String.valueOf(purchaseTimeSpinner.getValue()).substring(11, 19) + "', cost = " + costTextField.getText().trim() + " WHERE ticket_id = " + ticketIdTextField.getText() + ";");
					
				} catch (SQLException exp) {
					JOptionPane.showMessageDialog(null, "ERROR: Unable to update this entry.", "Error", JOptionPane.ERROR_MESSAGE);
					exp.printStackTrace();
				} finally {
					refreshAll();
					currentOperationStatus = "null";
				}
				
			} catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "ERROR: Can't connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
				exp.printStackTrace();
			}
		}
		else {
			refreshAll();
			currentOperationStatus = "null";
		}
	}
	
	public void performDeleteOperation() {
		int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete this entry ?" , "Confirmation Message", JOptionPane.YES_NO_OPTION);
		
		if(response == JOptionPane.YES_OPTION) {
			Connection conn = null;
			
			try {
				
				Class.forName (currentUserSettings.driver).newInstance();
				conn = DriverManager.getConnection (currentUserSettings.connectionString + currentUserSettings.dataBaseName, currentUserSettings.dbUserName, currentUserSettings.dbPassword);
				
				try {
					Statement st = conn.createStatement();
					
					st.executeUpdate("DELETE FROM customer_info WHERE ticket_id = " + ticketIdTextField.getText() + ";");
					
				} catch (SQLException exp) {
					JOptionPane.showMessageDialog(null, "ERROR: Unable to update this entry.", "Error", JOptionPane.ERROR_MESSAGE);
					exp.printStackTrace();
				} finally {
					refreshAll();
					currentOperationStatus = "null";
				}
				
			} catch(Exception exp) {
				JOptionPane.showMessageDialog(null, "ERROR: Can't connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
				exp.printStackTrace();
			}
		}
		else {
			refreshAll();
			currentOperationStatus = "null";
		}
	}
}
