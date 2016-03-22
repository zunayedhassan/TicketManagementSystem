import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
	public CustomLabel(String labelText, Insets insets, int x, int y, JPanel panel) {
		this.setText(labelText);
		Dimension size = this.getPreferredSize();
		this.setBounds(x + insets.left, y + insets.top, size.width, size.height);
		panel.add(this);
	}
}
