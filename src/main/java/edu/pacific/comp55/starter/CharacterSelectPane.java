package edu.pacific.comp55.starter;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import gameFiles.Battle.MainCharacter;
import gameFiles.Inventory.Items;
import gameFiles.Inventory.Weapon;
import gameFiles.Map.MapGraphics;

class CharacterSelectPane extends GraphicsPane implements MouseListener {
    private MainApplication program;
    private ArrayList<GObject> render = new ArrayList<GObject>();
    private GImage characterSelectScreen;
    private GButton returnButton;
    private GButton startGameButton;
    private GButton switchCharButton; 
    private GLabel charSelectTitle;
    private GLabel characterNameLabel;
    private GLabel characterDesLabel;
    private int currentCharacterIndex; // Index to keep track of the current character
    private GImage[] characterImages; // Array to store character images
    private GLabel strengthLabel;
    private GLabel maxHpLabel;
    private GLabel maxManaLabel;
    private GLabel startingCurse;
    private GLabel startingWeaponLabel;
    private AudioPlayer audioPlayer;
    private GLabel[] characterDescriptionLines;
    private double descriptionX; 
    private double descriptionY; 
    private GLabel difficultyLabel; 

    public CharacterSelectPane(MainApplication app) {
        this.program = app;
        // Initialize the AudioPlayer
        audioPlayer = AudioPlayer.getInstance();

        // Set up background image for character select screen
        characterSelectScreen = new GImage("sounds/SebastianScreen.gif", 0, 0);
        characterSelectScreen.setSize(program.getWidth(), program.getHeight());
        
        // Set up character images for character select screen
        characterImages = new GImage[3];
        characterImages[0] = new GImage("sounds/Dziallas.jpg");
        characterImages[1] = new GImage("sounds/knight1.gif");
        characterImages[2] = new GImage("sounds/Holy Knight 2.gif");

        // Initialize the current character index
        currentCharacterIndex = 0;

        // Set the desired size for all character images
        double targetWidth = 450;  
        double targetHeight = 560; 

        // Scale all images to the desired size
        for (GImage characterImage : characterImages) {
            characterImage.setSize(targetWidth, targetHeight);
        }

        // Calculate the position to center the first character image on the screen
        double imageX = (program.getWidth() - characterImages[0].getWidth()) / 2;
        double imageY = (program.getHeight() - characterImages[0].getHeight()) / 2;

        // Set the calculated position for the first character image
        characterImages[0].setLocation(imageX, imageY);
       
        // Set the calculated position for all character images
        for (int i = 0; i < characterImages.length; i++) {
            characterImages[i].setLocation(imageX, imageY);
        }
        
        // Set up the initial character name label
        String characterName = "Sebastian"; // Change this to the name of the initial character
        characterNameLabel = new GLabel(characterName);
        characterNameLabel.setColor(Color.WHITE);
        characterNameLabel.setFont("Chiller-125"); // Adjust the font size as needed
        double labelX = 50; // Adjust the horizontal position to the left side
        double labelY = program.getHeight() / 2 - characterNameLabel.getHeight() / 2; // Center vertically
        characterNameLabel.setLocation(labelX, labelY - 80);
        
                
        // Set up the initial character name label
        String characterDesTitle = "Description"; 
        characterDesLabel = new GLabel(characterDesTitle);
        characterDesLabel.setColor(Color.WHITE);
        characterDesLabel.setFont("Chiller-125"); // Adjust the font size as needed
        characterDesLabel.setLocation(labelX * 20 + 30, labelY - 80);
        
        // Set up "Controls" title at the top center
        charSelectTitle = new GLabel("Select Character", program.getWidth() / 2, 50);
        charSelectTitle.setFont("Chiller-168");
        charSelectTitle.setColor(Color.WHITE);
        charSelectTitle.move(-charSelectTitle.getWidth() / 2, 125);
        
        // Adjust the X-coordinate to move the title to the right
        double titleX = program.getWidth() / 2 - charSelectTitle.getWidth() / 2 + 30; // Adjust the value (e.g., +20) as needed

        // Move the title to the calculated position
        charSelectTitle.setLocation(titleX, 125);

        // Set up return button
        double buttonWidth = 150;
        double buttonHeight = 60;

        // Adjusted X-coordinate for the return button to be closer to the left edge
        double returnButtonX = 50;
        double buttonY = program.getHeight() - 100;

        returnButton = new GButton("Controls", returnButtonX, buttonY, buttonWidth, buttonHeight);
        returnButton.addMouseListener(this);
        returnButton.setFillColor(Color.DARK_GRAY);
        returnButton.setColor(Color.WHITE);

        // Set up start game button at the bottom right edge of the screen
        double startGameButtonX = program.getWidth() - buttonWidth - 50; // Adjusted X-coordinate for the start button
        startGameButton = new GButton("Start", startGameButtonX, buttonY, buttonWidth, buttonHeight);
        startGameButton.addMouseListener(this);
        startGameButton.setFillColor(Color.DARK_GRAY);
        startGameButton.setColor(Color.WHITE);
        
        
        // Set up select left button at the top of the GImage
        double selectLeftButtonX = imageX + targetWidth / 2 - buttonWidth / 2;
        double selectButtonY = imageY - 65; // Adjust the vertical position as needed
        switchCharButton = new GButton("Switch Character", selectLeftButtonX, selectButtonY, buttonWidth, buttonHeight);
        switchCharButton.addMouseListener(this);
        switchCharButton.setFillColor(Color.DARK_GRAY);
        switchCharButton.setColor(Color.WHITE);
        
        
        // Initialize stat labels
        strengthLabel = new GLabel("Strength: " + getStrength(currentCharacterIndex));
        maxHpLabel = new GLabel("Max HP: " + getMaxHp(currentCharacterIndex));
        maxManaLabel = new GLabel("Max Mana: " + getMaxMana(currentCharacterIndex));
        startingWeaponLabel = new GLabel("Starting Weapon: " + getStartingWeapon(currentCharacterIndex));
        startingCurse = new GLabel("Curse: " + getCurse(currentCharacterIndex));


        // Set label properties
        labelX = 250; // Adjust the horizontal position to the left side
        setupStatLabel(strengthLabel, labelX, labelY + 30);
        setupStatLabel(maxHpLabel, labelX, labelY + 60);
        setupStatLabel(maxManaLabel, labelX, labelY + 90);
        setupStatLabel(startingWeaponLabel, labelX, labelY + 120);
        setupStatLabel(startingCurse, labelX, labelY + 150);

        
        // Initialize character description lines
        String characterDescription = getCharacterDescription(currentCharacterIndex);
        String[] descriptionLines = characterDescription.split("\n");
        characterDescriptionLines = new GLabel[descriptionLines.length];

        // Set initial values for descriptionX and descriptionY
        descriptionX = program.getWidth() / 2 + 260; // Adjust the horizontal position
        descriptionY = program.getHeight() / 2 - descriptionLines.length * 10 + 20; // Center vertically

        for (int i = 0; i < descriptionLines.length; i++) {
            characterDescriptionLines[i] = new GLabel(descriptionLines[i]);
            characterDescriptionLines[i].setColor(Color.WHITE);
            characterDescriptionLines[i].setFont("Arial-20"); // Adjust the font size as needed
            characterDescriptionLines[i].setLocation(descriptionX, descriptionY + i * 20); // Adjust vertical spacing
        }
        
        // Set up difficulty label
        difficultyLabel = new GLabel("Difficulty: Hard");
        difficultyLabel.setColor(Color.WHITE);
        difficultyLabel.setFont("Arial-20");
        difficultyLabel.setLocation(imageX + targetWidth / 2 - difficultyLabel.getWidth() / 2, imageY + targetHeight + 20); 
    }
    
    
    private void setupStatLabel(GLabel label, double x, double y) {
        label.setColor(Color.WHITE);
        label.setFont("Ariel-20"); 
        label.setLocation(x - 150, y);
    }
    
