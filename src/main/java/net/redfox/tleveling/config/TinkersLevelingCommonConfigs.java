package net.redfox.tleveling.config;


import net.minecraftforge.common.ForgeConfigSpec;

public class TinkersLevelingCommonConfigs {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;

  public static final ForgeConfigSpec.ConfigValue<Double> PICKAXE_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Double> KILL_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Double> ARMOR_EXP_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> LEVELUP_EXP_REQUIRED;
  public static final ForgeConfigSpec.ConfigValue<Double> LEVELUP_INCREASE;

//  public static final ForgeConfigSpec.ConfigValue<Integer> LEVEL_BONUS_MODIFIER;

  static {
    BUILDER.push("Common Configs for Tinker's Tool Leveling");

    PICKAXE_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to a mining tool after each block break. Default is 1.0")
        .define("Exp Per Block", 1.0d);
    KILL_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to a melee weapon after each block break. Default is 1.0")
        .define("Exp Per Melee Kill", 1.0d);
    ARMOR_EXP_MULTIPLIER = BUILDER.comment("The multiplier for the exp granted to an armor piece after each block break. Default is 1.0")
        .define("Exp Per Armor Hit", 1.0d);
//    LEVEL_BONUS_MODIFIER = BUILDER.comment("The amount of levels between each bonus modifier. Set to 0 to disable bonus modifiers. Default is 3")
//        .defineInRange("Levels Between Bonus Modifiers", 3, 0, 11);
    LEVELUP_EXP_REQUIRED = BUILDER.comment("The base exp required to level up a tool. Default is 500")
        .defineInRange("Exp Required", 200, 1, 2147483647);
    LEVELUP_INCREASE = BUILDER.comment("The exponential increase between tool exp levels")
        .define("Exp Increase", 2d);

    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}