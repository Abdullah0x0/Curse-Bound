package gameFiles.Battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends Character {
	private int enemyDiff; //0 to 5
	private String path;
	private String description;

	public Enemy(String name, int hp, int mp, int str, int cor, int enemyDiff, String path, String description) {
		super(name, hp, mp, str, cor);
		this.enemyDiff = enemyDiff;
		this.description = description;
		this.path = path;
		try {
			getEnemyAttacks(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void getEnemyAttacks(String path) throws FileNotFoundException {
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		char[] chr;
		char[] attacks = {'a','b','c','d'};
		char[] spells = {'!', '@', '#', '$'};
		
		//right here is where you insert variables you want to fill
		//----------------------
		Attacks AttackList = new Attacks();
		Curses CurseList = new Curses();
		ArrayList<Attack> tempAttacks = new ArrayList<Attack>();
		ArrayList<Curse> tempCurses = new ArrayList<Curse>();
		//String attackKey;
		//----------------------
		
		try {
			while((st = br.readLine()) != null) {
				//parse strings here.
				chr = st.toCharArray();
				
				//run read strings here
				for (char iter : attacks) {
					if (AttackList.getAttack(ReadString.readString(iter, chr)) != null) {
						tempAttacks.add(AttackList.getAttack(ReadString.readString(iter, chr)));
					}
				}
				
				for (char iter : spells) {
					if (CurseList.getCurse(ReadString.readString(iter, chr)) != null) {
						tempCurses.add(CurseList.getCurse(ReadString.readString(iter, chr)));
					}
				}
				
				//FILL MAP
				if (!tempAttacks.isEmpty()) {
					this.setAttacks(tempAttacks);
				} else {
					this.getAttacks().clear();
				}
				
				if (!tempCurses.isEmpty()) {
					this.setCurses(tempCurses);
				} else {
					this.getCurses().clear();
				}
				
				chr = "".toCharArray();
				st = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getEnemyCurses() {
		
	}

	public int getEnemyDiff() {
		return enemyDiff;
	}

	public void setEnemyDiff(int enemyDiff) {
		this.enemyDiff = enemyDiff;
	}
	
	public String getPath() {
		return path;
	}

	public String getDescription() {
		return description;
	}
	
	public String readSwitch(char[] readString) {
		String outText = "";
		for (int i = 0; i < readString.length - 2; i++) {
			outText = outText + readString[i + 2];
		}
		System.out.println(outText);
		return outText;
	}

	@Override
	public String toString() {
		return "Enemy [enemyDiff=" + enemyDiff + ", path=" + path + "]";
	}
}
