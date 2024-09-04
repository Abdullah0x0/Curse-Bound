package gameFiles.Map;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.MainApplication;
import edu.pacific.comp55.starter.PauseScreenPane;
import gameFiles.Battle.BattlePane;
import gameFiles.Battle.MainCharacter;
import gameFiles.Inventory.Inventory;
import gameFiles.Inventory.Item;
import gameFiles.Inventory.Items;
import javafx.util.Pair;

public class MapGraphics extends GraphicsPane implements ActionListener {
	//program very important
	AudioPlayer audio = AudioPlayer.getInstance();
	public MainApplication program;
	public MainCharacter mc;
	public Map map;
	public Map above = null;
	public Map below = null;
	private boolean aboveBelow; //true if from above false otherwise
	Pair<Integer, Integer> coord;
	public int startI;
	public int startJ;
	
    private Timer mapTimer = new Timer(20, this);
    private GRect mapBG;
    private GRect tileArea;
    //temporary tile
    private GImage temp;
    private GImage player;
    
	public MapGraphics(MainApplication app, Map loadMap, MainCharacter mc, boolean aboveBelow) {
		this.program = app;
		this.mc = mc;
		this.aboveBelow = aboveBelow;
		this.coord = new Pair<Integer, Integer>(0, 0);
		this.startI = coord.getKey();
		this.startJ = coord.getValue();
		// generate map if map doesn't exist;
		if (loadMap == null) {
			map = new Map(10, 15);
		} else {
			map = loadMap;
		}
		
		System.out.println("Creating map..");
		
		// instatiate gobjects here
		mapBG = new GRect(0, 0, program.getWidth(), program.getHeight());
		mapBG.setFillColor(Color.BLACK);
		mapBG.setFilled(true);
		
		
		tileArea = new GRect(
				program.getWidth() / 10, 
				program.getHeight() / 8, 
				(program.getWidth() / 10) * 8,
				(program.getHeight() / 8) * 6
			);
		tileArea.setFillColor(Color.white);
		tileArea.setFilled(true);
		//tileArea.setVisible(false);
		
		player = new GImage("stone.png", 0, 0);
		player.setSize(cellWidth(), cellHeight());
	}
	
	public MapGraphics(MainApplication app, Map loadMap, MainCharacter mc, Pair<Integer, Integer> coord) {
		this.program = app;
		this.mc = mc;
		this.coord = coord;
		this.startI = coord.getKey();
		this.startJ = coord.getValue();
		// generate map if map doesn't exist;
		if (loadMap == null) {
			map = new Map(10, 15);
		} else {
			map = loadMap;
		}
		
		System.out.println("Creating map..");
		
		// instatiate gobjects here
		mapBG = new GRect(0, 0, program.getWidth(), program.getHeight());
		mapBG.setFillColor(Color.BLACK);
		mapBG.setFilled(true);
		
		
		tileArea = new GRect(
				program.getWidth() / 10, 
				program.getHeight() / 8, 
				(program.getWidth() / 10) * 8,
				(program.getHeight() / 8) * 6
			);
		tileArea.setVisible(false);
		
		player = new GImage("stone.png", 0, 0);
		player.setSize(cellWidth(), cellHeight());
	}
    
    @Override
	public void showContents() {
		program.add(mapBG);
		program.add(tileArea);
		spawnMap();
		startI = coord.getKey();
		startJ = coord.getValue();
		//load which character we have
		makeChar();
		//add to program
		if (startJ == 0 || startI == 0) {
			if (aboveBelow) {
				player.setLocation(tileArea.getX() + tileArea.getWidth() / 2, tileArea.getY() + tileArea.getHeight() - cellHeight());
				snapToGrid(player);
			} else {
				player.setLocation(tileArea.getX() + tileArea.getWidth() / 2, tileArea.getY());
				snapToGrid(player);
			}
		} else {
			player.setLocation((tileArea.getX() + startJ * cellWidth()), (tileArea.getY() + startI * cellHeight()));
			snapToGrid(player);
		}
		program.add(player);
		
		mapTimer.start();
	}
	
	@Override
    public void hideContents() {
    	program.clear();
    }
	
