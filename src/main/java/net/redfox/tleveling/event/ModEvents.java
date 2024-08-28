package net.redfox.tleveling.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.leveling.*;
import net.redfox.tleveling.util.ModTags;

import java.util.List;

public class ModEvents {
	@Mod.EventBusSubscriber(modid = TinkersLeveling.MOD_ID)
	public static class ForgeEvents {
		private static boolean skip = true;
		@SubscribeEvent
		public static void onBlockBreak(BlockEvent.BreakEvent event) { //This event fires twice when mining with Tinker's tools! Why?
			Player player = event.getPlayer();
			if (player.getMainHandItem().is(ModTags.Items.TINKERS_MINING)) {
				if (skip) {
					skip = false;
					return;
				}
				skip = true;
				ToolEventHandler.handleMiningEvent(event);
			}
		}
		@SubscribeEvent
		public static void onLivingDeath(LivingDeathEvent event) {
			if (!(event.getSource().getEntity() instanceof Player player)) {
				return;
			}
			if (event.getEntity().getType().is(ModTags.EntityTypes.EXCLUDED_ENTITIES)) {
				return;
			}
			if (player.getMainHandItem().is(ModTags.Items.TINKERS_MELEE)) {
				ToolEventHandler.handleAttackEvent(player, event.getEntity());
			}
		}
		@SubscribeEvent
		public static void onTakeDamage(LivingHurtEvent event) {
			if (!(event.getEntity() instanceof Player player)) {
				return;
			}
			ToolEventHandler.handleArmorEvent(player, event.getAmount());
		}
		@SubscribeEvent
		public static void onToolTip(ItemTooltipEvent event) {
			if (event.getEntity() == null) {
				return;
			}
			if (!event.getItemStack().is(ModTags.Items.ALL_TOOLS)) {
				return;
			}
			KeyModifier heldKey = KeyModifier.getActiveModifier();
			if (heldKey == KeyModifier.CONTROL || heldKey == KeyModifier.SHIFT) {
				return;
			}
			ItemStack stack = event.getItemStack();
			List<Component> tooltip = event.getToolTip();
			if (heldKey == KeyModifier.ALT) {
				TooltipHandler.handleExpTooltip(event, tooltip.get(0), stack);
			} else {
				TooltipHandler.appendAltTooltip(tooltip);
			}
		}
	}
}