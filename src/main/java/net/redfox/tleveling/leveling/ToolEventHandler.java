package net.redfox.tleveling.leveling;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.level.BlockEvent;
import net.redfox.tleveling.util.ModTags;

import java.util.Random;

public class ToolEventHandler {
	public static void handleMiningEvent(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		ItemStack stack = player.getMainHandItem();
		ToolLevel stackLevel = ToolExpHandler.loadLevelOnTool(stack);
		int requiredExp = (int) Math.round((500 * Math.pow(2.5f, stackLevel.getLevel())));
		ToolExpHandler.saveMiningExpOnTool(event.getState(), stack);
		double currentExp = ToolExpHandler.loadExpOnTool(stack);
		if (currentExp >= requiredExp && stackLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
			ToolLevelHandler.toolLevelUp(stack, currentExp, requiredExp, stackLevel, modifier, player);
		}
	}
	public static void handleAttackEvent(Player player, Entity deadEntity) {
		ItemStack stack = player.getMainHandItem();
		ToolLevel stackLevel = ToolExpHandler.loadLevelOnTool(stack);
		int requiredExp = ToolExpHandler.getRequiredExp(stackLevel.getLevel());
		ToolExpHandler.saveKillingExpOnTool(deadEntity, stack);
		double currentExp = ToolExpHandler.loadExpOnTool(stack);
		if (currentExp >= requiredExp && !stackLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
			ToolLevelHandler.toolLevelUp(stack, currentExp, requiredExp, stackLevel, modifier, player);
		}
	}
	public static void handleArmorEvent(Player player, float amount) {
		amount *= 3;
		if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModTags.Items.TINKERS_HELMET)) {
			handleSpecificArmorEvent(player, amount, EquipmentSlot.HEAD);
		}
		if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModTags.Items.TINKERS_CHESTPLATE)) {
			handleSpecificArmorEvent(player, amount, EquipmentSlot.CHEST);
		}
		if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModTags.Items.TINKERS_LEGGINGS)) {
			handleSpecificArmorEvent(player, amount, EquipmentSlot.LEGS);
		}
		if (player.getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.TINKERS_BOOTS)) {
			handleSpecificArmorEvent(player, amount, EquipmentSlot.FEET);
		}
	}
	private static void handleSpecificArmorEvent(Player player, float amount, EquipmentSlot slot) {
		ItemStack stack = player.getItemBySlot(slot);
		ToolLevel stackLevel = ToolExpHandler.loadLevelOnTool(stack);
		int reqStackExp = ToolExpHandler.getRequiredExp(stackLevel.getLevel());
		ToolExpHandler.saveArmorExpOnTool(getRandomArmorBonus(amount), stack);
		double helmExp = ToolExpHandler.loadExpOnTool(stack);
		if (helmExp >= reqStackExp && !stackLevel.isMaxLevel()) {
			Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
			ToolLevelHandler.toolLevelUp(stack, helmExp, reqStackExp, stackLevel, modifier, player);
		}
	}
	private static float getRandomArmorBonus(float amount) {
		Random random = new Random();
		float bonus = (random.nextInt(Math.round(amount*10)));
		bonus = bonus / 10;
		return bonus;
	}
}