    @Override
    public void showContents() {
        program.add(characterSelectScreen);
        audioPlayer.playSound(MainApplication.MUSIC_FOLDER, "CharSelectMusic.mp3");
        program.add(charSelectTitle);
        program.add(startGameButton);
        program.add(returnButton);
        program.add(switchCharButton);
        program.add(characterImages[currentCharacterIndex]); 
        program.add(characterNameLabel); 
        program.add(characterDesLabel);
        // Add stat labels
        program.add(strengthLabel);
        program.add(maxHpLabel);
        program.add(maxManaLabel);
        program.add(startingWeaponLabel);
        program.add(startingCurse);
        program.add(difficultyLabel);
        // Add character description lines
        for (GLabel descriptionLine : characterDescriptionLines) {
            program.add(descriptionLine);
        }

    }

    @Override
    public void hideContents() {
        audioPlayer.stopSound(MainApplication.MUSIC_FOLDER, "CharSelectMusic.mp3");
        program.remove(characterSelectScreen);
        program.remove(charSelectTitle);
        program.remove(startGameButton);
        program.remove(returnButton);
        program.remove(switchCharButton);
        program.remove(characterImages[currentCharacterIndex]); 
        program.remove(characterNameLabel);
        program.remove(characterDesLabel);
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
        if (obj == returnButton) {
            program.switchToControlsScreen(); 
        } else if (obj == startGameButton) {
            //create character constructor
            MainCharacter mc = new MainCharacter(
            		getCharacterName(currentCharacterIndex), 
            		getMaxHp(currentCharacterIndex), 
            		getMaxMana(currentCharacterIndex), 
            		getStrength(currentCharacterIndex), 
            		getCor(currentCharacterIndex)
            );
            // 5 encounters before boss spawns
            program.encounterCount = 10; 
 
            giveAttacksAndCurses(mc, currentCharacterIndex);
            program.mc = mc;
            
            mc.giveLevel();
            mc.setHp(mc.getMaxHp());
            mc.setMp(mc.getMaxMp());
            MapGraphics map = new MapGraphics(program, null, mc, true);
            program.switchToMap(map); 
        } else if (obj == switchCharButton) {
            audioPlayer.playSound(MainApplication.MUSIC_FOLDER, "ButtonSound.mp3");
            changeCharacter();
        }
    }


