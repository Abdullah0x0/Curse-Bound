package edu.pacific.comp55.starter;

import gameFiles.Battle.MainCharacter;
import gameFiles.Inventory.InventoryPane;
import gameFiles.Map.MapGraphics;
import javafx.embed.swing.JFXPanel;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1500;
	public static final int WINDOW_HEIGHT = 1080;
	public static final String MUSIC_FOLDER = "sounds";
	public JFXPanel fxPanel;

	// NOTE: Currently boss Spawns in 5 enemy encounters.
	public MainCharacter mc = new MainCharacter("Seb", 80, 50, 5, 5); // Default Char
	private int count;
	public int encounterCount = 0;
	public InventoryPane inventoryPane;
	
	
    public void init() {
        this.gw.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.gw.setTitle("Curse Bound");
    }

	public void run() {
		// ---------------------//
		// ----- Start of Game -//
		// ---------------------//
		System.out.println("Game Begin");		
		switchToPixelFoxScreen();	    
		setupInteractions();
		//To menu pane
		switchToMenu();
	}
	
    public void switchToPixelFoxScreen() {
        PixelFoxPane pixelFoxPane = new PixelFoxPane(this);
        switchToScreen(pixelFoxPane);
    }
	
	
	public void switchToMenu() {
		if (count == 0) { // First Game Launch
			AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "CharSelectMusic.mp3");		
			count++;
			switchToScreen(new MenuPane(this));
		}
		else {
			count++;
			AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
		    AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "CharSelectMusic.mp3");		
			switchToScreen(new MenuPane(this));	
		}
	}
	
	public void switchToControlsScreen() { // Start button
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");		
	    switchToScreen(new SomePane(this));
	}

	public void switchToCreditsScreen() { //Credits button
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "CharSelectMusic.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "TitleScreen_Music.mp3");
	    AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "CreditsMusic.mp3");		
	    switchToScreen(new CreditScreen(this));
	}
	
	public void switchToCharacterSelect() {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");		
	    switchToScreen(new CharacterSelectPane(this));
	}
	
	public void switchToBattle(GraphicsPane battle, GraphicsPane prev) {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "InGameMusic.mp3");	
	    AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "BattleMusic.mp3");	
	    //NOTE: Boss Spawns after 5 enemy encounters
		switchToScreen(battle);
	}

	public void switchToPause(GraphicsPane prev) {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "TitleScreen_Music.mp3");
		switchToScreen(new PauseScreenPane(this, prev));
	}
	
	public void switchToInventory(MapGraphics prev) {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
		switchToScreen(new InventoryPane(this, mc, prev));
	}
	
	public void switchToGameWin(MapGraphics prev) {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "CharSelectMusic.mp3");
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "Victory Sound Effect.mp3");		
		switchToScreen(new VictoryPane(this, prev));
	}
	
	public void switchToGameOver() {	
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
		AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "CharSelectMusic.mp3");
		switchToScreen(new GameOverPane(this));
	}
	
	public void switchToMap(MapGraphics mapPane) {
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    AudioPlayer.getInstance().stopSound(MUSIC_FOLDER, "TitleScreen_Music.mp3");	
	    AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "InGameMusic.mp3");	
	    switchToScreen(mapPane);
	}
	
	public void switchTo(GraphicsPane pane) {
		switchToScreen(pane);
	}

	public void stopExecution() { // Exit button
		AudioPlayer.getInstance().playSound(MUSIC_FOLDER, "ButtonSound.mp3");
	    System.exit(0);
	}

	public static void main(String[] args) {
		MainApplication myApplication = new MainApplication();
		myApplication.start();
	}
}
