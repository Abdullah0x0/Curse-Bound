package gameFiles.Inventory;
import java.util.ArrayList;

public class Inventory {
	private ArrayList<Item> myInventory = new ArrayList<Item>();
	
	public ArrayList<Item> getMyInventory() {
		return myInventory;
	}
	
	public void setMyInventory(ArrayList<Item> myInventory) {
		this.myInventory = myInventory;
	}
	
	public Inventory() {
		myInventory = new ArrayList<Item>();
	}
	
	public void addItem(Item item)
	{
		if (myInventory.size() + 1 < 7) {
			myInventory.add(item);
		}
	}
	
	public void removeItem(int item) {
		myInventory.remove(item);
	}
	
	public void useItem(Item item) {
		switch(item.getItemType()) {
		case CONSUMABLE:
			break;
		
		case WEAPON:
			break;
		}
	}

	public void displayInventory() {
			System.out.println("Inventory:");
	        for (Item item : myInventory) {
	            System.out.println("Item: " + item.getItemName() + ", Type: " + item.getItemType() +
	                    ", Description: " + item.getItemDescription());
	            if (item.getItemType() == ItemType.CONSUMABLE) {
	                Consumable consumable = (Consumable) item;
	                System.out.println("   HealthType: " + consumable.isHealthType() +
	                        ", HealAmount: " + consumable.getHealAmount());
	            } else if (item.getItemType() == ItemType.WEAPON) {
	                Weapon weapon = (Weapon) item;
	                System.out.println("   Damage: " + weapon.getItemDamage() +
	                        ", CritChance: " + weapon.getCritChance());
	            }
	            System.out.println();
	        }
		}
	
	
	//for testing
	public static void main(String[] args) {
        Items itemDatabase = new Items();
        Inventory myInventory = new Inventory();
        myInventory.addItem(new Consumable("Health Potion", true, 50, "Restores health."));
        myInventory.addItem(new Weapon("Sword", 20, 10, "A sharp sword."));
        myInventory.addItem(itemDatabase.getItem(21));

        myInventory.displayInventory();
    }

}
