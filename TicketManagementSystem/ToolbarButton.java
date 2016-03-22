import javax.swing.*;
import java.awt.*;

public class ToolbarButton extends JButton {
	public ToolbarButton(String iconName, String toolTipText)
	{
		this.setIcon(new ImageIcon(iconName));
		this.setToolTipText(toolTipText);
	}
}
