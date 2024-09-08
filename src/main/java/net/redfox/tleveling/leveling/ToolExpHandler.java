package net.redfox.tleveling.leveling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;
import net.redfox.tleveling.util.NBTHandler;

import java.util.Random;

public class ToolExpHandler {
	public static void saveLevelOnTool(ItemStack stack, ToolLevel level) {
		NBTHandler.saveDoubleNBT(stack.getOrCreateTag(), level.getLevel(), "toolLevel");
	}
	public static ToolLevel loadLevelOnTool(ItemStack item) {
		return ToolLevel.TOOL_LEVELS[(int) NBTHandler.loadDoubleNBT(item.getOrCreateTag(), "toolLevel")];
	}
	public static void saveMiningExpOnTool(BlockState state, ItemStack stack) {
		double blockExp = getExpFromBlockState(state);
		double toolExp = blockExp + NBTHandler.loadDoubleNBT(stack.getOrCreateTag(), "toolExp");
		NBTHandler.saveDoubleNBT(stack.getOrCreateTag(), toolExp, "toolExp");
	}
	public static void saveKillingExpOnTool(Entity entity, ItemStack item) {
		double entityExp = getExpFromEntity(entity);
		double toolExp = entityExp + NBTHandler.loadDoubleNBT(item.getOrCreateTag(), "toolExp");
		NBTHandler.saveDoubleNBT(item.getOrCreateTag(), toolExp, "toolExp");
	}
	public static void saveArmorExpOnTool(double exp, ItemStack item) {
		double toolExp = exp + NBTHandler.loadDoubleNBT(item.getOrCreateTag(), "toolExp");
		NBTHandler.saveDoubleNBT(item.getOrCreateTag(), toolExp, "toolExp");
	}
	public static double loadExpOnTool(ItemStack stack) {
		return NBTHandler.loadDoubleNBT(stack.getOrCreateTag(), "toolExp");
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
			if (TinkersLevelingCommonConfigs.ADMIN_MINING_EXP.get()) {
				exp = 100000;
			} else {
				exp = 1;
			}
		} else if (state.is(ModTags.Blocks.EXCLUDED_BLOCKS)) {
			exp = 0;
		} else {
			exp = 1;
		}
		exp = getRandomBonus(exp);
		return exp * 2 * TinkersLevelingCommonConfigs.PICKAXE_EXP_MULTIPLIER.get();
	}
	private static double getExpFromEntity(Entity entity) {
		float maxHealth;
		if (entity instanceof LivingEntity e) {
			maxHealth = e.getMaxHealth();
		} else {
			return 0;
		}
		return getRandomBonus(maxHealth) * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get();
	}
	public static double getRandomBonus(double amount) {
		if (amount == 0) {
			return 0;
		}
		Random random = new Random();
		return amount + random.nextInt(Math.abs(MathHandler.round(amount*100)))/100d;
	}
}