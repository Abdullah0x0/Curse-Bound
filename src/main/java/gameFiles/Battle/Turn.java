package gameFiles.Battle;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.GButton;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.MainApplication;
import javafx.event.ActionEvent;

public class Turn extends GraphicsPane {
	private boolean alive;
	private MainApplication program;
	private BattlePane last;
	private MainCharacter mc;
	private Battle battle;
	AudioPlayer audio = AudioPlayer.getInstance();
	
	private Character target;
	private Attack attack;
	private Curse curse;
	private boolean ran = false;
	private boolean inventory = false;
	
	private ArrayList<GObject> hoverable = new ArrayList<GObject>();
	private int hovering;
	ArrayList<GObject> itemLabels = new ArrayList<GObject>();
	
	private GImage playerInterface;
	private GLabel select;
	private GImage button1;
	private GImage button2;
	private GImage button3;
	private GImage button4;
	private GButton attack1;
	private GButton attack2;
	private GButton attack3;
	private GButton attack4;
	private GButton curse1;
	private GButton curse2;
	private GButton curse3;
	private GButton curse4;
	
	String playerOn = "nan";
	String playerLast = "nan";
	
	public Turn(MainApplication program, BattlePane last, Battle battle) {
		this.program = program;
		this.mc = battle.getMainCharacter();
		this.battle = battle;
		this.last = last; 
		
		select = new GLabel(">");
		select.setFont("Arial-40");
		select.setColor(Color.WHITE);
	}
	
	public void openInventory() {
		BattleInventory inventory = new BattleInventory(program, this, mc);
		
		CompletableFuture<Void> inventoryFuture = CompletableFuture.runAsync(() -> {
			System.out.println("Created inventory");
			inventory.showContents(); 
			program.setCurScreen(inventory);
		});
		
		inventoryFuture.join();
	}
	
	public boolean getInventory() {
		return inventory;
	}
	
	public Character getTarget() {
		return target;
	}

	public Attack getAttack() {
		return attack;
	}

	public Curse getCurse() {
		return curse;
	}

	public boolean isRan() {
		return ran;
	}

	public void setRan(boolean ran) {
		this.ran = ran;
	}

