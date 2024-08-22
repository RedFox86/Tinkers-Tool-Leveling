package net.redfox.tleveling.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TinkersLevelingCommonConfigs {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Float> PICKAXE_EXP_MULTIPLIER;
	public static final ForgeConfigSpec.ConfigValue<Float> MELEE_EXP_MULTIPLIER;
	public static final ForgeConfigSpec.ConfigValue<Float> RANGED_EXP_MULTIPLER;
	public static final ForgeConfigSpec.ConfigValue<Float> ARMOR_EXP_MULTIPLIER;

	static {
		BUILDER.push("Client Configs for Tinker's Tool Leveling 2");

		PICKAXE_EXP_MULTIPLIER = BUILDER.comment("The multipler for the exp granted to a mining tool after each block break.")
				.define("Exp Per Block: ", 1f);
		MELEE_EXP_MULTIPLIER = BUILDER.comment("The multipler for the exp granted to a melee weapon after each block break.")
				.define("Exp Per Block: ", 1f);
		RANGED_EXP_MULTIPLER = BUILDER.comment("The multipler for the exp granted to a ranged weapon after each block break.")
				.define("Exp Per Block: ", 1f);
		ARMOR_EXP_MULTIPLIER = BUILDER.comment("The multipler for the exp granted to an armor piece after each block break.")
				.define("Exp Per Block: ", 1f);

		BUILDER.pop();
		SPEC = BUILDER.build();
	}
}
