package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import acm.graphics.GImage;
import acm.graphics.GObject;

//Create a CreditsScreen class
public class CreditScreen extends GraphicsPane implements MouseListener {
private MainApplication program;
private GParagraph creditsText;
private GButton returnButton;
private GImage creditsImage;
private double scrollSpeed = 2.0; // Adjust the scrolling speed as needed
private boolean scrolling = true;
private double initialTextY; // Store the initial Y position of the text
private AudioPlayer audioPlayer;




// Dimensions and animation will need to be adjusted and reconfigured for full screen mode

public CreditScreen(MainApplication app) {
   this.program = app;
// Initialize the AudioPlayer
   audioPlayer = AudioPlayer.getInstance();
   
 
  
   String credits = "Thank you for playing!\n\nDevelopers:\nAbdullah Tariq Choudhry\nShoji Shinkawa\nMatthew Kunkel\nKoby Naomi Izunaso.";
   
   creditsText = new GParagraph(credits, 0, 0);  
   creditsText.setFont("Sanserif-50");
   creditsText.setColor(Color.WHITE);

   double buttonWidth = 150;
   double buttonHeight = 60;
   double buttonX = (program.getWidth() - buttonWidth) / 2;
   double buttonY = program.getHeight() - 100;

   returnButton = new GButton("Title", buttonX, buttonY, buttonWidth, buttonHeight);
   returnButton.setFillColor(Color.DARK_GRAY);
   returnButton.setColor(Color.WHITE);
   returnButton.addMouseListener(this);
   
   creditsImage = new GImage("sounds/CreditsScreen.gif", 0, 0);
   // Scale the image to the screen size while maintaining aspect ratio
   double scaleX = program.getWidth() / creditsImage.getWidth();
   double scaleY = program.getHeight() / creditsImage.getHeight();
   creditsImage.scale(scaleX, scaleY);
   
   // Calculate the position to center the text on the image
   double textX = (creditsImage.getWidth() - creditsText.getWidth()) / 2 + creditsImage.getX();
   double textY = (creditsImage.getHeight() - creditsText.getHeight()) / 2 + creditsImage.getY();


   // Set the location of the text
   creditsText.setLocation(textX, textY);
   
}


@Override
public void showContents() {
	scrolling = true;
    // Play the credits music
    //audioPlayer.playSound(MainApplication.MUSIC_FOLDER, "TitleScreen_Music.mp3");


    // Reset text position to the top-center of the image
    double textX = (creditsImage.getWidth() - creditsText.getWidth()) / 2 + creditsImage.getX();
    double textY = creditsImage.getY();
    creditsText.setLocation(textX, textY);
    
    // Add image, text, and button
    program.add(creditsImage);
    program.add(creditsText);
    program.add(returnButton);

    // Start the scrolling animation
    new Thread(this::scrollText).start();
}



	@Override
	public void hideContents() {
	    audioPlayer.stopSound(MainApplication.MUSIC_FOLDER, "CreditsMusic.mp3");

        program.remove(creditsImage);
		program.remove(creditsText);
        program.remove(returnButton);
        // Stop scrolling when hiding the contents
        scrolling = false;
        program.clear();
	}
	
	private void scrollText() {
	    while (scrolling) {
	        // Update the Y position of the text
	        creditsText.move(0, scrollSpeed);

	        try {
	            // Pause the thread for a short duration (adjust as needed)
	            Thread.sleep(20);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        // Check if the text has scrolled off the screen
	        if (creditsText.getY() > program.getHeight()) {
	            // Reset the text position to the top
	            creditsText.setLocation(program.getWidth() / 2 - creditsText.getWidth() / 2, initialTextY);
	        }
	    }
	}


    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = program.getElementAt(e.getX(), e.getY());

        if (obj == returnButton) {
        	// Stop scrolling and switch back to the title screen
            //scrolling = false;
        	// Stop credits music when hiding the contents
            audioPlayer.stopSound(MainApplication.MUSIC_FOLDER, "TitleScreen_Music.mp3");
            program.switchToMenu(); // Switch back to the title screen
        }
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
}