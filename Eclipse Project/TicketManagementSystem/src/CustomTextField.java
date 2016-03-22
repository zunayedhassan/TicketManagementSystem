import javax.swing.*;
import java.awt.*;

public class CustomTextField extends JTextField {
	public CustomTextField(int columns, Insets insets, int x, int y, JPanel panel) {
		this.setColumns(columns);
		Dimension size = this.getPreferredSize();
		this.setBounds(x + insets.left, y + insets.top, size.width, size.height);
		panel.add(this);
	}
}
