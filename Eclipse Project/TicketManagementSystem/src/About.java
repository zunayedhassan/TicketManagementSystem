import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class About extends JFrame {
	int aboutWidth = 425,
		aboutHeight = 170;
	
	public About() {
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("About " + (new Settings()).applicationName);
		this.setSize(aboutWidth, aboutHeight);
		
		// Setting up the position of this frame at the middle of the screen
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = ((Toolkit.getDefaultToolkit().getScreenSize()).width - aboutWidth) / 2;
       	int y = (screenDimension.height - aboutHeight) / 2;    
      	this.setBounds(x, y, aboutWidth, aboutHeight);
      	
      	this.setIconImage((Toolkit.getDefaultToolkit()).getImage("Icons/about.png"));			// Setting up icon
		this.setResizable(false);
		
		ImagePanel aboutPanel;
		
		try {
			aboutPanel = new ImagePanel("Images/Travel_Bus_Large.png", 10, 5);
			aboutPanel.setLayout(null);
	       	this.add(aboutPanel);
	       	
	       	CustomLabel applicationNameLabel = new CustomLabel((new Settings()).applicationName, aboutPanel.getInsets(), 170, 15 + (0 * 25), aboutPanel);
	       	CustomLabel versionNameLabel = new CustomLabel("VERSION: " + (new Settings()).version, aboutPanel.getInsets(), 170, 15 + (1 * 25), aboutPanel);
	       	CustomLabel authorNameLabel = new CustomLabel("AUTHOR: " + (new Settings()).authorName, aboutPanel.getInsets(), 170, 15 + (2 * 25) + 10, aboutPanel);
	       	CustomLabel emailLabel = new CustomLabel("EMAIL: " + (new Settings()).email, aboutPanel.getInsets(), 170, 15 + (3 * 25) + 10, aboutPanel);

		} catch (IOException exp) {
			exp.printStackTrace();
		}
		
		this.setVisible(true);
	}
}
