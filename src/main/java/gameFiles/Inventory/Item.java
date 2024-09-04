package gameFiles.Inventory;

public class Item{
	protected String itemName;
	protected ItemType itemType;
	protected String itemDescription;
	
	public Item(String itemName, ItemType itemType, String itemDescription) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemDescription = itemDescription;
    }

	public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
