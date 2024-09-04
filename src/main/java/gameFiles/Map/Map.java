

//Format: Map(#OfRows, #OfColumns)
package gameFiles.Map;

import acm.util.RandomGenerator;

public class Map {
	public Tile tileMap[][];
	private int rows;
	//First bracket
	private int cols;
	//Second bracket
	private int chestChance = 10;
	
	//map statistics to use
	public int stoneCount;
	public int enemyCount;
	public boolean isChest = false;
	
	public Map(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		tileMap = new Tile[rows][cols];
		this.generateMap();
		while (enemyCount == 0) {
			this.generateMap();
		}
	}
	
	public Tile getTile(int row, int col) {
		if (row < 0 || row > rows - 1) {
			return null;
		}
		
		if (col < 0 || col > cols - 1) {
			return null;
		}
		
		return tileMap[row][col];
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public void generateMap() {
		RandomGenerator rgen = new RandomGenerator();
		stoneCount = 0;
		enemyCount = 0;
		
		//fill map with all stone
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				tileMap[i][j] = new Tile(TileType.STONE);
				stoneCount++;
			}
		}
		
		//fill left right borders with lava
		for (int i = 0; i < rows; i++) {
			tileMap[i][0] = new Tile(TileType.GRASS);
			tileMap[i][cols - 1] = new Tile(TileType.GRASS);
			stoneCount--;
		}
		
		//next column inwards rgen more lava so it looks random
		for (int i = 0; i < rows; i++) {
			if (rgen.nextInt(1, 10) <= 5) {
				tileMap[i][1] = new Tile(TileType.GRASS);
				tileMap[i][cols - 2] = new Tile(TileType.GRASS);
				stoneCount--;
			};
		}
		
		//once more but sparse
		for (int i = 0; i < rows; i++) {
			if (rgen.nextInt(1, 10) <= 3) {
				tileMap[i][1] = new Tile(TileType.GRASS);
				tileMap[i][cols - 3] = new Tile(TileType.GRASS);
				stoneCount--;
			};
		}
		
		//generate enemy tiles
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ((tileMap[i][j].getType() == TileType.STONE) && (rgen.nextInt(1, stoneCount) == 1)) {
					tileMap[i][j] = new Tile(TileType.ENEMY);
					stoneCount--;
					enemyCount++;
				}
			}
		}
		
		//add decor
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ((tileMap[i][j].getType() == TileType.STONE) && (rgen.nextInt(1, 25) == 1)) {
					switch (rgen.nextInt(1, 3)) {
					case 1:
						tileMap[i][j] = new Tile(TileType.FOUNTAIN);
						stoneCount--;
						break;
					case 2:
						tileMap[i][j] = new Tile(TileType.ROCK);
						stoneCount--;
						break;
					case 3:
						tileMap[i][j] = new Tile(TileType.DECOR);
						stoneCount--;
					}
				}
			}
		}
		
		//load a chest with a random chance
		int randX = rgen.nextInt(0,rows - 1);
		int randY = rgen.nextInt(0,cols - 1);
		// 1 / 20 chest to load the code to add a chest
		if (rgen.nextInt(1, 20) <= chestChance) {
			System.out.println("Chest chance met");
			//while tilemap[random][random] is not equal to a stone tile
			while (tileMap[randX][randY].getType() != TileType.STONE) {
				randX = rgen.nextInt(0,rows - 1);
				randY = rgen.nextInt(0,cols - 1);
			}
			//once we get here we know randX and randY are a valid spot
			tileMap[randX][randY] = new Tile(TileType.CHEST);
			stoneCount++;
			isChest = true;
		}
	}
	
	public boolean isStone(int j, int i) {
		if (tileMap[i][j].getType() == TileType.STONE) {
			return true;
		} else {
			return false;
		}
	}
	
	public void replace(int rows, int cols, TileType type) {
		tileMap[rows][cols] = new Tile(type);
	}
	
	@Override
	public String toString() {
		String outString = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				outString = outString + tileMap[i][j].toString();
			}
			outString = outString + "\n";
		}
		
		return outString;
	}
	
	public static void main(String[] args) {
		Map myMap = new Map(10, 10);
		System.out.println(myMap.toString());
		
		for (int i = 0; i < 10; i++) {
			System.out.println(myMap.toString());
		}
	}
}
