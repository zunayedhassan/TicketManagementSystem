import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
	Image image;
	int positionX;
	int positionY;
	
	public ImagePanel(String fileName, int posX, int posY) throws IOException {
		this.positionX = posX;
		this.positionY = posY;
		
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics grph) {
		super.paintComponents(grph);
		if(image == null)
			return;
		else
			grph.drawImage(image, this.positionX, this.positionY, null);
	}
}
