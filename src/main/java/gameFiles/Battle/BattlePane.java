package gameFiles.Battle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import edu.pacific.comp55.starter.AudioPlayer;
import edu.pacific.comp55.starter.GraphicsPane;
import edu.pacific.comp55.starter.Interfaceable;
import edu.pacific.comp55.starter.MainApplication;
import gameFiles.Inventory.Weapon;
import gameFiles.Map.MapGraphics;
import gameFiles.Map.TileType;
import javafx.util.Pair;

public class BattlePane extends GraphicsPane implements Interfaceable, ActionListener, KeyListener{
	// ---------- important do not touch  //
	private MainApplication program;
	private MapGraphics prev;
	private ArrayList<GObject> render = new ArrayList<GObject>();
	// ---------------------------------- //
	// initialize GObjects here.. GObject myObject; etc.. //
	private GRect canvasColor;
	private GImage Background;
	private GRect topBar;
	private GImage healthBar;
	private GImage mpBar;
	private GRect barBacking;
	private GLabel hpText;
	private GLabel mpText;
	private GImage mcImage;
	private GImage temp; 
	private HashMap<Character, GImage> enemyGImages = new HashMap<Character, GImage>();
	
	// -------------------------------------------------- //
	// custom vars -------------------------------------- //
	private MainCharacter mc;
	private Battle battle;
	private Timer battleTimer = new Timer(25, this);
	// battle vars -------------------------------------- //
	private RandomGenerator rgen = new RandomGenerator();
	private boolean ran;
	
	public BattlePane(MainApplication program, MapGraphics prev, MainCharacter mc, Pair<Integer, Integer> coord) {
		super();
		this.program = program;
		this.prev = new MapGraphics(program, prev.map, mc, coord);
		this.mc = mc;
		System.out.println("Encounter num: " + program.encounterCount);
		program.encounterCount++;
		System.out.println("Encounter num: " + program.encounterCount);
		boolean isBoss = false;
		Enemy boss = null;
		//Platform.runLater(() -> playAnimation(fxPanel, null, 0, 0));
		if (program.encounterCount == 15) {
			boss = new Enemy("Eye of Cthulhu", 1000, 9999, 10, 10, 3, "databases/enemies/Boss", "The final boss.");
			boss.setPortrait("media/boss.png");
			battle = new Battle(mc, boss);
			isBoss = true;
		} else {
			battle = new Battle(mc);
		}
		// create GObjects here ----------------------------- //
		// myObject = new GObject(...);
		// render.add(myObject) ----- Allows our object to get auto rendered.
		// -------------------------------------------------- //
		
		canvasColor = new GRect(0, 0, program.getWidth(), program.getHeight());
		canvasColor.setFillColor(Color.black);
		canvasColor.setFilled(true);
		render.add(canvasColor);
		
		switch (rgen.nextInt(1, 3)) {
		case 1:
			Background = new GImage("media/Battle_arena_background.gif", 0, 0);
			Background.setSize(program.getWidth(), program.getHeight() - program.getHeight() / 5);
			render.add(Background);
			break;
		case 2:
			Background = new GImage("media/Battle_arena_2.gif", 0, 0);
			Background.setSize(program.getWidth(), program.getHeight() - program.getHeight() / 5);
			render.add(Background);
			break;
		case 3:
			Background = new GImage("media/Battle_arena_3.gif", 0, 0);
			Background.setSize(program.getWidth(), program.getHeight() - program.getHeight() / 5);
			render.add(Background);
		default:
			Background = new GImage("media/Battle_arena_2.gif", 0, 0);
			Background.setSize(program.getWidth(), program.getHeight() - program.getHeight() / 5);
			render.add(Background);
		}
		
		topBar = new GRect(0,0, program.getWidth(), program.getHeight() / 8);
		topBar.setFillColor(Color.black);
		topBar.setFilled(true);
		render.add(topBar);
		
		barBacking = new GRect(10, 10, 350, 100);
		barBacking.setFillColor(Color.black);
		barBacking.setFilled(true);
		render.add(barBacking);
		
		healthBar = new GImage("media/healthBar.png", 10, 10);
		healthBar.setSize(300 * healthBarScale(), 50);
		render.add(healthBar);
		
		mpBar = new GImage("media/manaBar.png", 10, 60);
		mpBar.setSize(300 * mpBarScale(), 50);
		render.add(mpBar);
		
		hpText = new GLabel("000", barBacking.getX() + barBacking.getWidth() - 45, healthBar.getY() + healthBar.getHeight() /2);
		hpText.setFont("Arial-25");
		hpText.setColor(Color.white);
		render.add(hpText);
		
		mpText = new GLabel("000", barBacking.getX() + barBacking.getWidth() - 45, mpBar.getY() + mpBar.getHeight() /2);
		mpText.setFont("Arial-25");
		mpText.setColor(Color.white);
		render.add(mpText);
		
		GRect enemyArea = new GRect(0 + barBacking.getHeight() + 100, 150, program.getWidth() - 400, (program.getHeight() / 4) + 200);
		enemyArea.setVisible(false);
		render.add(enemyArea);
		
		double enemyScale = enemyArea.getWidth() / battle.getCombatants().size();
		for (int i = 0; i < battle.getEnemies().size(); i++) {
			temp = new GImage(battle.getEnemies().get(i).getPortrait(), enemyArea.getX() + (enemyScale * i), enemyArea.getY());
			temp.setSize(enemyScale, enemyArea.getHeight());
			System.out.println(enemyScale);
			System.out.println(enemyArea.getHeight());
			System.out.println(temp.toString());
			enemyGImages.put(battle.getEnemies().get(i), temp);
			render.add(temp);
		}
		
		if (isBoss == true) {
			enemyGImages.get(boss).setLocation(enemyArea.getWidth() / 2, enemyArea.getY());
		}
		
		mcImage = new GImage("media/mcBack.png", program.getWidth() / 8, program.getHeight() / 2 - 100);
		mcImage.setSize(program.getWidth() / 3, program.getHeight() / 2);
		render.add(mcImage);
	}
	
