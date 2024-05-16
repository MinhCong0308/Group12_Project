package screen.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

class GradientPanel extends JPanel {
    private BufferedImage gradientLogo;

    public GradientPanel(){
        gradientLogo = GradientLogoMaker.drawImage(512, Color.decode("#000080"), Color.decode("#ADD8E6"), "", true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gradientLogo != null) {
            int width = getWidth();
            int height = getHeight();
            g.drawImage(gradientLogo, 0, 0, width, height, this);
        }
    }
    
}
