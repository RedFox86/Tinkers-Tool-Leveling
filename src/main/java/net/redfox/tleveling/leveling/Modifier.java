package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Modifier {
	private static final int GLOBAL = 0;
	private static final int MELEE = 1;
	private static final int BONUS = 2;
	private static final int PICKAXE = 3;
	private static final int RANGED = 4;
	private static final int CROSSBOW = 5;
	private static final int ARMOR = 6;
	private static final int HELMET = 7;
	private static final int CHESTPLATE = 8;
	private static final int LEGGINGS = 9;
	private static final int BOOTS = 10;

	//	private static final Modifier WORLDBOUND = new Modifier("worldbound", 1); Modifiers with max level one don't work.

	private static final Modifier MAGNETIC = new Modifier("magnetic", 5);
	private static final Modifier REINFORCED = new Modifier("reinforced", 5);

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
	private static final Modifier SWIFTSTRIKE = new Modifier("swiftstrike", 5);

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

	//Modifiers that can go on any tool
	public static final List<Modifier> GLOBAL_MODIFIERS = new ArrayList<>(Arrays.asList(MAGNETIC, REINFORCED));
	//Modifiers that can only go on melee tools
	public static final List<Modifier> MELEE_MODIFIERS = new ArrayList<>(Arrays.asList(FIERY, FREEZING, KNOCKBACK, NECROTIC, PADDED, SEVERING, SWEEPING_EDGE));
	//Modifiers that can only go on weapons (melee, ranged, etc)
	public static final List<Modifier> BONUS_DAMAGE_MODIFIERS = new ArrayList<>(Arrays.asList(ANTIAQUATIC, BANE_OF_SPIDERS, COOLING, KILLAGER, PIERCE, SHARPNESS, SMITE, SWIFTSTRIKE));
	//Modifiers that can only go on pickaxes
	public static final List<Modifier> PICKAXE_MODIFIERS = new ArrayList<>(Arrays.asList(HASTE, BLASTING, HYDRAULIC, LIGHTSPEED));
	//Modifiers that can only go on ranged weapons (bows, crossbows, etc)
	public static final List<Modifier> RANGED_MODIFIERS = new ArrayList<>(Arrays.asList(FIERY, FREEZING, IMPALING, NECROTIC, PIERCE, POWER, PUNCH));
	//Modifiers that can only go on crossbows
	public static final List<Modifier> CROSSBOW_MODIFIERS = new ArrayList<>(List.of(QUICK_CHARGE));
	//Modifiers that can only go on armor
	public static final List<Modifier> ARMOR_MODIFIERS = new ArrayList<>(Arrays.asList(FIERY, FREEZING, RICOCHET, SPRINGY, THORNS));
	//Modifiers that can only go on helmets
	public static final List<Modifier> HELMET_MODIFIERS = new ArrayList<>(List.of(RESPIRATION));
	//Modifiers that can only go on chestplates
	public static final List<Modifier> CHESTPLATE_MODIFIERS = new ArrayList<>(Arrays.asList(HASTE, KNOCKBACK));
	//Modifiers that can only go on leggings
	public static final List<Modifier> LEGGINGS_MODIFIERS = new ArrayList<>(Arrays.asList(LEAPING, SPEEDY));
	//Modifiers that can only go on boots
	public static final List<Modifier> BOOTS_MODIFIERS = new ArrayList<>(Arrays.asList(DEPTH_STRIDER, FEATHER_FALLING, LIGHTSPEED_ARMOR, SOUL_SPEED));

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

	/**
	 * Adds a new Modifier to its appropriate ArrayList so that it can be added by the tool leveling system.
	 * <p>
	 * Use static constants accessed via the Modifier class for easier access.
	 *
	 * @param modifierType An integer from 0-10 that represents the ArrayList that the Modifier should be added to.
	 */
	public void register(int modifierType) {
		switch (modifierType) {
			case 0 -> GLOBAL_MODIFIERS.add(this);
			case 1 -> MELEE_MODIFIERS.add(this);
			case 2 -> BONUS_DAMAGE_MODIFIERS.add(this);
			case 3 -> PICKAXE_MODIFIERS.add(this);
			case 4 -> RANGED_MODIFIERS.add(this);
			case 5 -> CROSSBOW_MODIFIERS.add(this);
			case 6 -> ARMOR_MODIFIERS.add(this);
			case 7 -> HELMET_MODIFIERS.add(this);
			case 8 -> CHESTPLATE_MODIFIERS.add(this);
			case 9 -> LEGGINGS_MODIFIERS.add(this);
			case 10 -> BOOTS_MODIFIERS.add(this);
			default -> throw new IllegalStateException("Unexpected value: " + modifierType);
		};
	}
}