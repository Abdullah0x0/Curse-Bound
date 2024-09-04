package gameFiles.Inventory;

public enum ItemType {
	WEAPON, CONSUMABLE;
	
	//implement classes so we can return the types
	
	public String getName() {
		switch(this) {
			case WEAPON:
				return "weapon";
			case CONSUMABLE:
				return "consumable";
		}
		
		return "n/a";
	}
}
