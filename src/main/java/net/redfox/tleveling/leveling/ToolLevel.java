package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;

public class ToolLevel {
	public static final ToolLevel LIKE_NEW = new ToolLevel(0, "Like New", "like_new");
	public static final ToolLevel CLUMSY = new ToolLevel(1, "Clumsy", "clumsy");
	public static final ToolLevel COMFORTABLE = new ToolLevel(2, "Comfortable", "comfortable");
	public static final ToolLevel ACCUSTOMED = new ToolLevel(3, "Accustomed", "accustomed");
	public static final ToolLevel ADEPT = new ToolLevel(4, "Adept", "adept");
	public static final ToolLevel EXPERT = new ToolLevel(5, "Expert", "expert");
	public static final ToolLevel MASTER = new ToolLevel(6, "Master", "master");
	public static final ToolLevel GRANDMASTER = new ToolLevel(7, "Grandmaster", "grandmaster");
	public static final ToolLevel HEROIC = new ToolLevel(8, "Heroic", "heroic");
	public static final ToolLevel LEGENDARY = new ToolLevel(9, "Legendary", "legendary");
	public static final ToolLevel GODLIKE = new ToolLevel(10, "Godlike", "godlike");
	public static final ToolLevel AWESOME = new ToolLevel(11, "Awesome", "awesome");


	public static final ToolLevel[] TOOL_LEVELS = new ToolLevel[]{LIKE_NEW, CLUMSY, COMFORTABLE, ACCUSTOMED, ADEPT, EXPERT, MASTER, GRANDMASTER, HEROIC, LEGENDARY, GODLIKE, AWESOME};

	private final int level;
	private final String name;
	private final String id;
	public ToolLevel(int ILevel, String IName, String IID) {
		this.level = ILevel;
		this.name = IName;
		this.id = IID;
	}
	public int getLevel() {
		return this.level;
	}
	public Component getMessage(Component toolName, boolean bonusModifier) {
		if (bonusModifier) {
			return Component.translatable("message.tleveling."+id, toolName, Component.literal("(+1 modifier)")).withStyle(s -> s.withColor(TooltipHandler.BLUE));
		}
		return Component.translatable("message.tleveling."+id, toolName, "").withStyle(s -> s.withColor(TooltipHandler.BLUE));
	}
	public String getName() {
		return this.name;
	}
	public String getId() {
		return this.id;
	}
	public boolean isMaxLevel() {
		return this.level == 11;
	}
}
