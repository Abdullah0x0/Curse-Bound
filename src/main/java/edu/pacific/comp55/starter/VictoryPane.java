package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import gameFiles.Map.MapGraphics;
import gameFiles.Map.TileType;

public class VictoryPane extends GraphicsPane implements MouseListener {
    private MainApplication program;
	private ArrayList<GObject> render = new ArrayList<GObject>();
    private GLabel victoryTitle;
    private GLabel victoryTitleShadow;
    private GLabel xpLabel;
    private GButton resumeButton;
    private GRect backgroundRect;
    private MapGraphics prev;

    private static final String VICTORY_TITLE_FONT = "Chiller-350";
    private static final String XP_LABEL_FONT = "Arial-40";
    


    public VictoryPane(MainApplication app, MapGraphics prev) {
        this.program = app;
        this.prev = prev;
        
      // Set up background color
        backgroundRect = new GRect(0, 0, program.getWidth(), program.getHeight());
        backgroundRect.setFillColor(Color.BLACK);
        backgroundRect.setFilled(true);
        render.add(backgroundRect);

      // Set up resume button at the bottom center
        double buttonWidth = 150;
        double buttonHeight = 60;
        double buttonX = (program.getWidth() - buttonWidth) / 2;
        double buttonY = program.getHeight() - buttonHeight - 50;

        resumeButton = new GButton("Continue", buttonX, buttonY - 100, buttonWidth, buttonHeight);
        resumeButton.addMouseListener(this);
        resumeButton.setFillColor(Color.DARK_GRAY);
        resumeButton.setColor(Color.WHITE);
        render.add(resumeButton);

      // Create victory title, victory label, and xp label.
        double victoryTitleX = program.getWidth() / 2; // Initialize before using width
        double victoryTitleY = program.getHeight() / 4;
        double shadowOffset = 2;

        victoryTitle = createLabel("Victory!", VICTORY_TITLE_FONT, Color.RED, victoryTitleX, victoryTitleY);
        victoryTitleShadow = createLabel("Victory!", VICTORY_TITLE_FONT, Color.WHITE, victoryTitleX + shadowOffset, victoryTitleY + shadowOffset);

        program.mc.giveExp(1000); // Xp boost given on winning battle.

        double xpLabelX = program.getWidth() / 2;
        double xpLabelY = victoryTitleY + victoryTitle.getHeight() + shadowOffset; 
        xpLabel = createLabel("1000+ Experience Points Gained! Level Up! Level:" + program.mc.getLevel(), XP_LABEL_FONT, Color.YELLOW, xpLabelX, xpLabelY);
    }
    
    private GLabel createLabel(String text, String font, Color color, double x, double y) {
        GLabel label = new GLabel(text);
        label.setFont(font);
        label.setColor(color);
        label.setLocation(x - label.getWidth() / 2, y); // Centering the label
        render.add(label);
        return label;
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
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.RED);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof GButton) {
            GButton sourceButton = (GButton) e.getSource();
            sourceButton.setFillColor(Color.DARK_GRAY);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = program.getElementAt(e.getX(), e.getY());
        if (obj == resumeButton) {
        	// Boss battle encounter win takes to credits screen
        	// Regular battle encounter win returns back to map.
            if (program.encounterCount == 15) { 
            	program.switchToCreditsScreen();
            } else {
            	prev.map.replace(prev.startI, prev.startJ, TileType.STONE);
            	program.switchToMap(prev);
            }
        }
    }
}
