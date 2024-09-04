package gameFiles.Inventory;

public class Weapon extends Item {
	    private int itemDamage;
	    private int critChance;

	    public Weapon(String itemName, int itemDamage, int critChance, String itemDescription) {
	        super(itemName, ItemType.WEAPON, itemDescription);
	        this.itemDamage = itemDamage;
	        this.critChance = critChance;
	    }

	    // Getters and setters

	    @Override
		public String toString() {
			return "Weapon [itemDamage=" + itemDamage + ", critChance=" + critChance + ", itemName=" + itemName
					+ ", itemType=" + itemType + "]";
		}

		public int getItemDamage() {
	        return itemDamage;
	    }


		public void setItemDamage(int itemDamage) {
	        this.itemDamage = itemDamage;
	    }

	    public int getCritChance() {
	        return critChance;
	    }

	    public void setCritChance(int critChance) {
	        this.critChance = critChance;
	    }
	

}
