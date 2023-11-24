package GUI;

import javax.swing.*;
import java.awt.*;

public class MultiIcon implements Icon {

    private Icon icon1;
    private Icon icon2;

    public MultiIcon(Icon icon1, Icon icon2) {
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if(hasSingleIcon()) {
            icon1.paintIcon(c, g, x, y);
        }
        else {
            icon1.paintIcon(c, g, x, y);
            icon2.paintIcon(c, g, x, y);}
    }

    @Override
    public int getIconWidth() {
        return Math.max(icon1.getIconWidth(), icon1.getIconWidth());
    }

    public boolean hasSingleIcon() {
        return icon2 == null;
    }

    public Icon getIcon1() {
        return icon1;
    }

    public Icon getIcon2() {
        return icon2;
    }

    public void removeIcon1() {
        icon1 = null;

    }

    public void removeIcon2() {
        icon2 = null;
    }

    @Override
    public int getIconHeight() {
        return icon1.getIconHeight();
    }

    // Ensure that the icon respects the specified width and height
    public void setIconSize(int width, int height) {
        if (icon1 != null) {
            ImageIcon imageIcon1 = (ImageIcon) icon1;
            Image image1 = imageIcon1.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.icon1 = new ImageIcon(image1);
        }

        if (icon2 != null) {
            ImageIcon imageIcon2 = (ImageIcon) icon2;
            Image image2 = imageIcon2.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.icon2 = new ImageIcon(image2);
        }
    }
}