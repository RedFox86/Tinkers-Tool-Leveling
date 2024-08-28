package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;

public class Modifier {
//	private static final Modifier WORLDBOUND = new Modifier("worldbound", 1); Modifiers with max level one don't work.
	private static final Modifier MAGNETIC = new Modifier("magnetic", 5);

	private static final Modifier FIERY = new Modifier("fiery", 5);
	private static final Modifier FREEZING = new Modifier("freezing", 3);
	private static final Modifier KNOCKBACK = new Modifier("knockback", 3);
	private static final Modifier NECROTIC = new Modifier("necrotic", 5);
	private static final Modifier PADDED = new Modifier("padded", 3);
	private static final Modifier SEVERING = new Modifier("severing", 3);
	private static final Modifier SWEEPING_EDGE = new Modifier("sweeping_edge", 3);

	private static final Modifier ANTIAQUATIC = new Modifier("antiaquatic", 5);
	private static final Modifier BANE_OF_SPIDERS = new Modifier("bane_of_sssss", 5);
	private static final Modifier COOLING = new Modifier("cooling", 5);
	private static final Modifier KILLAGER = new Modifier("killager", 5);
	private static final Modifier PIERCE = new Modifier("pierce", 3);
	private static final Modifier SHARPNESS = new Modifier("sharpness", 5);
	private static final Modifier SMITE = new Modifier("smite", 5);
	private static final Modifier SWIFTSTRIKE = new Modifier("switftstrike", 5);

	private static final Modifier HASTE = new Modifier("haste", 5);
	private static final Modifier BLASTING = new Modifier("blasting", 5);
	private static final Modifier HYDRAULIC = new Modifier("hydraulic", 5);
	private static final Modifier LIGHTSPEED = new Modifier("lightspeed", 5);

	private static final Modifier RICOCHET = new Modifier("ricochet", 2);
	private static final Modifier SPRINGY = new Modifier("springy", 3);
	private static final Modifier THORNS = new Modifier("thorns", 3);
	private static final Modifier RESPIRATION = new Modifier("respiration", 3);
	private static final Modifier LEAPING = new Modifier("leaping", 2);
	private static final Modifier SPEEDY = new Modifier("speedy", 3);
	private static final Modifier DEPTH_STRIDER = new Modifier("depth_strider", 3);
	private static final Modifier FEATHER_FALLING = new Modifier("feather_falling", 4);
	private static final Modifier LIGHTSPEED_ARMOR = new Modifier("lightspeed_armor", 3);
	private static final Modifier SOUL_SPEED = new Modifier("soulspeed", 3);

	private static final Modifier IMPALING = new Modifier("impaling", 4);
	private static final Modifier POWER = new Modifier("power", 5);
	private static final Modifier PUNCH = new Modifier("punch", 2);
	private static final Modifier QUICK_CHARGE = new Modifier("quick_charge", 3);

	public static final Modifier[] GLOBAL_MODIFIERS = new Modifier[]{MAGNETIC};
	public static final Modifier[] MELEE_MODIFIERS = new Modifier[]{FIERY, FREEZING, KNOCKBACK, NECROTIC, PADDED, SEVERING, SWEEPING_EDGE};
	public static final Modifier[] BONUS_DAMAGE_MODIFIERS = new Modifier[]{ANTIAQUATIC, BANE_OF_SPIDERS, COOLING, KILLAGER, PIERCE, SHARPNESS, SMITE, SWIFTSTRIKE};
	public static final Modifier[] PICKAXE_MODIFIERS = new Modifier[]{HASTE, BLASTING, HYDRAULIC, LIGHTSPEED};
	public static final Modifier[] RANGED_MODIFIERS = new Modifier[]{FIERY, FREEZING, IMPALING, NECROTIC, PIERCE, POWER, PUNCH};
	public static final Modifier[] CROSSBOW_MODIFIERS = new Modifier[]{QUICK_CHARGE};
	public static final Modifier[] ARMOR_MODIFIERS = new Modifier[]{FIERY, FREEZING, RICOCHET, SPRINGY, THORNS};
	public static final Modifier[] HELMET_MODIFIERS = new Modifier[]{RESPIRATION};
	public static final Modifier[] CHESTPLATE_MODIFIERS = new Modifier[]{HASTE, KNOCKBACK};
	public static final Modifier[] LEGGINGS_MODIFIERS = new Modifier[]{LEAPING, SPEEDY};
	public static final Modifier[] BOOTS_MODIFIERS = new Modifier[]{DEPTH_STRIDER, FEATHER_FALLING, LIGHTSPEED_ARMOR, SOUL_SPEED};

	private final String name;
	private final int max;
	private final Component message;
	public Modifier(String IName, int Imax) {
		this.max = Imax;
		this.name = IName;
		this.message = Component.translatable("message.tleveling."+name).withStyle(s -> s.withColor(TooltipHandler.DARK_AQUA));
	}
	public String getName() {
		return this.name;
	}
	public int getMax() {
		return this.max;
	}
	public Component getMessage() {
		return this.message;
	}
}