	@Override
	public void showContents() {
		alive = true;
		System.out.println("Showed contents - Turn.java");
		
		//start looping here
			//passive turn updates
		new Thread(this::gameActions).start();
			//begin flow here
		Thread beginTurn = new Thread(this::showPlayerTurn);
		beginTurn.start();
		
		try {
			beginTurn.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		showMenuButtons();
		program.add(select);
	}
	
	@Override
	public void hideContents() {
		program.remove(select);
		program.remove(playerInterface);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void resetHovering() {
		hoverable.clear();
		hovering = 0;
	}
	
	private void gameActions() {
		while (alive) {
			
			if (hoverable.size() == 0) {
				select.setLocation(-999, -999);
			} else {
				select.setLocation(hoverable.get(hovering).getX() - 20, hoverable.get(hovering).getY() + (hoverable.get(hovering).getHeight() / 2));
			}
			
			try {
	            // Pause the thread for a short duration (adjust as needed)
	            Thread.sleep(20);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			
			//boolean checks
			// -----------------
		}
	}
	
	public void showPlayerTurn() {		
		playerInterface = new GImage("media/playerInterface.png", 0, program.getHeight() - 44);
		playerInterface.setSize((program.getWidth() / 3) * 2, 0);
		program.add(playerInterface);
		
		while(playerInterface.getHeight() < program.getHeight() / 5) {
			playerInterface.setLocation(playerInterface.getX(), playerInterface.getY() - 3);
			playerInterface.setSize(playerInterface.getWidth(), playerInterface.getHeight() + 3);
			
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void hidePlayerTurn() {
		program.remove(playerInterface);
	}
	
	public void showMenuButtons() {
		playerOn = "menu";
		resetHovering();
		
		/// SET VISIBLE DOES NOT MEAN NON INTERACTABLE!!!!
		button1 = new GImage(
				"media/attackButton.png", 
				playerInterface.getX() + 10, 
				playerInterface.getY() + 10
		);
		
		button1.setSize((playerInterface.getWidth() / 4) - 10, 
				playerInterface.getHeight() - 20);
		button1.addActionListener(program);
		
		
		button2 = new GImage(
				"media/curseButton.png", 
				playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 1, 
				playerInterface.getY() + 10
		);
		button2.setSize(
				(playerInterface.getWidth() / 4) - 10, 
				playerInterface.getHeight() - 20);
		button2.addActionListener(program);
		
		
		button3 = new GImage(
				"media/itemButton.png", 
				playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 2, 
				playerInterface.getY() + 10
		);
		button3.setSize(
				(playerInterface.getWidth() / 4) - 10, 
				playerInterface.getHeight() - 20);
		button3.addActionListener(program);
		
		
		button4 = new GImage(
				"media/runButton.png", 
				playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 3, 
				playerInterface.getY() + 10 
		);
		button4.setSize((playerInterface.getWidth() / 4) - 20, playerInterface.getHeight() - 20);
		button4.addActionListener(program);
		
		program.add(button4);
		program.add(button3);
		program.add(button2);
		program.add(button1);
		hoverable.add(button1);
		hoverable.add(button2);
		hoverable.add(button3);
		hoverable.add(button4);
	}
	
	public void hideMenuButtons() {
		resetHovering();
		program.remove(button1);
		program.remove(button2);
		program.remove(button3);
		program.remove(button4);
	}
	
	public void showAttackButtons() {
		resetHovering();
		playerOn = "attack";
		
		if (mc.getAttack(0) != null) {
			attack1 = new GButton(
					mc.getAttack(0).getName(), 
					playerInterface.getX() + 10, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/attackMenuButton.png"
			);
			program.add(attack1);
			hoverable.add(attack1);
		}
		
		if (mc.getAttack(1) != null) {
			attack2 = new GButton(
					mc.getAttack(1).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 1, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/attackMenuButton.png"
			);
			program.add(attack2);
			hoverable.add(attack2);
		}
		
		if (mc.getAttack(2) != null) {
			attack3 = new GButton(
					mc.getAttack(2).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 2, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/attackMenuButton.png"
			);
			program.add(attack3);
			hoverable.add(attack3);
		}
		
		if (mc.getAttack(3) != null) {
			attack4 = new GButton(
					mc.getAttack(3).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 3, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 20, 
					playerInterface.getHeight() - 10,
					"media/attackMenuButton.png"
			);
			program.add(attack4);
			hoverable.add(attack4);
		}
	}
	
	public void hideAttackButtons() {
		resetHovering();
		switch (mc.getAttacks().size()) {
		case 1:
			program.remove(attack1);
			break;
		case 2:
			program.remove(attack1);
			program.remove(attack2);
			break;
		case 3:
			program.remove(attack1);
			program.remove(attack2);
			program.remove(attack3);
			break;
		case 4:
			program.remove(attack1);
			program.remove(attack2);
			program.remove(attack3);
			program.remove(attack4);
			break;
		}
	}
	
	public void showCurseButtons() {
		resetHovering();
		playerOn = "curse";

		if (mc.getCurse(0) != null) {
			curse1 = new GButton(
					mc.getCurse(0).getName(), 
					playerInterface.getX() + 10, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/curseMenuButton.png"
			);
			
			program.add(curse1);
			hoverable.add(curse1);
		}
		
		if (mc.getCurse(1) != null) {
			curse2 = new GButton(
					mc.getCurse(1).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 1, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/curseMenuButton.png"
			);
			program.add(curse2);
			hoverable.add(curse2);
		}
		
		if (mc.getCurse(2) != null) {
			curse3 = new GButton(
					mc.getCurse(2).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 2, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 10, 
					playerInterface.getHeight() - 10,
					"media/curseMenuButton.png"
			);
			program.add(curse3);
			hoverable.add(curse3);
		}
		
		if (mc.getCurse(3) != null) {
			curse4 = new GButton(
					mc.getCurse(3).getName(), 
					playerInterface.getX() + 10 + (playerInterface.getWidth() / 4) * 3, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() / 4) - 20, 
					playerInterface.getHeight() - 10,
					"media/curseMenuButton.png"
			);
			program.add(curse4);
			hoverable.add(curse4);
		}
	}
	
	public void hideCurseButtons() {
		resetHovering();
		
		switch (mc.getCurses().size()) {
		case 1:
			program.remove(curse1);
			break;
		case 2:
			program.remove(curse1);
			program.remove(curse2);
			break;
		case 3:
			program.remove(curse1);
			program.remove(curse2);
			program.remove(curse3);
			break;
		case 4:
			program.remove(curse1);
			program.remove(curse2);
			program.remove(curse3);
			program.remove(curse4);
			break;
		}
	}
	
	public void showItemButtons() {
		openInventory();
	}
	
	public void showTargetButtons(boolean type) {
		playerOn = "target";
		
		int buttonScale = battle.getEnemies().size();
		ArrayList<Character> enemies = battle.getEnemies();
		GButton temp = null;
		resetHovering();
		
		for (int i = 0; i < enemies.size(); i++) {
			temp = new GButton(
					enemies.get(i).getName(),
					(playerInterface.getX() + 20) + ((playerInterface.getWidth() - 20) / buttonScale) * i, 
					playerInterface.getY() + 10, 
					(playerInterface.getWidth() - 60) / buttonScale,
					playerInterface.getHeight() - 80,
					"media/targetButton.png"
				);
			hoverable.add(temp);
			itemLabels.add(temp);
			program.add(temp);
		}
		
		System.out.println("Showed Targets");
	}
	
	public void hideTargetButtons() {
		playerOn = null;
		playerLast = null;
		resetHovering();
		
		System.out.println(itemLabels.toString());
		
		for (int i = 0; i < itemLabels.size(); i++) {
			program.remove(itemLabels.get(i));
			System.out.println(itemLabels.toString());
		}
		
		for (int i = 0; i < itemLabels.size() - 1; i++) {
			itemLabels.remove(itemLabels.get(i));
			System.out.println(itemLabels.toString());
		}
		
		System.out.println(itemLabels.toString());
		System.out.println("All hidden!");
	}
	
	public void exit() {
		System.out.println("Exiting...");
		System.out.println("Attack: " + attack);
		System.out.println("Curse: " + curse);
		System.out.println("Target: " + target);
		hideContents();
		program.setCurScreen(last);
		alive = false;
	}
	
	public void back() {
		switch (playerOn) {
		case "attack":
			hideAttackButtons();
			resetHovering();
			showMenuButtons();
			attack = null;
			break;
		case "curse":
			hideCurseButtons();
			resetHovering();
			showMenuButtons();
			curse = null;
			break;
		case "menu":
			break;
		case "target":
			hideTargetButtons();
			resetHovering();
			showMenuButtons();
			target = null;
			attack = null;
			curse = null;
			break;
		default:
			break;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		System.out.println("Hoverable: " + hoverable.get(hovering));
		
		switch (keyCode) {
		case KeyEvent.VK_X:
			audio.playSound("sounds", "back.mp3");
			back();
			break;
		case KeyEvent.VK_Z:
			GObject obj = hoverable.get(hovering);
			audio.playSound("sounds", "button.mp3");
			System.out.println("Attack: " + attack);
			System.out.println("Curse: " + curse);
			System.out.println("Target: " + target);
			
			if (obj == button1) {
				hideMenuButtons();
				showAttackButtons();
			}
			
			if (obj == button2) {
				hideMenuButtons();
				showCurseButtons();
			} 
			
			if (obj == button3) {
				showItemButtons();
			} 
			
			if (obj == button4) {
				hideMenuButtons();
				setRan(true);
				exit();
			} 
			
			if (obj == attack1) {
				attack = mc.getAttack(0);
				hideAttackButtons();
				showTargetButtons(true);
			} 
			
			if (obj == attack2) {
				attack = mc.getAttack(1);
				hideAttackButtons();
				showTargetButtons(true);
			} 
			
			if (obj == attack3) {
				attack = mc.getAttack(2);
				hideAttackButtons();
				showTargetButtons(true);
			} 
			
			if (obj == attack4) {
				attack = mc.getAttack(3);
				hideAttackButtons();
				showTargetButtons(true);
			} 
			
			if (obj == curse1) {
				curse = mc.getCurse(0);
				hideCurseButtons();
				showTargetButtons(false);
			} 
			
			if (obj == curse2) {
				curse = mc.getCurse(1);
				hideCurseButtons();
				showTargetButtons(false);
			} 
			
			if (obj == curse3) {
				curse = mc.getCurse(2);
				hideCurseButtons();
				showTargetButtons(false);
			} 
			
			if (obj == curse4) {
				curse = mc.getCurse(3);
				hideCurseButtons();
				showTargetButtons(false);
			}
			
			ArrayList<Character> enemies = battle.getEnemies();
			//for all enemy buttons
			for (int i = 0; i < itemLabels.size(); i++) {
				//if enemy button is equal to the one we are hovering
				if (obj == itemLabels.get(i)) {
					//find the matching enemy and send that to target
					System.out.println(enemies.get(i).getName());
					target = enemies.get(i);
					
					//pass target out
					hideTargetButtons();
					exit();
				}
			}
			break;
			
		case KeyEvent.VK_RIGHT:
			audio.playSound("sounds", "navigate.mp3");
			hovering++;
			if (hovering > hoverable.size() - 1) {
				hovering = 0;
			}
			break;
			
		case KeyEvent.VK_LEFT:
			audio.playSound("sounds", "navigate.mp3");
			hovering--;
			if (hovering < 0) {
				hovering = hoverable.size() - 1;
			}
			break;
			
		case KeyEvent.VK_UP:
			System.out.println("Up key down");
			break;
			
		case KeyEvent.VK_DOWN:
			break;
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		
	}
}