	public void spawnMap() {
		if (map == null) {
			return;
		}
		
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getCols(); j++) {
				if (map.getTile(i, j).getType() == TileType.STONE || map.getTile(i, j).getType() == TileType.GRASS) {
					temp = new GImage(map.getTile(i, j).getType().getPath(),
							tileArea.getX() + cellWidth() * j,
							tileArea.getY() + cellHeight() * i
						);
					temp.setSize(cellWidth(), cellHeight());
					program.add(temp);
				} else {
					temp = new GImage(TileType.STONE.getPath(),
							tileArea.getX() + cellWidth() * j,
							tileArea.getY() + cellHeight() * i
						);
					temp.setSize(cellWidth(), cellHeight());
					program.add(temp);
					
					temp = new GImage(map.getTile(i, j).getType().getPath(),
							tileArea.getX() + cellWidth() * j,
							tileArea.getY() + cellHeight() * i
						);
					temp.setSize(cellWidth(), cellHeight());
					program.add(temp);
				}
			}
		}
	}
	
	public double cellWidth() {
		return Math.ceil(tileArea.getWidth() / map.getCols());
	}
	
	public double cellHeight() {
		return Math.ceil(tileArea.getHeight() / map.getRows());
	}
	
	public void makeChar() {
		//program.mc
		System.out.println("Name: " + program.mc.getName());
		switch(program.mc.getName()) {
		case "Sebastian":
			player = new GImage("sebastian.gif", 0, 0);
			break;
		case "Holy Knight":
			player = new GImage("player.gif", 0, 0);//CHARACTER3
			break;
		case "Godly Knight":
			player = new GImage("player1.gif", 0, 0);//CHARACTER2
			break;
		default:
			System.out.println("Default Name: " + program.mc.getName());
			player = new GImage("sebastian.gif", 0, 0);
		}
		player.setSize(cellWidth(), cellHeight());
	}
	
	public void moveLeft(GObject obj) {
		obj.setLocation(obj.getX() - cellWidth(), obj.getY());
		snapToGrid(obj);
		audio.playSound("sounds", "move.mp3");
	}
	
	public void moveRight(GObject obj) {
		obj.setLocation(obj.getX() + cellWidth(), obj.getY());
		snapToGrid(obj);
		audio.playSound("sounds", "move.mp3");
	}
	
	public void moveUp(GObject obj) {
		obj.setLocation(obj.getX(), obj.getY() - cellWidth());
		snapToGrid(obj);
		audio.playSound("sounds", "move.mp3");
	} 
	
	public void moveDown(GObject obj) {
		obj.setLocation(obj.getX(), obj.getY() + cellWidth());
		snapToGrid(obj);
		audio.playSound("sounds", "move.mp3");
	}
	
	//from double to int
	public int getI(GObject obj) {
		double y = obj.getY();
		y = y - tileArea.getY();
		y /= cellHeight();
		return (int) Math.round(y);
	}
	
	//from double to int
	public int getJ(GObject obj) {
		double x = obj.getX();
		x = x - tileArea.getX();
		x /= cellWidth();
		return (int) Math.round(x);
	}
	
	public void snapToGrid(GObject obj) {
		obj.setLocation(tileArea.getX() + (getJ(obj) * cellWidth()), tileArea.getY() + (getI(obj) * cellHeight()));
	}
	
	public void openChest() {
		RandomGenerator rgen = new RandomGenerator();
		Items itemDB = new Items();
		Item award;
		Inventory playerInventory = program.mc.getMainInventory();
		System.out.println("Open chest");
		switch (rgen.nextInt(1, 3)) {
		case 1:
			award = itemDB.getItem(rgen.nextInt(0, 41));
			audio.playSound("sounds", "getItem.mp3");
			playerInventory.addItem(award);
			System.out.println(playerInventory.toString());
		case 2:
			for (int i = 0; i < 1; i++) {
				award = itemDB.getItem(rgen.nextInt(0, 41));
				audio.playSound("sounds", "getItem.mp3");
				playerInventory.addItem(award);
			}
		case 3:
			for (int i = 0; i < 2; i++) {
				award = itemDB.getItem(rgen.nextInt(0, 41));
				audio.playSound("sounds", "getItem.mp3");
				playerInventory.addItem(award);
			}
		}
	}
	
    public void keyPressed(KeyEvent e) {
    	Tile temp;
    	
	    int key = e.getKeyCode();
	    
	    if (key == KeyEvent.VK_Z) {
	    	if (!(getI(player) + 1 > map.getRows() - 1)) {
	    		if (map.getTile(getI(player) + 1, getJ(player)).getType() == TileType.CHEST) {
		    		map.replace(getI(player) + 1, getJ(player), TileType.STONE);
		    		openChest();
		    	}
	    	}
	    	
	    	if (!(getI(player) - 1 < 0)) {
	    		if (map.getTile(getI(player) - 1, getJ(player)).getType() == TileType.CHEST) {
		    		map.replace(getI(player) - 1, getJ(player), TileType.STONE);
		    		openChest();
		    	}
	    	}
	    	
	    	if (!(getJ(player) - 1 < 0)) {
	    		if (map.getTile(getI(player), getJ(player) - 1).getType() == TileType.CHEST) {
		    		map.replace(getI(player), getJ(player) - 1, TileType.STONE);
		    		openChest();
		    	}
	    	}
	    	
	    	if (!(getJ(player) + 1 > map.getRows() - 1)) {
	    		if (map.getTile(getI(player), getJ(player) + 1).getType() == TileType.CHEST) {
		    		map.replace(getI(player), getJ(player) + 1, TileType.STONE);
		    		openChest();
		    	}
	    	}
	    }

	    if (key == KeyEvent.VK_ESCAPE) {
	    	coord = new Pair<Integer, Integer>(getI(player), getJ(player));
	    	PauseScreenPane pause = new PauseScreenPane(program, this);
	    	pause.showContents();
	    	audio.playSound("sounds", "navigate.mp3");
	    	program.setCurScreen(pause);
	    }
	    
	    if (key == KeyEvent.VK_C) {
	    	coord = new Pair<Integer, Integer>(getI(player), getJ(player));
	    	program.switchToInventory(this);
	    	audio.playSound("sounds", "navigate.mp3");
	    }
	    
	    if (key == KeyEvent.VK_LEFT) {
	    	temp = map.getTile(getI(player), getJ(player) - 1);
	    	if (temp.getType() == TileType.STONE || temp.getType() == TileType.ENEMY) {
	    		moveLeft(player);
	    	}
	    }

	    if (key == KeyEvent.VK_RIGHT) {
	    	temp = map.getTile(getI(player), getJ(player) + 1);
	    	if (temp.getType() == TileType.STONE || temp.getType() == TileType.ENEMY) {
	    		moveRight(player);
	    	}
	    }

	    if (key == KeyEvent.VK_UP) {
	    	if (getI(player) - 1 < 0) {
	    		switchUp();
	    	}
	    	
	    	temp = map.getTile(getI(player) - 1, getJ(player));
	    	if (temp != null) {
	    		if (temp.getType() == TileType.STONE || temp.getType() == TileType.ENEMY) {
		    		moveUp(player);
		    	} 
	    	}
	    	
	    }

	    if (key == KeyEvent.VK_DOWN) {
	    	if (getI(player) + 1 > map.getRows() - 1) {
	    		switchDown();
	    	}
	    	
	    	temp = map.getTile(getI(player) + 1, getJ(player));
	    	if (temp != null) {
	    		if (temp.getType() == TileType.STONE || temp.getType() == TileType.ENEMY) {
		    		moveDown(player);
		    	}
	    	}
	    }
    }
    
    public void switchUp() {
    	System.out.println("I am leaving up..");
		mapTimer.stop();
		hideContents();
		//new pane in respect to this.above
		MapGraphics pane1 = new MapGraphics(program, this.above, mc, true);
		//tell the new pane that below is 
		pane1.below = map;
		//tell thispane that above is our current above is pane
		this.above = pane1.map;
		program.switchToMap(pane1);
		// switch maps
    }
    
    public void switchDown() {
    	//switch maps
		mapTimer.stop();
		hideContents();
		System.out.println("I am leaving down..");
		//create new pane from this mapgraphic's below
		MapGraphics pane2 = new MapGraphics(program, this.below, mc, false);
		//tell new pane that the map above this one is map
		pane2.above = map;
		//tell this.mapgraphics that our new pane is below
		this.below = pane2.map;
		//this mappane knows that: pane2 is below
		//pane2 knows that : above is this mappane
		program.switchToMap(pane2);
    }

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (getI(player) < 0 || getI(player) > map.getRows() || getJ(player) < 0 || getJ(player) > map.getCols()) {
			//fail checks
		} else {
			if (map.getTile(getI(player), getJ(player)).getType() == TileType.ENEMY) {
				mapTimer.stop();
				hideContents();
				Pair<Integer, Integer> coord = new Pair<Integer, Integer>(getI(player), getJ(player));
				BattlePane battle = new BattlePane(program, this, mc, coord);
				//program.encounterCount++;
				program.switchToBattle(battle, this);
			}
		}
		
	}
}

