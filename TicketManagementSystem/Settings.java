public class Settings {
	// Software related information
	public String applicationName = "TICKET MANAGEMENT SYSTEM";
	public String version = "1.0";
	public String authorName = "Mohammad Zunayed Hassan";
	public String email = "zunayed-hassan@live.com";
	
	// Database settings
	public String connectionString;
	public String dataBaseName;
	public String dbUserName;
	public String dbPassword;
	public String driver = "com.mysql.jdbc.Driver";
	
	// User settings
	public String userId;
	public String userPassword;
	public String userName;
	public String userType;
	
	// Database constraints
	public int minUserIdLength = 1;
	public int minUserPasswordLength = 3;
	
	boolean connectionEstablished = false;
	
	public Settings() {
		this.connectionString = "jdbc:mysql://127.0.0.1/";
		this.dbUserName = "root";
		this.dataBaseName = "ticketmanagementsystem";
		this.dbPassword = "123456";
	}
}
