package net.redfox.tleveling.leveling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;

import java.util.Random;

public class ToolEventHandler {
	public static void handleMiningEvent(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		ItemStack stack = player.getMainHandItem();
		ToolLevel level = ToolExpHandler.loadLevelOnTool(stack);
		int requiredExp = (int) Math.round((500 * Math.pow(2.5f, level.getLevel())));
		ToolExpHandler.saveMiningExpOnTool(event.getState(), stack);
		double currentExp = ToolExpHandler.loadExpOnTool(stack);
		if (level.isMaxLevel()) {
			return;
		}
		if (currentExp >= requiredExp) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
			ToolLevelHandler.toolLevelUp(stack, currentExp, requiredExp, level, modifier, player);
		}
	}
	public static void handleAttackEvent(Player player, Entity deadEntity) {
		ItemStack stack = player.getMainHandItem();
		ToolLevel level = ToolExpHandler.loadLevelOnTool(stack);
		int requiredExp = (int) Math.round((500 * Math.pow(2.5f, level.getLevel())));
		ToolExpHandler.saveKillingExpOnTool(deadEntity, stack);
		double currentExp = ToolExpHandler.loadExpOnTool(stack);
		if (level.isMaxLevel()) {
			return;
		}
		if (currentExp >= requiredExp) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
			ToolLevelHandler.toolLevelUp(stack, currentExp, requiredExp, level, modifier, player);
		}
	}
	public static void handleArmorEvent(Player player, float amount) {
		ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
		ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
		ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
		ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
		ToolLevel helmLevel = ToolExpHandler.loadLevelOnTool(helmet);
		ToolLevel chestLevel = ToolExpHandler.loadLevelOnTool(chestplate);
		ToolLevel legLevel = ToolExpHandler.loadLevelOnTool(leggings);
		ToolLevel bootLevel = ToolExpHandler.loadLevelOnTool(boots);
		int reqHelmExp = Math.round((float)(500 * Math.pow(2.5f, helmLevel.getLevel())));
		int reqChestExp = Math.round((float)(500 * Math.pow(2.5f, chestLevel.getLevel())));
		int reqLegExp = Math.round((float)(500 * Math.pow(2.5f, legLevel.getLevel())));
		int reqBootExp = Math.round((float)(500 * Math.pow(2.5f, bootLevel.getLevel())));
		ToolExpHandler.saveArmorExpOnTool(getRandomArmorBonus(amount), helmet);
		ToolExpHandler.saveArmorExpOnTool(getRandomArmorBonus(amount), chestplate);
		ToolExpHandler.saveArmorExpOnTool(getRandomArmorBonus(amount), leggings);
		ToolExpHandler.saveArmorExpOnTool(getRandomArmorBonus(amount), boots);
		double helmExp = ToolExpHandler.loadExpOnTool(helmet);
		double chestExp = ToolExpHandler.loadExpOnTool(chestplate);
		double legExp = ToolExpHandler.loadExpOnTool(leggings);
		double bootExp = ToolExpHandler.loadExpOnTool(boots);
		if (helmExp >= reqHelmExp && !helmLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(helmet);
			ToolLevelHandler.toolLevelUp(helmet, helmExp, reqHelmExp, helmLevel, modifier, player);
		}
		if (chestExp >= reqChestExp && !chestLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(chestplate);
			ToolLevelHandler.toolLevelUp(chestplate, chestExp, reqChestExp, chestLevel, modifier, player);
		}
		if (legExp >= reqLegExp && !legLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(leggings);
			ToolLevelHandler.toolLevelUp(leggings, legExp, reqLegExp, legLevel, modifier, player);
		}
		if (bootExp >= reqBootExp && !bootLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(boots);
			ToolLevelHandler.toolLevelUp(boots, bootExp, reqBootExp, bootLevel, modifier, player);
		}
	}
	private static float getRandomArmorBonus(float amount) {
		Random random = new Random();
		float bonus = (random.nextInt(Math.round(amount*10)));
		bonus = bonus / 10;
		return bonus;
	}
}