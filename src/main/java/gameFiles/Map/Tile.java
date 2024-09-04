package gameFiles.Map;

public class Tile {
	private TileType type;
	
	public Tile(TileType type) {
		this.type = type;
	}
	
	public void setType(TileType type) {
		this.type = type;
	}
		
	public TileType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return " [" + type + "] ";
	}
}
