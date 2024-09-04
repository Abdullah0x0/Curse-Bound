package gameFiles.Battle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import acm.util.RandomGenerator;

public class EnemyDatabase {
	private HashMap<Integer, Enemy> enemyHash = new HashMap<Integer, Enemy>();

	public EnemyDatabase(String fileLoc) {
		//run file reader
		try {
			ParseEnemies(fileLoc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ParseEnemies(String fileLoc) throws FileNotFoundException {
		File file = new File(fileLoc);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		char[] chr;
		
		//right here is where you insert variables you want to fill
		//----------------------
		//HashMap<String, Enemy> outData = new HashMap<String, Enemy>();
		int key;
		String name;
		int hp;
		int mp;
		int enemyDiff;
		int str;
		int cor;
		String path;
		String portrait;
		String description;
		//----------------------
		
		try {
			while((st = br.readLine()) != null) {
				//parse strings here.
				chr = st.toCharArray();
				
				key = Integer.valueOf(chr[0]) - 48;
				
				//run read strings here
				name = ReadString.readString('$', chr);
				portrait = ReadString.readString('(', chr);
				hp = Integer.valueOf(ReadString.readString('-', chr));
				mp = Integer.valueOf(ReadString.readString('+', chr));
				enemyDiff = Integer.valueOf(ReadString.readString('^', chr));
				str = Integer.valueOf(ReadString.readString('@', chr));
				cor = Integer.valueOf(ReadString.readString('#', chr));
				path = ReadString.readString('%', chr);
				description = ReadString.readString('!', chr);
				
				//fill map
				Enemy enemyTemp = new Enemy(name, hp, mp, str, cor, enemyDiff, path, description);
				enemyTemp.setPortrait(portrait);
				//System.out.println("ENEMYPORTRAIT:" + enemyTemp.getPortrait());
				enemyHash.put(key, enemyTemp);
				
				//resetting values
				chr = "".toCharArray();
				st = "";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return outData;
	}
	
	public HashMap<Integer, Enemy> getEnemyHash() {
		return enemyHash;
	}
	
	public Set<Integer> getEnemyKeys() {
		return getEnemyHash().keySet();
	}
	
	public Enemy getEnemy(int key) {
		Enemy tempEnemy = getEnemyHash().get(key);
		Enemy outEnemy = new Enemy(tempEnemy.getName(), tempEnemy.getHp(), tempEnemy.getMp(), tempEnemy.getStr(), tempEnemy.getCor(), tempEnemy.getEnemyDiff(), tempEnemy.getPath(), tempEnemy.getDescription());
		outEnemy.setPortrait(tempEnemy.getPortrait());
		return outEnemy;
	}
	
	public Enemy getRandomEnemy() {
		RandomGenerator rgen = new RandomGenerator();
		
		return getEnemy(rgen.nextInt(0, getEnemyHash().size() - 1));
	}
	
	public Enemy getRandomEnemy(int difficulty) {
		Enemy tempEnemy = getRandomEnemy();
		
		while (tempEnemy.getEnemyDiff() != difficulty) {
			tempEnemy = getRandomEnemy();
		}
		
		return tempEnemy;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		EnemyDatabase myEnemyDatabase = new EnemyDatabase("databases/EnemyDatabase");
		for (int keys : myEnemyDatabase.getEnemyKeys()) {
			System.out.println(myEnemyDatabase.getEnemy(keys));
		}
		
		System.out.println("Random:");
		for (int i = 0; i < 10; i++) {
			System.out.println(myEnemyDatabase.getRandomEnemy().getPortrait());
		}
		
		System.out.println("Random enemies from difficulty 1");
		for (int i = 0; i < 3; i++) {
			System.out.println(myEnemyDatabase.getRandomEnemy(1).toString());
		}
		
		System.out.println("Random enemies from difficulty 2");
		for (int i = 0; i < 3; i++) {
			System.out.println(myEnemyDatabase.getRandomEnemy(2).toString());
		}
		
		System.out.println("Random enemies from max difficulty");
		for (int i = 0; i < 3; i++) {
			System.out.println(myEnemyDatabase.getRandomEnemy(3).toString());
		}
		return;
	}
}
