package gameFiles.Inventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import gameFiles.Battle.ReadString;

public class Items {
	private HashMap<Integer, Item> ItemsHash = new HashMap<Integer, Item>();
	String filePath = "databases/ItemsList";
	
	public HashMap<Integer, Item> getItemsHash() {
		return ItemsHash;
	}
	
    public Items() {
        try {
			ParseItems();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void ParseItems() throws FileNotFoundException {
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
		char[] chr;
		
		int key;
		String name;
		String itemType;
		String description;
		boolean healType;
		int healAmount;
		int weaponDamage;
		int critRate;
		
		try {
			while((st = br.readLine()) != null) {
				//parse strings here.
				chr = st.toCharArray();
				
				key = Integer.valueOf(ReadString.readString('$', chr));
				name = ReadString.readString('!', chr);
				description = ReadString.readString('#', chr);
				
				itemType = ReadString.readString('@', chr);
				
				switch(ItemType.valueOf(itemType)) {
				case CONSUMABLE:
					healType = Boolean.valueOf(ReadString.readString('%', chr));
					healAmount = Integer.valueOf(ReadString.readString('^', chr));
					ItemsHash.put(key, new Consumable(name, healType, healAmount, description));
					break;
				case WEAPON:
					weaponDamage = Integer.valueOf(ReadString.readString('&', chr));
					critRate = Integer.valueOf(ReadString.readString('*', chr));
					ItemsHash.put(key, new Weapon(name, weaponDamage, critRate, description));
					break;
				default:
					ItemsHash.put(key, new Item(name, ItemType.valueOf(itemType), description));
					break;
				}
				
				
				chr = "".toCharArray();
				st = "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public Item getItem(Integer key) {
        return ItemsHash.get(key);
    }
}
