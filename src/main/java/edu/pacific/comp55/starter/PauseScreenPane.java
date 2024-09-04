package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import acm.graphics.GObject;
import acm.graphics.GRect;

public class PauseScreenPane extends GraphicsPane implements MouseListener {
    private MainApplication program;
    private ArrayList<GObject> render = new ArrayList<GObject>();
    private GRect pauseScreenBackground;
    private GButton resumeButton;
    private GButton quitButton;

    private GraphicsPane prev;

    double buttonWidth = 150;
    double buttonHeight = 60;

    public PauseScreenPane(MainApplication app, GraphicsPane prev) {
        this.program = app;
        this.prev = prev;

      // Set up background image for pause screen
        pauseScreenBackground = new GRect(0, 0, program.getWidth(), program.getHeight());
        pauseScreenBackground.setFilled(true);
        pauseScreenBackground.setFillColor(new Color(0, 0, 0, 150));
        render.add(pauseScreenBackground);

      // Add Resume button
        double buttonX = (program.getWidth() - buttonWidth) / 2 - 100; 
        double buttonY = (program.getHeight() - buttonHeight) / 2 - 50;
        resumeButton = createButton("Resume", buttonX, buttonY);

      // Add Quit button
        double quitButtonY = buttonY;
        double quitButtonX = buttonX + buttonWidth + 50; 
        quitButton = createButton("Quit", quitButtonX, quitButtonY);
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
	public void showContents() {
		for (GObject obj : render) {
			program.add(obj);
		}
	}

	@Override
	public void hideContents() {
		for (GObject obj : render) {
			program.remove(obj);
		}
	}
	
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.RED); // Change to red when hovering
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.DARK_GRAY); // Change back to the original color when not hovering
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = program.getElementAt(e.getX(), e.getY());
        if (obj == resumeButton) {
        	program.setCurScreen(prev);
        	hideContents();
        } else if (obj == quitButton) {
    	    AudioPlayer.getInstance().stopSound(MainApplication.MUSIC_FOLDER, "InGameMusic.mp3");		
            program.switchToMenu();
        }
    }
}