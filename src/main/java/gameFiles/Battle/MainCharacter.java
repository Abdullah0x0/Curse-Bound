package gameFiles.Battle;
import gameFiles.Inventory.Inventory;

public class MainCharacter extends Character {
	private int experience;
	private Inventory mainInventory;
	private int level;
	
	public MainCharacter(String name, int hp, int mp, int str, int cor) {
		super(name, hp, mp, str, cor);
		experience = 0;
		mainInventory = new Inventory();
		level = 0;
	}
	
	public void giveExp(int amount) {
		if (experience >= 1000) {
			giveLevel();
			experience = 0;
		} else {
			experience += amount;
		}
	}
	
	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public Inventory getMainInventory() {
		return mainInventory;
	}

	public void setMainInventory(Inventory mainInventory) {
		this.mainInventory = mainInventory;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void giveLevel() {
		level++;
		//give increases to hp and more.
		this.setMaxHp(this.getHp() + 50);
		this.setMaxMp(this.getMp() + 50);
		
		this.setHp(this.getMaxHp());
		System.out.println("HP:" + this.getHp() + "max:" + this.getMaxHp());
		this.setMp(this.getMaxMp());
		
		this.setStr(this.getStr() + 1);
		this.setCor(this.getCor() + 1);
	}
	
	public int getLevel() {
		return level;
	}	 
}
