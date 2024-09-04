package gameFiles.Battle;

public class Curse {
	private String name;
	private int curseMultiplier;
	private String target;
	private int mpCost;
	private String description;
	
	public Curse(String name, int curseMultiplier, String target, int mpCost, String description) {
		super();
		this.name = name;
		this.curseMultiplier = curseMultiplier;
		this.target = target;
		this.mpCost = mpCost;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Curse [name=" + name + ", curseMultiplier=" + curseMultiplier + ", target=" + target + ", mpCost="
				+ mpCost + ", description=" + description + "]";
	}
	
	public String getDescription() {
		return description;
	}

	public String getTarget() {
		return target;
	}
	
	public String getName() {
		return name;
	}
	public int getCurseMultiplier() {
		return curseMultiplier;
	}
	public int getMpCost() {
		return mpCost;
	}
}