	public float healthBarScale() {
		return (float)mc.getHp() / (float)mc.getMaxHp();
	}
	
	public float mpBarScale() {
		return (float)mc.getMp() / (float)mc.getMaxMp();
	}
	
	public void TextBox(String text) {
		System.out.println("created text box");
		
		CompletableFuture<Void> textBox = CompletableFuture.runAsync(() -> {
			TextBox box = new TextBox(program, this, text) ;
			box.showContents();
        	program.setCurScreen(box);
        
        	while (box.isAlive()) {
        		//do nothing
        		//System.out.println("Text box still open");
        		try {
        			Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		});
		
		textBox.join();
	}
	
	public void PlayerTurn() {
		Turn pTurn = new Turn(program, this, battle);
		
		CompletableFuture<Void> turn = CompletableFuture.runAsync(() -> {
			System.out.println("Created turn");
	        pTurn.showContents();
	        program.setCurScreen(pTurn);
        
        	while (pTurn.isAlive()) {
        		//do nothing
        		//System.out.println("Text box still open");
        		try {
        			Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		});
		
		turn.join();
		
		System.out.println("BattlePane: ");
		System.out.println("Attack: " + pTurn.getAttack());
		System.out.println("Curse: " + pTurn.getCurse());
		System.out.println("Target: " + pTurn.getTarget());
		
		if (pTurn.getAttack() != null) {
			checkAttack(mc, pTurn.getAttack(), pTurn.getTarget());
		} else if(pTurn.getCurse() != null) {
			checkCurse(mc, pTurn.getCurse(), pTurn.getTarget());
		} else {
			ran = pTurn.isRan();
		}
		
		System.out.println("Attack / Ran should've been executed");
	}
	
	@Override
	public void showContents() {
		for (GObject obj : render) {
			program.add(obj);
		}
		
		// ---- PANE STARTS HERE !!!! ---- //
		//these will run along side each other
			//handles small updates to gobjects
		battleTimer.start();
			//handles turns and flow
		new Thread(this::startBattle).start();
	}

	@Override
	public void hideContents() {
		program.clear();
		battleTimer.stop();	
	}
	
	public void startBattle() {
		// begin battle loop and set up turns.
		RandomGenerator rgen = new RandomGenerator();
		ArrayList<Integer> order = new ArrayList<Integer>();
		int tempInt;
		int randInt;
		for (int i = 0; i < battle.getCombatants().size(); i++) {
			order.add(i + 1);
		}
		
		System.out.println("Sorting Combatants...");
		for (Character iter : battle.getCombatants()) {
			randInt = rgen.nextInt(0, order.size() - 1);
			tempInt = order.get(randInt);
			iter.setTurnOrder(tempInt);
			order.remove(randInt);
			
			//System.out.println(iter.toString());
		}
		System.out.println("Combatants assigned turn..");
		
		
		System.out.println("Combatants sorting..");
		Collections.sort(battle.getCombatants());
		System.out.println("Sorting complete!");
		
		//all characters have been added and set with a turn;
		
		System.out.println("Preparing to loop turns");
		int currentTurn = 0;
		
		boolean win = false;
		while(battle.getMainCharacter().getHp() > 0 && battle.getEnemies().size() > 0) {
			System.out.println(battle.getCombatants());
			if (battle.getCombatants().get(currentTurn) == battle.getMainCharacter()) {
				//playerTurn = true;
				System.out.println("Player Turn");
				takeTurn(battle.getMainCharacter());
				System.out.println("BattlePane | Player Turn Over -- ");
			} else {
				//playerTurn = false;
				System.out.println(battle.getCombatants().get(currentTurn).getName() + " Turn");
				takeTurn(battle.getCombatants().get(currentTurn));
			}
			
			//check enemies health bar
			for (int i = 0; i < battle.getEnemies().size(); i++) {
				//get an enemy
				Character curEnemy = battle.getEnemies().get(i); 
				String curEnemyName = curEnemy.getName();
				
				//if they are dead
				if (curEnemy.getHp() <= 0) {
					//remove from the battle
					battle.getCombatants().remove(curEnemy);
					//remove their image
					program.remove(enemyGImages.get(curEnemy));
					//say they died
					TextBox(curEnemyName + " has been defeated!");
					
					//resort remaining enemies
					Collections.sort(battle.getCombatants());
				}
			}
			
			if (currentTurn >= battle.getCombatants().size() - 1) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}
			
			if (battle.getEnemies().size() == 0 && battle.getMainCharacter().getHp() > 0) {
				win = true;
				break;
			}
			
			if (battle.getMainCharacter().getHp() <= 0) {
				win = false;
				break;
			}
			
			if (ran == true) {
				TextBox(battle.getMainCharacter().getName() + " ran...");
				System.out.println("ran");
			    AudioPlayer.getInstance().stopSound(MainApplication.MUSIC_FOLDER, "BattleMusic.mp3");
			    prev.map.replace(prev.startI, prev.startJ, TileType.STONE);
				program.switchToMap(prev);
				return;
			}
		}
		
		if (win == true) {
			TextBox("You won!");
			System.out.println("You win");
		    AudioPlayer.getInstance().stopSound(MainApplication.MUSIC_FOLDER, "BattleMusic.mp3");		
			program.switchToGameWin(prev);
		} else {
			TextBox("You were vanquished..");
			System.out.println("Lost");
			this.hideContents();
		    AudioPlayer.getInstance().stopSound(MainApplication.MUSIC_FOLDER, "BattleMusic.mp3");		
			program.switchToGameOver();
		}
		
		// return to map and calculate yields
	}

	public void takeTurn(Character current) {
		if (current.getClass() == MainCharacter.class) {
			//INVOKE TURN HERE
			PlayerTurn();
		} else {
			//do thing for npc
			EnemyTurn(current);
		}
	}
	
	public void EnemyTurn(Character enemy) {
		boolean hasAttacks = true;
		boolean hasCurses = true;
		if (!(enemy.getAttacks().size() > 0)) {
			hasAttacks = false;
		}
		
		if (!(enemy.getCurses().size() > 0)) {
			hasCurses = false;
		}
		
		if (hasAttacks && hasCurses) {
			if (rgen.nextBoolean() == true) {
				//choose attack
				checkAttack(enemy, enemy.getAttack(rgen.nextInt(1, enemy.getAttacks().size() - 1)), (Character)mc);
			} else {
				//choose curse
				checkCurse(enemy, enemy.getCurse(rgen.nextInt(1, enemy.getAttacks().size() - 1)), (Character)mc);
			}
		} else if ((hasAttacks == true) && (hasCurses == false)) {
			checkAttack(enemy, enemy.getAttack(rgen.nextInt(1, enemy.getAttacks().size() - 1)), (Character)mc);
		} else if ((hasAttacks == false) && (hasCurses == true)) {
			checkCurse(enemy, enemy.getCurse(rgen.nextInt(1, enemy.getAttacks().size() - 1)), (Character)mc);
		} else {
			System.exit(0);
		}
		
	}
	
	public void computeAttack(Character attacker, Attack attack, Character target) {
		Weapon thisWeapon = attacker.getEquipped();
		RandomGenerator crit = new RandomGenerator();
		int critSwitch;
		int hp = target.getHp();	
		int damage;
		
		TextBox(attacker.getName() +  " used: " + attack.getName() + " against " + target.getName() + ".");
		
		if (thisWeapon == null) {
			damage = attacker.getStr() * attack.getDamageMultiplier();
			target.setHp(hp - damage);
			
			TextBox(String.valueOf(damage) + " damage dealt.");
			Shake(target);
		} else {
			damage = attacker.getStr() * attack.getDamageMultiplier() + thisWeapon.getItemDamage();
			critSwitch = crit.nextInt(1, 100);
			
			switch(critSwitch) {
			case 1:
				damage = 0;
				target.setHp(hp - damage);
				
				//also include animations here
				//play a sound here about missing ------------
				if (attacker.getClass() == Enemy.class) {
					TextBox("CRITICAL MISS!!! They miss and injure themself.");
					TextBox(String.valueOf(damage) + " damage dealt to themself.");
					Shake(target);
				} else {
					TextBox("CRITICAL MISS!!! You miss and injure yourself.");
					TextBox(String.valueOf(damage) + " damage dealt to yourself.");
					Shake(target);
				}
				break;
				
			case 100:
				damage *= 4;
				target.setHp(hp - damage);
				
				if (attacker.getClass() == Enemy.class) {
					TextBox("HYPER-CRITICAL ATTACK!!! Their attack goes wild!");
					TextBox(String.valueOf(damage) + " damage dealt!!");
					Shake(target);
				} else {
					TextBox("HYPER-CRITICAL ATTACK!!! Your attack goes wild!");
					TextBox(String.valueOf(damage) + " damage dealt!!");
					Shake(target);
				}
				break;
				
			default:
				if (critSwitch < thisWeapon.getCritChance()) {
					damage *= 2;
					target.setHp(hp - damage);
					
					TextBox("Critical Hit!");
					TextBox(String.valueOf(damage) + " damage dealt.");
					Shake(target);
				} else {
					TextBox(String.valueOf(damage) + " damage dealt.");
					Shake(target);
				}
				
				break;
			}
		}
	}
	
	public void computeCurse(Character attacker, Curse curse, Character character) {
		if (curse.getMpCost() > attacker.getMp()) {
			TextBox(attacker.getName() + " failed to cast spell... ");
		}
		int hp = character.getHp();
		int damage;
		damage = attacker.getCor() * curse.getCurseMultiplier();
		attacker.setMp(attacker.getMp() - curse.getMpCost());
		character.setHp(hp - damage);
		Shake(character);
		TextBox(attacker.getName() + " uses: " + curse.getName() + " on " + character.getName() + ".");
		TextBox(Integer.valueOf(damage) + " damage dealt. ( - " + curse.getMpCost() + " mp )");
	}
	
	public void checkAttack(Character mc, Attack atkSwitch, Character target) {
		Attacks tempAttacks = new Attacks();
		if (atkSwitch == tempAttacks.getAttack("999")) {
			System.out.println("No attack selectable");
		} else {
			switch (atkSwitch.getTarget()) {
			case "single":
				singleAttack(mc, atkSwitch, target);
				break;
			case "multi":
				multiAttack(mc, atkSwitch, target);
				break;
			case "all":
				allAttack(mc, atkSwitch, target);
				break;
			}
			
		}
	}
	

	public void checkCurse(Character mc, Curse curse, Character target) {
		Curses tempCurses = new Curses();
		if (curse == tempCurses.getCurse("999")) {
			System.out.println("No attack selectable");
		} else if (0 < mc.getMp() - curse.getMpCost()) {
			switch (curse.getTarget()) {
			case "single":
				singleCurse(mc, curse, target);
				break;
			case "multi":
				multiCurse(mc, curse, target);
				break;
			case "all":
				allCurse(mc, curse, target);
				break;
			}
		} else {
			System.out.println("Could not cast.. Not enough mana: ");
		}
	}

	private void allCurse(Character mc, Curse curse, Character target) {
		if (mc.getClass() != MainCharacter.class) {
			computeCurse(mc, curse, target);
		} else {
			for (Character iter : battle.getEnemies()) {
				computeCurse(mc, curse, iter);
			}
		}
	}

	private void multiCurse(Character mc, Curse curse, Character target) {
		if (target != null) {
			switch (curse.getName()) {
			case "Arcane Barrage":
				for (int i = 0; i < 4; i++) {
					computeCurse(mc, curse, target);
				}
				break;
			case "Telekinetic Barrage":
				for (int i = 0; i < 6; i++) {
					computeCurse(mc, curse, target);
				}
				break;
			}
		}
	}

	private void singleCurse(Character mc, Curse curse, Character target) {
		computeCurse(mc, curse, target);
	}
	
	private void allAttack(Character mc, Attack attack, Character target) {
		if (mc.getClass() != MainCharacter.class) {
			computeAttack(mc, attack, target);
		} else {
			for (Character iter : battle.getEnemies()) {
				computeAttack(mc, attack, iter);
			}
		}
	}

	public void multiAttack(Character mc, Attack attack, Character target) {
		if (target != null) {
			switch(attack.getName()) {
			case "Double Strike":
				computeAttack(mc, attack, target);
				computeAttack(mc, attack, target);
				break;
			case "Rapid Strikes":
				for (int i = 0; i < 4; i++) {
					computeAttack(mc, attack, target);
				}
				break;
			}
		} else {
			System.out.println("Invalid target..");
		}
	}
	
	public void Shake(Character character) {
		RandomGenerator rgen = new RandomGenerator();
		
		Thread shake = new Thread(() -> {
			GImage target = enemyGImages.get(character);
			if (target != null) {
				double prevX = target.getX();
				System.out.println("Shaking..");
				for (int i = 0; i < 100; i++) {
					target.setLocation(target.getX() + rgen.nextDouble(-20, 20), target.getY());
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				target.setLocation(prevX, target.getY());
			}
		});
		
		shake.start();
	}
	
	public void singleAttack(Character mc, Attack attack, Character target) {
		computeAttack(mc, attack, target);
	}
	
	public void actionPerformed(ActionEvent e) {
		//System.out.println("tick: ");
		//add position and movement checks
		healthBar.setSize(300 * healthBarScale(), healthBar.getHeight());
		mpBar.setSize(300 * mpBarScale(), mpBar.getHeight());
		hpText.setLabel(String.valueOf(mc.getHp()));
		mpText.setLabel(String.valueOf(mc.getMp()));
		
		
		
		//animate menu if open menu is true; else, try to hide
		
	}
}