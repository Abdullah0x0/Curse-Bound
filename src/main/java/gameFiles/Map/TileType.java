package gameFiles.Map;

import acm.util.RandomGenerator;

public enum TileType {
	GRASS, STONE, ENEMY, CHEST, FOUNTAIN, DECOR, ROCK;
	
	RandomGenerator rgen = new RandomGenerator();
	
	public boolean walkable() {
		switch (this) {
		case GRASS: return false;
		case STONE: return true;
		case ENEMY: return true;
		case CHEST: return false;
		case FOUNTAIN: return false;
		case DECOR: return true;
		case ROCK: return false;
	}
	return false;
	}
	
	public String getPath() {
		switch (this) {
			case GRASS: return "stone1.png";
			case STONE: return "stone.png";
			case ENEMY: return "enemy1.gif";
			case CHEST: return "chest.png";
			case FOUNTAIN: return "media/blood_fountain.png";
			case ROCK: return "rock.png";
			case DECOR:
				switch (rgen.nextInt(1, 2)) {
				case 1:
					return "dragonSkull.png";
				case 2:
					return "skullPile.png";
				default:
					return "stone.png";
				}
		}
		
		return "N/A";
	}
}
