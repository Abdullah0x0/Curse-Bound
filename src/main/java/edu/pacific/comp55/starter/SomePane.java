package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane implements MouseListener {
    private MainApplication program;
	private ArrayList<GObject> render = new ArrayList<GObject>();
    private GImage controlScreen;
    private GButton returnButton;
    private GButton nextButton;
    private GLabel controlsTextTitle;
    private GCompound controlsText;
    double buttonWidth = 150;
    double buttonHeight = 60;

    
    public SomePane(MainApplication app) {
        this.program = app;

        double buttonX = (program.getWidth() - buttonWidth) / 2 - 100;
        double buttonY = program.getHeight() - 100;

      // Reposition background image to fit the screen
        controlScreen = new GImage("sounds/ControlsScreen.jpg", 0, 0);
        controlScreen.setSize(program.getWidth(), program.getHeight());
        render.add(controlScreen);

      // Set up "Controls" title at the top center
        controlsTextTitle = new GLabel("Controls", program.getWidth() / 2, 50);
        controlsTextTitle.setFont("Chiller-168");
        controlsTextTitle.setColor(Color.WHITE);
        controlsTextTitle.move(-controlsTextTitle.getWidth() / 2, 125);
        render.add(controlsTextTitle);

      // Set up GCompound for control information
        controlsText = new GCompound();
        controlsText.setLocation(20, controlsTextTitle.getY() + controlsTextTitle.getHeight() - 100);
        controlsText.setColor(Color.white);
        render.add(controlsText);

      // Add control information to the text
        String controlInfo =
            "Map Controls:\n" +
            "   Move: Arrow keys\n" +
            "   Open Chest: Z\n" +
            "   Open Pause Menu: Esc\n\n" +
            "Inventory Controls:\n" +
            "   Open Inventory: C\n" +
            "   Shuffle: Arrow Keys\n" +
            "   Select: Z\n" +
            "   Return to Map: Return Button\n\n" +
            "Combat Controls:\n" +
            "   Select: Arrow keys\n" +
            "   Confirm: Z\n" +
            "   Back: X";

        addMultilineText(controlsText, controlInfo);

      // Set up return button
        returnButton = createButton("Title", buttonX, buttonY);

      // Set up next button
        double nextButtonX = buttonX + buttonWidth + 20; 
        nextButton = createButton("Next", nextButtonX, buttonY);
    }
    
    
	private GButton createButton(String label, double x, double y) {
	    GButton button = new GButton(label, x, y, buttonWidth, buttonHeight);
	    button.addMouseListener(this);
	    button.setFillColor(Color.DARK_GRAY);
	    button.setColor(Color.WHITE);
	    render.add(button);
	    return button;
	}
    
    private void addMultilineText(GCompound compound, String text) {
        double lineHeight = 45; // Adjust as needed
        String[] lines = text.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            String lineText = lines[i].trim(); // Remove leading and trailing whitespaces

            // Skip empty lines
            if (lineText.isEmpty()) {
                continue;
            }

            // Check if the line is a heading
            if (lineText.endsWith(":")) {
                GLabel headingLabel = new GLabel(lineText, 0, i * lineHeight);
                headingLabel.setFont("SansSerif-35");
                headingLabel.setColor(Color.white);
                compound.add(headingLabel);
            } else {
                // Add bullet points for content under headings
                String indentedLine = "   • " + lineText;
                GLabel contentLabel = new GLabel(indentedLine, 0, i * lineHeight);
                contentLabel.setFont("SansSerif-28");
                contentLabel.setColor(Color.white);
                compound.add(contentLabel);
            }
        }
    }


    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered");
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.RED); // Change to red when hovering
        }
    }

    
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited");
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.DARK_GRAY); // Change back to the original color when not hovering
        }
    }
    
	@Override
	public void showContents() {
		for (GObject obj : render) {
			program.add(obj);
		}
	}

	@Override
	public void hideContents() {
		program.clear();
	}

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = program.getElementAt(e.getX(), e.getY());
        if (obj == returnButton) {
        	this.hideContents();
            program.switchToMenu(); // Switch back to the menu screen
        } else if (obj == nextButton) {
        	this.hideContents();
            program.switchToCharacterSelect(); // Switch to the character select screen
        }
    }
}
