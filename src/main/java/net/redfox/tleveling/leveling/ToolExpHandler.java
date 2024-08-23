package net.redfox.tleveling.leveling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;
import net.redfox.tleveling.util.NBTHandler;

import java.util.Random;

public class ToolExpHandler {
	public static void saveLevelOnTool(ItemStack item, ToolLevel level) {
		item.getOrCreateTag().putInt("toolLevel", level.getLevel());
	}
	public static ToolLevel loadLevelOnTool(ItemStack item) {
		return ToolLevel.TOOL_LEVELS[(int) NBTHandler.loadNBTData(item.getOrCreateTag(), "toolLevel")];
	}
	public static void saveMiningExpOnTool(BlockState state, ItemStack item) {
		double blockExp = getExpFromBlockState(state);
		double toolExp = blockExp + NBTHandler.loadNBTData(item.getOrCreateTag(), "toolExp");
		NBTHandler.saveNBTData(item.getOrCreateTag(), toolExp, "toolExp");
	}
	public static void saveKillingExpOnTool(Entity entity, ItemStack item) {
		double entityExp = getExpFromEntity(entity);
		double toolExp = entityExp + NBTHandler.loadNBTData(item.getOrCreateTag(), "toolExp");
		NBTHandler.saveNBTData(item.getOrCreateTag(), toolExp, "toolExp");
	}
	public static void saveArmorExpOnTool(double exp, ItemStack item) {
		double toolExp = exp + NBTHandler.loadNBTData(item.getOrCreateTag(), "toolExp");
		NBTHandler.saveNBTData(item.getOrCreateTag(), toolExp, "toolExp");
	}
	public static double loadExpOnTool(ItemStack stack) {
		return NBTHandler.loadNBTData(stack.getOrCreateTag(), "toolExp");
	}
	public static int getRequiredExp(int level) {
		return MathHandler.round(Math.pow(2.5f, level) * 500);
	}

	private static double getExpFromBlockState(BlockState state) {
		double exp;
		if (state.is(ModTags.Blocks.EXP_PICKAXE_NOR_FIVE)) {
			exp = 0.5f;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_TWO)) {
			exp = 2;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_FIVE)) {
			exp = 5;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_ADMIN)) {
			TinkersLeveling.warnLog("Admin only mining exp was granted. Was this intentional?");
			exp = 100000;
		} else if (state.is(ModTags.Blocks.EXCLUDED_BLOCKS)) {
			exp = 0;
		} else {
			exp = 1;
		}
		exp = getRandomBonus(exp);
		return exp * TinkersLevelingCommonConfigs.PICKAXE_EXP_MULTIPLIER.get();

	}
	private static double getExpFromEntity(Entity entity) {
		double exp;
		if (entity.getType().is(ModTags.EntityTypes.BOSS_ENTITIES)) {
			exp = getRandomBonus(200);
		} else {
			exp = getRandomBonus(5);
		}
		return exp * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get();
	}
	public static double getRandomBonus(double amount) {
		Random random = new Random();
		return random.nextInt(MathHandler.round(amount*100))/100d;
	}
}