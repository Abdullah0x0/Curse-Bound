package gameFiles.Inventory;

public class Consumable extends Item {
	private boolean healthType;
    private int healAmount;

    public Consumable(String itemName, boolean healthType, int healAmount, String itemDescription) {
        super(itemName, ItemType.CONSUMABLE, itemDescription);
        this.healthType = healthType;
        this.healAmount = healAmount;
    }

    // Getters and setters

    @Override
	public String toString() {
		return "Consumable [healthType=" + healthType + ", healAmount=" + healAmount + ", itemName=" + itemName
				+ ", itemType=" + itemType + "]";
	}

	public boolean isHealthType() {
        return healthType;
    }

	public void setHealthType(boolean healthType) {
        this.healthType = healthType;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

}
