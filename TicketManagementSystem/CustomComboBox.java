import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JComboBox {
	public CustomComboBox(String[] list, Insets insets, int x, int y, JPanel panel) {
		for (int i = 0; i < list.length; i++) {
			this.addItem(list[i]);
		}
		
		Dimension size = this.getPreferredSize();
		this.setBounds(x + insets.left, y + insets.top, size.width, size.height);
		panel.add(this);
	}
}
