package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class GameOverPane extends GraphicsPane implements MouseListener {
    private MainApplication program;
    private GButton quitButton;
    private GImage gameOverBackground;
    private AudioPlayer audioPlayer;


    public GameOverPane(MainApplication app) {
        this.program = app;
        audioPlayer = AudioPlayer.getInstance();

        // Set up background image for GameOver screen
        gameOverBackground = new GImage("sounds/GameOver3.gif");
        // Adjust the size of the image to fit the screen
        gameOverBackground.scale(program.getWidth() / gameOverBackground.getWidth(), program.getHeight() / gameOverBackground.getHeight());

        double buttonWidth = 150;
        double buttonHeight = 60;
                
        // Set up quit button at the bottom center
        double quitButtonX = (program.getWidth() - buttonWidth) / 2;
        double quitButtonY = program.getHeight() - buttonHeight - 50; // Adjust Y-coordinate for the bottom center

        quitButton = new GButton("Return to Title", quitButtonX, quitButtonY, buttonWidth, buttonHeight);
        quitButton.addMouseListener(this);
        quitButton.setFillColor(Color.DARK_GRAY);
        quitButton.setColor(Color.WHITE);
    }
    

    @Override
    public void showContents() {
        // Play the game over music
        audioPlayer.playSound(MainApplication.MUSIC_FOLDER, "TitleScreen_Music.mp3");
        program.add(gameOverBackground);
        program.add(quitButton);
    }


    @Override
    public void hideContents() {
	    AudioPlayer.getInstance().stopSound(MainApplication.MUSIC_FOLDER, "TitleScreen_Music.mp3");		
        program.remove(gameOverBackground);
        program.remove(quitButton);
        program.clear();
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
        if (obj == quitButton) {
        	// Stop game over music
            audioPlayer.stopSound(MainApplication.MUSIC_FOLDER, "GameOverMusic.mp3");
            program.switchToMenu();
        }
    }

}