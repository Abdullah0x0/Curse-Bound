package gameFiles.Battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Curses {
	public static HashMap<String, Curse> curseList = new HashMap<String, Curse>();
	
	public Curses() {
		try {
			ParseCurses();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void ParseCurses() throws FileNotFoundException {
		File file = new File("databases/CurseList");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String st;
		char[] chr;
		
		String key;
		String name;
		int curseMultiplier;
		int mpCost;
		String target;
		String description;
		
		try {
			while((st = br.readLine()) != null) {
				//parse strings here.
				chr = st.toCharArray();
				
				key = ReadString.readString('*', chr);
				
				name = ReadString.readString('$', chr);
				curseMultiplier = Integer.valueOf(ReadString.readString(':', chr));
				target = ReadString.readString('%', chr);
				mpCost = Integer.valueOf(ReadString.readString('^', chr));
				description = ReadString.readString('@', chr);
				
				curseList.put(key, new Curse(name, curseMultiplier, target, mpCost, description));
				
				chr = "".toCharArray();
				st = "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Curse getCurse(String key) {
		return curseList.get(key);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ParseCurses();
		
		for (String key : curseList.keySet() ) {
			System.out.println(key + ": " + curseList.get(key).toString());
		}
	}
}
