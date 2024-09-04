package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.Font;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class PixelFoxPane extends GraphicsPane {
    private MainApplication program;
    private GCompound pixelFoxContainer;
    private GLabel pixelFoxLabelLine1;
    private GLabel pixelFoxLabelLine2;
    private GImage pixelFoxImage;

    public PixelFoxPane(MainApplication app) {
        this.program = app;
        
        // Set up black screen background
        pixelFoxContainer = new GCompound();
        GRect blackScreen = new GRect(program.getWidth(), program.getHeight());
        blackScreen.setFilled(true);
        blackScreen.setFillColor(Color.BLACK);

        // Set up Pixel Fox labels
        pixelFoxLabelLine1 = new GLabel("Pixel Fox");
        pixelFoxLabelLine2 = new GLabel("Productions");
        Font font = new Font("SansSerif", Font.BOLD, 200);
        pixelFoxLabelLine1.setFont(font);
        pixelFoxLabelLine2.setFont(font);
        pixelFoxLabelLine1.setColor(Color.MAGENTA);
        pixelFoxLabelLine2.setColor(Color.MAGENTA);

        double x1 = (program.getWidth() - pixelFoxLabelLine1.getWidth()) / 2;
        double y1 = (program.getHeight() - pixelFoxLabelLine1.getAscent()) / 2;
        double x2 = (program.getWidth() - pixelFoxLabelLine2.getWidth()) / 2;
        double y2 = y1 + pixelFoxLabelLine1.getAscent();

        pixelFoxLabelLine1.setLocation(x1, y1);
        pixelFoxLabelLine2.setLocation(x2, y2);

        // Set up Pixel Fox image
        pixelFoxImage = new GImage("sounds/PixelFox.gif");
        pixelFoxImage.setSize(400, 300);
        double xImage = (program.getWidth() - pixelFoxImage.getWidth()) / 2 + 40;
        double yImage = -20;  // Adjust the Y-coordinate as needed
        pixelFoxImage.setLocation(xImage, yImage);
        pixelFoxImage.setVisible(false);
        
        // Add components to the container
        pixelFoxContainer.add(blackScreen);
        pixelFoxContainer.add(pixelFoxLabelLine1);
        pixelFoxContainer.add(pixelFoxLabelLine2);
        pixelFoxContainer.add(pixelFoxImage);

        pixelFoxContainer.setVisible(true);
    }

    @Override
    public void showContents() {
        program.add(pixelFoxContainer);
        pixelFoxLabelLine1.setVisible(true);
        pixelFoxLabelLine2.setVisible(true);
        pixelFoxImage.setVisible(true);
    }

    @Override
    public void hideContents() {
        program.remove(pixelFoxContainer);
        pixelFoxLabelLine1.setVisible(false);
        pixelFoxLabelLine2.setVisible(false);
        pixelFoxImage.setVisible(false);
    }
}
