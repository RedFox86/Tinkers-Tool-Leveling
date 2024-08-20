package net.redfox.tleveling.leveling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.redfox.tleveling.util.ModTags;
import net.redfox.tleveling.util.NBTHandler;

public class ToolExpHandler {
	public static void saveLevelOnTool(ItemStack item, ToolLevel level) {
		NBTHandler.saveNBTData(item.getOrCreateTag(), level.getLevel(), "toolLevel");
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

	private static double getExpFromBlockState(BlockState state) {
		if (state.is(ModTags.Blocks.EXP_PICKAXE_NOR_FIVE)) {
			return 0.5f;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_TWO)) {
			return 2f;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_FIVE)) {
			return 5f;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_ADMIN)) {
			return 100000f;
		} else if (state.is(ModTags.Blocks.EXCLUDED_BLOCKS)) {
			return 0f;
		}
		return 1f;
	}
	private static double getExpFromEntity(Entity entity) {
		if (entity.getType().is(ModTags.EntityTypes.BOSS_ENTITIES)) {
			return 100f;
		}
		return 1f;
	}
}