    private void changeCharacter() {
        // Decrease the current character index
        currentCharacterIndex = (currentCharacterIndex - 1 + characterImages.length) % characterImages.length;
        updateCharacterImageAndLabel();
    }

    private void updateCharacterImageAndLabel() {
    	
        // Update the character image and label based on the current character index
        int previousIndex = (currentCharacterIndex - 1 + characterImages.length) % characterImages.length;
        program.remove(characterImages[previousIndex]);
        program.add(characterImages[currentCharacterIndex]);
        characterNameLabel.setLabel(getCharacterName(currentCharacterIndex));
        strengthLabel.setLabel("Strength: " + getStrength(currentCharacterIndex));
        maxHpLabel.setLabel("Max HP: " + getMaxHp(currentCharacterIndex));
        maxManaLabel.setLabel("Max Mana: " + getMaxMana(currentCharacterIndex));
        startingWeaponLabel.setLabel("Starting Weapon: " + getStartingWeapon(currentCharacterIndex));
        startingCurse.setLabel("Curse: " + getCurse(currentCharacterIndex));

        
        // Update character description lines
        String characterDescription = getCharacterDescription(currentCharacterIndex);
        String[] descriptionLines = characterDescription.split("\n");
        
        // Clear previous description lines
        for (GLabel oldDescriptionLine : characterDescriptionLines) {
            program.remove(oldDescriptionLine);
        }


        // Ensure that characterDescriptionLines array is initialized correctly
        if (characterDescriptionLines == null || characterDescriptionLines.length != descriptionLines.length) {
            characterDescriptionLines = new GLabel[descriptionLines.length];
        }

       
        // Update the labels with the new description lines
        for (int i = 0; i < descriptionLines.length; i++) {
            characterDescriptionLines[i] = new GLabel(descriptionLines[i]);
            characterDescriptionLines[i].setColor(Color.WHITE);
            characterDescriptionLines[i].setFont("Arial-20");
            characterDescriptionLines[i].setLocation(descriptionX, descriptionY + i * 20);
            program.add(characterDescriptionLines[i]);
        }
        
        // Update difficulty label
        difficultyLabel.setLabel("Difficulty: " + getDifficultyName(currentCharacterIndex));

        
        // Change background image based on the current character index
        String backgroundImagePath = getBackgroundImagePath(currentCharacterIndex);
        characterSelectScreen.setImage(backgroundImagePath);
        characterSelectScreen.setSize(program.getWidth(), program.getHeight());
    }

    private String getBackgroundImagePath(int index) {
        // Get the background image path based on the current character index
        switch (index) {
            case 0:
                return "sounds/SebastianScreen.gif"; 
            case 1:
                return "sounds/HolyKnightScreen.gif"; 
            case 2:
                return "sounds/GodlyKnightFinal.gif"; 
            default:
                return "";
        }
    }
    
