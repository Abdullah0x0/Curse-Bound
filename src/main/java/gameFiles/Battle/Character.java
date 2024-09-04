package gameFiles.Battle;

import java.util.ArrayList;
import acm.graphics.GPoint;
import gameFiles.Inventory.Weapon;

public class Character implements Comparable<Character>{
	private String name;
	private String portrait;
	private GPoint pos;
	private int hp;
	private int maxHp;
	private int mp;
	private int maxMp;
	private int str;
	private int cor;
	private Weapon equipped;
	private ArrayList<Attack> attacks = new ArrayList<Attack>();
	private ArrayList<Curse> curses = new ArrayList<Curse>();
	private int turnOrder;

	public Character(String name, int hp, int mp, int str, int cor) {
		super();
		this.name = name;
		this.hp = hp;
		this.maxHp = hp;
		this.mp = mp;
		this.maxMp = mp;
		this.str = str;
		this.cor = cor;
	}
	
	
	
	public int getMaxHp() {
		return maxHp;
	}



	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}



	public int getMaxMp() {
		return maxMp;
	}



	public void setMaxMp(int maxMp) {
		this.maxMp = maxMp;
	}



	public Weapon getEquipped() {
		return equipped;
	}

	public void setEquipped(Weapon equipped) {
		this.equipped = equipped;
	}

	public int getStr() {
		return str;
	}
	
	public void setStr(int str) {
		this.str = str;
	}

	public void setCor(int cor) {
		this.cor = cor;
	}
	
	public int getCor() {
		return cor;
	}

	@Override
	public String toString() {
		return "Character [name=" + name + ", hp=" + hp + ", mp=" + mp + ", turnOrder=" + turnOrder + "]";
	}

	public int getTurnOrder() {
		return turnOrder;
	}

	public void setTurnOrder(int turnOrder) {
		this.turnOrder = turnOrder;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public GPoint getPos() {
		return pos;
	}
	public void setPos(GPoint pos) {
		this.pos = pos;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		if (hp > this.maxHp) {
			this.hp = this.maxHp;
		} else {
			this.hp = hp;
		}
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		if (mp > this.maxMp) {
			this.mp = this.maxMp;
		} else {
			this.mp = mp;
		}
	}
	public Curse getCurse(int choice) {
		if (choice > curses.size() - 1) {
			return null;
		} else {
			return curses.get(choice);
		}
	}
	public void giveCurse(String key) {
		Curses tempCurses = new Curses();
		curses.add(tempCurses.getCurse(key));
	}
	public Attack getAttack(int choice) {
		if (choice > attacks.size() - 1) {
			return null;
		} else {
			return attacks.get(choice);
		}
	}
	public void giveAttack(String key) {
		Attacks tempAttacks = new Attacks();
		attacks.add(tempAttacks.getAttack(key));
	}
	public ArrayList<Attack> getAttacks() {
		return attacks;
	}
	public void setAttacks(ArrayList<Attack> attacks) {
		this.attacks = attacks;
	}
	public ArrayList<Curse> getCurses() {
		return curses;
	}
	public void setCurses(ArrayList<Curse> curses) {
		this.curses = curses;
	}

	@Override
	public int compareTo(Character c) {
		// TODO Auto-generated method stub
		return Integer.compare(getTurnOrder(), c.getTurnOrder());
	}
}
