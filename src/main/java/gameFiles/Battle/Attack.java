package gameFiles.Battle;

public class Attack {
	private String name;
	private int damageMultiplier;
	private String target;
	private String description;

	public Attack(String name, int damageMultiplier, String target, String description) {
		this.name = name;
		this.damageMultiplier = damageMultiplier;
		this.description = description;
		this.target = target;
	}

	

	@Override
	public String toString() {
		return "Attack [name=" + name + ", damageMultiplier=" + damageMultiplier + ", target=" + target
				+ ", description=" + description + "]";
	}

	public String getTarget() {
		return target;
	}

	public String getName() {
		return name;
	}
	public int getDamageMultiplier() {
		return damageMultiplier;
	}

	public String getDescription() {
		return description;
	}
}
