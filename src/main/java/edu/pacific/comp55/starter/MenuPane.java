package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane implements MouseListener{
	private MainApplication program; 
	private ArrayList<GObject> render = new ArrayList<GObject>();
	private GButton playButton;
	private GButton creditsButton;
    private GButton exitButton;
	private GLabel title;
	private GLabel shadow;
    private GImage titleBackgroundImg;
    double buttonWidth = 150;
    double buttonHeight = 60;
    

    // shoji temp
    private GButton battleButton;
    // Abdullah temp
    private GButton pauseButton;
    private GButton gameOverButton;
    private GButton victoryButton;
    // Matthew temp
    private GButton mapButton;
    //Koby temp
    private GButton inventoryButton;

    
	public MenuPane(MainApplication app) {
		super();
		program = app; 
	  // Button Positioning:
	    double verticalSpacing = 10;
	    double buttonY = (program.getHeight() - buttonHeight) / 2 + 40;
	    double creditsY = buttonY + buttonHeight + verticalSpacing;
	    double exitY = creditsY + buttonHeight + verticalSpacing;
	    double titleY = buttonY - 150; // Adjusted position relative to the playButton
	    double buttonX = (program.getWidth() - buttonWidth) / 2;
        double creditsX = (program.getWidth() - buttonWidth) / 2;
        double exitX = (program.getWidth() - buttonWidth) / 2;

	  // Load the background image
	    titleBackgroundImg = new GImage("sounds/TS10.png");
	    render.add(titleBackgroundImg);
	  // Adjust/scale the size of the image to fit the screen
        titleBackgroundImg.scale(program.getWidth() / titleBackgroundImg.getWidth(), program.getHeight() / titleBackgroundImg.getHeight());
        
        
 
	   // Adding the title label

        String gameTitle = "CurseBound";
        title = new GLabel(gameTitle);
        title.setFont("Chiller-350"); 
        title.setColor(Color.RED);
        double titleX = (program.getWidth() - title.getWidth()) / 2 - 10;
        title.setLocation(titleX, titleY);
        render.add(title);
        
      // Adding a shadow label for title
        shadow = new GLabel(gameTitle);
        shadow.setFont("Chiller-350"); 
        shadow.setColor(Color.WHITE); 
        render.add(shadow);
        
      // Calculate and set the position for the shadow label
        double shadowX = title.getX() + 3; 
        double shadowY = title.getY() - 3; 
        title.setLocation((program.getWidth() - title.getWidth()) / 2, program.getHeight() / 2 - 150);
        shadow.setLocation(shadowX, shadowY);
        
      // Create Play, Credits, and Exit button
        playButton = createButton("Play", buttonX, buttonY);
        creditsButton = createButton("Credits", creditsX, creditsY);
        exitButton = createButton("Exit", exitX, exitY);
	}
	
	private GButton createButton(String label, double x, double y) {
	    GButton button = new GButton(label, x, y, buttonWidth, buttonHeight);
	    button.addMouseListener(this);
	    button.setFillColor(Color.DARK_GRAY);
	    button.setColor(Color.WHITE);
	    render.add(button);
	    return button;
	}

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered");
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.RED); // Change to red when hovering
        }
    }

    @Override
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
		System.out.println(obj.toString());
		// Switch to appropriate screen upon correct button press
	    if (obj == playButton) {
	        program.switchToControlsScreen();
	    } else if (obj == creditsButton) {
	        program.switchToCreditsScreen();
	    } else if (obj == exitButton) {
	        program.stopExecution();

	    }
	}
}