    private void giveAttacksAndCurses(MainCharacter mc, int index) {
    	Items itemDB = new Items();
    	switch (index) {
    	case 0:
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(0));
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(11));
    		mc.setEquipped((Weapon) itemDB.getItem(41));
    		mc.giveAttack("021");
    		mc.giveAttack("022");
    		mc.giveCurse("010");
    		mc.giveCurse("014");
    		break;
    	case 1:
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(3));
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(15));
    		mc.setEquipped((Weapon) itemDB.getItem(21));
    		mc.giveAttack("002");
    		mc.giveAttack("015");
    		mc.giveAttack("019");
    		
    		mc.giveCurse("002");
    		mc.giveCurse("004");
    		mc.giveCurse("012");
    		break;
    	case 2:
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(10));
    		mc.getMainInventory().getMyInventory().add(itemDB.getItem(20));
    		mc.setEquipped((Weapon) itemDB.getItem(26));
    		mc.giveAttack("015");
    		mc.giveAttack("020");
    		mc.giveAttack("019");
    		mc.giveAttack("009");
    		mc.giveCurse("010");
    		mc.giveCurse("015");
    		mc.giveCurse("018");
    		mc.giveCurse("019");
    		break;
    	default:
    		break;
    	}
    }
    
    private String getDifficultyName(int index) {
        switch (index) {
            case 0:
                return "Hard";
            case 1:
                return "Normal";
            case 2:
                return "Easy";
            default:
                return "";
        }
    }
    
    
    private String getCharacterDescription(int index) {
        // Get the character description based on the current character index
        switch (index) {
            case 0:
                return "Sebastian, a revered computer science professor,\npossesses a unique blend of technological prowess\nand arcane mastery.\r\n"
                		+ "\nKnown as the \"Code Sorcerer,\" he wields\nalgorithms like spells and commands the digital\nrealm with unparalleled precision.\r\n"
                		+ "\nSebastian's mind, a labyrinth of binary incantations,\nconceals the secrets of ancient coding arts,\nmaking him a formidable force on the battlefield.";
            case 1:
                return "The Holy Knight, shrouded in darkness, is a relentless\nguardian of the realm. His armor, forged in the heart\nof shadowy abysses, emanates an ominous aura.\n"
                		+ "\nBranded by celestial markings, the Holy Knight wields\na cursed blade, the \"Umbral Reckoner,\" which\ndevours the very essence of his foes.\n"
                		+ "\nLegends speak of his haunting presence, a silhouette\non the horizon, striking fear into the hearts of those\nwho dare cross his path.";
            case 2:
                return "The Godly Knight, an embodiment of unmatched\nstrength, strides through realms with divine authority."
                		+ "\n\nClad in armor forged from the heavens and wielding\nthe \"Ethereal Excalibur,\" his every step sends tremors\nthrough the earth."
                		+ "\n\nA living legend, the Godly Knight commands celestial\nforces, rendering him invincible in the face of mortal\nadversaries.";
            default:
                return "";
        }
    }


    private String getCharacterName(int index) {
        // Get the character name based on the current character index
        switch (index) {
            case 0:
                return "Sebastian";
            case 1:
                return "Holy Knight";
            case 2:
                return "Godly Knight";
            default:
                return "";
        }
    }
    
    private int getStrength(int index) {
        // Get the strength value based on the current character index
        switch (index) {
            case 0:
                return 5; // Sebastian Strength
            case 1:
                return 15;  // Holy Knight Strength
            case 2:
                return 25; // Godly Knight Strength
            default:
                return 0;
        }
    }

    private int getMaxHp(int index) {
        // Get the max HP value based on the current character index
        switch (index) {
            case 0:
                return 80; // Max Hp Sebastian
            case 1:
                return 160;  // Max Hp Holy Knight
            case 2:
                return 10000; // Max Hp Godly Knight
            default:
                return 0;
        }
    }

    private int getMaxMana(int index) {
        // Get the max mana value based on the current character index
        switch (index) {
            case 0:
                return 50; // Max Mp Sebastian
            case 1:
                return 120; // Max Mp Holy Knight
            case 2:
                return 1000; // Max Mp Godly Knight
            default:
                return 0;
        }
    }
    
    private int getCor(int index) {
    	switch (index) {
    		case 0:
    			return 5; // Sebastian
    		case 1:
    			return 6; // Holy Knight
    		case 2:
    			return 8; // Godly Knight
    		default:
    			return 0;
    	}
    }

    private String getStartingWeapon(int index) {
        // Get the starting weapon based on the current character index
        switch (index) {
            case 0:
                return "Computer";   // Sebastian
            case 1:
                return "Runeblade of Eldritch Flames";   // Holy Knight
            case 2:
                return "Celestial Lance";     // Godly Knight
            default:
                return "";
        }
    }
    
    private String getCurse(int index) {
        // Get the curse information based on the current character index
        switch (index) {
            case 0:
                return "Pyroclasm"; // Sebastian
            case 1:
                return "Baneful Banishment"; // Holy Knight
            case 2:
                return "Temporal Strike"; // Godly Knight
            default:
                return "";
        }
    }
}