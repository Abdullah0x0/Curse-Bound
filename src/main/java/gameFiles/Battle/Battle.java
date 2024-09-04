package gameFiles.Battle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Iterator;

import acm.util.RandomGenerator;
import gameFiles.Inventory.Inventory;
import gameFiles.Inventory.Item;
import gameFiles.Inventory.Items;
import gameFiles.Inventory.Weapon;

public class Battle {
	public ArrayList<Character> Combatants = new ArrayList<Character>();
	public EnemyDatabase eD;
	private MainCharacter mainCharacter;
	private boolean run = false;
	RandomGenerator enemyRgen = new RandomGenerator();
	
	public boolean getRun() {
		return run;
}
	
	public ArrayList<Character> getCombatants() {
		return Combatants;
	}
	
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}
	
	public ArrayList<Character> getEnemies() {
		ArrayList<Character> arrOut = new ArrayList<Character>();
		for (Character iter : Combatants) {
			if (iter != mainCharacter) {
				arrOut.add(iter);
			}
		}
		
		return arrOut;
	}
	
	public Battle(MainCharacter mainCharacter) {
		eD = new EnemyDatabase("databases/EnemyDatabase");
		this.mainCharacter = mainCharacter;
		Combatants.add(mainCharacter);
		
		for (int i = 0; i < enemyRgen.nextInt(1, 3); i++) {
			Combatants.add(eD.getRandomEnemy(enemyRgen.nextInt(1, 3)));
		}
		
	}
	
	public Battle(MainCharacter mainCharacter, Enemy boss) {
		this.mainCharacter = mainCharacter;
		Combatants.add(mainCharacter);
		Combatants.add(boss);
	}
	
	public void startBattle() {
		// begin battle loop and set up turns.
		RandomGenerator rgen = new RandomGenerator();
		ArrayList<Integer> order = new ArrayList<Integer>();
		int tempInt;
		int randInt;
		for (int i = 0; i < Combatants.size(); i++) {
			order.add(i + 1);
		}
		
		System.out.println("Sorting Combatants...");
		for (Character iter : Combatants) {
			randInt = rgen.nextInt(0, order.size() - 1);
			tempInt = order.get(randInt);
			iter.setTurnOrder(tempInt);
			order.remove(randInt);
			
			System.out.println(iter.toString());
		}
		System.out.println("Combatants assigned turn..");
		
		
		System.out.println("Combatants sorting..");
		Collections.sort(Combatants);
		System.out.println("Sorting complete!");
		
		for (Character iter : Combatants) {
			System.out.println(iter.toString());
		}
		
		//all characters have been added and set with a turn;
		
		System.out.println("Preparing to loop turns");
		int currentTurn = 0;
		
		while(mainCharacter.getHp() > 0 && Combatants.size() > 1) {
			
			System.out.println();
			System.out.println("| ------------- |");
			System.out.println("|   Combatants  |");
			System.out.println("| ------------- |");
			for (Character iter : Combatants) {
				System.out.println(iter.getName());
			}
			System.out.println("| ------------- |");
			System.out.println();
			
			
			takeTurn(Combatants.get(currentTurn));
			
			if (currentTurn == Combatants.size() - 1) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}
			
			//check combatants health bar
			
			for (int i = 0; i < Combatants.size(); i++) {
				if (Combatants.get(i).getHp() <= 0) {
					Combatants.remove(Combatants.get(i));
				}
			}
			
			if (run == true) {
				break;
			}
		}
		
		System.out.println("| ------------- |");
		System.out.println(" YOU WIN!!!!");
		System.out.println("| ------------- |");
		
	}
	
	public void takeTurn(Character current) {
		if (current.getClass() == MainCharacter.class) {
			//do thing for player
			getPlayerInput(current);
		} else {
			//do thing for npc
			EnemyTurn(current);
		}
	}
	
	public void EnemyTurn(Character enemy) {
		boolean hasAttacks = true;
		boolean hasCurses = true;
		System.out.println("Loading enemy info");
		System.out.println("| -------------- |");
		System.out.println("Name: " + enemy.getName());
		System.out.println("HP: " + enemy.getHp());
		System.out.println("| -------------- |");
		if (enemy.getAttacks().size() > 0) {
			for (Attack iter : enemy.getAttacks()) {
				System.out.println("- " + iter.getName());
			}
		} else {
			hasAttacks = false;
		}
		System.out.println("| -------------- |");
		System.out.println("curse count: " + enemy.getCurses().size());
		if (enemy.getCurses().size() > 0) {
			for (Curse iter : enemy.getCurses()) {
				System.out.println("- " + iter.getName());
			}
		} else {
			hasCurses = false;
		}
		System.out.println("| -------------- |");
		
		RandomGenerator enemyRgen = new RandomGenerator();
		
		if (hasAttacks && hasCurses) {
			if (enemyRgen.nextBoolean() == true) {
				//choose attack
				System.out.println("Choosing Attack..");
				computeAttack(enemy, enemy.getAttack(enemyRgen.nextInt(1, enemy.getAttacks().size() - 1)), mainCharacter);
			} else {
				//choose curse
				System.out.println("Choosing Curse..");
				computeCurse(enemy, enemy.getCurse(enemyRgen.nextInt(1, enemy.getAttacks().size() - 1)), mainCharacter);
			}
		} else if ((hasAttacks == true) && (hasCurses == false)) {
			System.out.println("Choosing attack..");
			computeAttack(enemy, enemy.getAttack(enemyRgen.nextInt(1, enemy.getAttacks().size() - 1)), mainCharacter);
		} else if ((hasAttacks == false) && (hasCurses == true)) {
			System.out.println("Choosing curse..");
			computeCurse(enemy, enemy.getCurse(enemyRgen.nextInt(1, enemy.getAttacks().size() - 1)), mainCharacter);
		} else {
			System.out.println("------------CRITICAL ERROR NO ATTACKS OR CURSES-------------");
			System.exit(0);
		}
		
	}
     
	private final static BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));
	public void getPlayerInput(Character mc) {
		String response = "";
		ArrayList<Character> enemies = getEnemies();
		
		try {
			System.out.println("Player turn: please choose an option: ");
			System.out.println("a: Attack");
			System.out.println("b: Curse");
			System.out.println("c: Item");
			System.out.println("d: Run");
			System.out.print("Your choice: ");
			response = CONSOLE_READER.readLine(); // we now have user input
			switch (response.toLowerCase()) {
			case "a":
				promptAttack(mc, enemies);
				break;
			case "b":
				//prompt and load curses
				promptCurse(mc, enemies);
				break;
			case "c":
				//prompt and load USEABLE items
				System.out.println("Loading items..");
				break;
			case "d":
				//quit and return to map
				System.out.println("Running..");
				endBattle();
				return;
			default:
				System.out.println("Invalid response given.");
				break;
			}
		} catch (IOException ioe) {
			System.err.println("Problem encountered while reading text from standard input.  Bailing.");
			System.exit(1);
		}

	}
	
	public void checkInventory(MainCharacter mc) {
		Inventory tempInventory = mc.getMainInventory();
		int count = 1;
		System.out.println("| ----------------- |");
		if (!tempInventory.getMyInventory().isEmpty()) {
			for (Item iter : tempInventory.getMyInventory()) {
				System.out.println(count + ": " + iter.getItemName());
			}
			System.out.println("| ----------------- |");
		} else {
			System.out.println("Inventory is empty..");
			System.out.println("| ----------------- |");
		}
	}
	
	public void endBattle() {
		this.run = true;
	}
	
	public void promptCurse(Character mc, ArrayList<Character> enemies) throws IOException {
		String response = "";
		//prompt and load attacks
		System.out.println("Loading curses..");
		System.out.println("Mc mana: " + mc.getMp());
		System.out.println("Curses: ");
		System.out.println("a: " + mc.getCurse(0).getName() + " - Cost: " + mc.getCurse(0).getMpCost());
		System.out.println("b: " + mc.getCurse(1).getName() + " - Cost: " + mc.getCurse(1).getMpCost());
		System.out.println("c: " + mc.getCurse(2).getName() + " - Cost: " + mc.getCurse(2).getMpCost());
		System.out.println("d: " + mc.getCurse(3).getName() + " - Cost: " + mc.getCurse(3).getMpCost());
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		switch (response) {
		case "a":
			checkCurse(mc, enemies, mc.getCurse(0));
			break;
		case "b":
			checkCurse(mc, enemies, mc.getCurse(1));
			break;
		case "c":
			checkCurse(mc, enemies, mc.getCurse(2));
			break;
		case "d":
			checkCurse(mc, enemies, mc.getCurse(3));
			break;
		}
	}
	
	public void checkCurse(Character mc, ArrayList<Character> enemies, Curse curse) throws IOException {
		Curses tempCurses = new Curses();
		if (curse == tempCurses.getCurse("999")) {
			System.out.println("No attack selectable");
		} else if (0 < mc.getMp() - curse.getMpCost()) {
			switch (curse.getTarget()) {
			case "single":
				singleCurse(mc, curse, enemies);
				break;
			case "multi":
				multiCurse(mc, curse, enemies);
				break;
			case "all":
				allCurse(mc, curse, enemies);
				break;
			}
			
		} else {
			System.out.println("Could not cast.. Not enough mana: ");
		}
	}

	private void allCurse(Character mc, Curse curse, ArrayList<Character> enemies) {
		for (Character iter : enemies) {
			computeCurse(mc, curse, iter);
		}
	}

	private void multiCurse(Character mc, Curse curse, ArrayList<Character> enemies) throws IOException {
		int count;
		String response = "";;
		System.out.println("Loading targets..");
		System.out.println("Targets: ");
		count = 0;
		
		for (Character iter : enemies) {
			System.out.println(count + ": " + iter.getName());
			count++;
		}
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		if (enemies.get(Integer.valueOf(response)) != null) {
			switch (curse.getName()) {
			case "Arcane Barrage":
				for (int i = 0; i < 4; i++) {
					computeCurse(mc, curse, enemies.get(Integer.valueOf(response)));
				}
				break;
			case "Telekinetic Barrage":
				for (int i = 0; i < 6; i++) {
					computeCurse(mc, curse, enemies.get(Integer.valueOf(response)));
				}
				break;
			}
		} else {
			System.out.println("Invalid target");
		}
	}

	private void singleCurse(Character mc, Curse curse, ArrayList<Character> enemies) throws IOException {
		int count;
		String response = "";;
		System.out.println("Loading targets..");
		System.out.println("Targets: ");
		count = 0;
		//print targets
		for (Character iter : enemies) {
			System.out.println(count + ": " + iter.getName());
			count++;
		}
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		if (enemies.get(Integer.valueOf(response)) != null) {
			computeCurse(mc, curse, enemies.get(Integer.valueOf(response)));
		} else {
			System.out.println("Invalid target");
		}
	}

	public void computeCurse(Character mc, Curse curse, Character character) {
		int hp = character.getHp();
		int damage;
		System.out.println("|	Target hp: " + hp);
		System.out.println(mc.getName() + " uses: " + curse.getName());
		damage = mc.getCor() * curse.getCurseMultiplier();
		System.out.println("|	Damage dealt: " + damage);
		System.out.println("|	Target hp: " + (hp - damage));
		mc.setMp(mc.getMp() - curse.getMpCost());
		System.out.println("|	mc MP: " + mc.getMp());
		character.setHp(hp - damage);
	}

	public void promptAttack(Character mc, ArrayList<Character> enemies) throws IOException {
		String response = "";
		//prompt and load attacks
		System.out.println("Loading attacks..");
		System.out.println("Attacks: ");
		System.out.println("a: " + mc.getAttack(0).getName());
		System.out.println("b: " + mc.getAttack(1).getName());
		System.out.println("c: " + mc.getAttack(2).getName());
		System.out.println("d: " + mc.getAttack(3).getName());
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		switch (response) {
		case "a":
			checkAttack(mc, enemies, mc.getAttack(0));
			break;
		case "b":
			checkAttack(mc, enemies, mc.getAttack(1));
			break;
		case "c":
			checkAttack(mc, enemies, mc.getAttack(2));
			break;
		case "d":
			checkAttack(mc, enemies, mc.getAttack(3));
			break;
		}
	}
	
	public void checkAttack(Character mc, ArrayList<Character> enemies, Attack atkSwitch) throws IOException {
		Attacks tempAttacks = new Attacks();
		if (atkSwitch == tempAttacks.getAttack("999")) {
			System.out.println("No attack selectable");
		} else {
			switch (atkSwitch.getTarget()) {
			case "single":
				singleAttack(mc, atkSwitch, enemies);
				break;
			case "multi":
				multiAttack(mc, atkSwitch, enemies);
				break;
			case "all":
				allAttack(mc, atkSwitch, enemies);
				break;
			}
			
		}
	}
	
	private void allAttack(Character mc, Attack atkSwitch, ArrayList<Character> enemies) throws IOException {
		for (Character iter : enemies) {
			computeAttack(mc, atkSwitch, iter);
		}
	}

	public void multiAttack(Character mc, Attack attack, ArrayList<Character> enemies) throws IOException {
		int count;
		String response = "";
		System.out.println("Loading targets..");
		System.out.println("Targets: ");
		count = 0;
		
		for (Character iter : enemies) {
			System.out.println(count + ": " + iter.getName());
			count++;
		}
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		if (enemies.get(Integer.valueOf(response)) != null) {
			switch(attack.getName()) {
			case "Double Strike":
				computeAttack(mc, attack, enemies.get(Integer.valueOf(response)));
				computeAttack(mc, attack, enemies.get(Integer.valueOf(response)));
				break;
			case "Rapid Strikes":
				for (int i = 0; i < 4; i++) {
					computeAttack(mc, attack, enemies.get(Integer.valueOf(response)));
				}
				break;
			}
		} else {
			System.out.println("Invalid target..");
		}
	}
	
	public void singleAttack(Character mc, Attack attack, ArrayList<Character> enemies) throws IOException {
		int count;
		String response = "";
		System.out.println("Loading targets..");
		System.out.println("Targets: ");
		count = 0;
		//print targets
		for (Character iter : enemies) {
			System.out.println(count + ": " + iter.getName());
			count++;
		}
		System.out.print("Your choice: ");
		response = CONSOLE_READER.readLine();
		if (enemies.get(Integer.valueOf(response)) != null) {
			computeAttack(mc, attack, enemies.get(Integer.valueOf(response)));
		} else {
			System.out.println("Invalid target");
		}
	}
	
	public void computeAttack(Character attacker, Attack attack, Character target) {
		Weapon thisWeapon = attacker.getEquipped();
		RandomGenerator crit = new RandomGenerator();
		int critSwitch;
		
		int hp = target.getHp();
		int damage;
		if (thisWeapon == null) {
			System.out.println("|	Target hp: " + hp);
			damage = attacker.getStr() * attack.getDamageMultiplier();
			System.out.println("|	Damage dealt: " + damage);
			System.out.println("|	Target hp: " + (hp - damage));
			target.setHp(hp - damage);
		} else {
			System.out.println("|	Target hp: " + hp);
			damage = attacker.getStr() * attack.getDamageMultiplier() + thisWeapon.getItemDamage();
			critSwitch = crit.nextInt(1, 100);
			switch(critSwitch) {
			case 1:
				System.out.println("| CRITICAL FAIL..");
				damage = 0;
				break;
			case 100:
				System.out.println("| HYPER-CRITICAL SUCCESS");
				damage *= 4;
				break;
			default:
				if (critSwitch < thisWeapon.getCritChance()) {
					System.out.println("| CRIT!");
					damage *= 2;
				}
				break;
			}
			System.out.println("|	Damage dealt: " + damage);
			System.out.println("|	Target hp: " + (hp - damage));
			target.setHp(hp - damage);
		}

	}
	
	public static void main(String[] args) {
		MainCharacter myMainCharacter = new MainCharacter("Seb", 20, 10, 5, 10);
		Items itemDB = new Items();
		myMainCharacter.setEquipped((Weapon)itemDB.getItem(31));
		myMainCharacter.giveAttack("002");
		myMainCharacter.giveAttack("004");
		myMainCharacter.giveAttack("015");
		myMainCharacter.giveAttack("019");
		myMainCharacter.giveCurse("999");
		myMainCharacter.giveCurse("001");
		myMainCharacter.giveCurse("019");
		myMainCharacter.giveCurse("010");
		myMainCharacter.setLevel(1);
		myMainCharacter.setHp(2000);
		Battle myBattle = new Battle(myMainCharacter);
		myBattle.startBattle();
	}
}
