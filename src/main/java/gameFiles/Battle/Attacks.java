package gameFiles.Battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Attacks {
	public static HashMap<String, Attack> attackList = new HashMap<String, Attack>();
	
	public Attacks() {
		try {
			ParseAttacks();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ParseAttacks() throws FileNotFoundException {
		File file = new File("databases/AttackList");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
		char[] chr;
		
		String key;
		String name;
		int damageMultiplier;
		String description;
		String target;
		
		try {
			while((st = br.readLine()) != null) {
				//parse strings here.
				chr = st.toCharArray();
				
				key = ReadString.readString('$', chr);
				name = ReadString.readString('!', chr);
				target = ReadString.readString('*', chr);
				damageMultiplier = Integer.valueOf(ReadString.readString(':', chr));
				description = ReadString.readString('-', chr);
				attackList.put(key, new Attack(name, damageMultiplier, target, description));
				
				chr = "".toCharArray();
				st = "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Attack getAttack(String key) {
		return attackList.get(key);
	}
}
