package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.util.MathHandler;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;

import java.util.List;

public class TooltipHandler {
	public static final TextColor RED = TextColor.parseColor("#FF5555");
	public static final TextColor ORANGE = TextColor.parseColor("#FFAA00");
	public static final TextColor YELLOW = TextColor.parseColor("#FFFF55");
	public static final TextColor GREEN = TextColor.parseColor("#55FF55");
	public static final TextColor BLUE = TextColor.parseColor("#5555FF");
	public static final TextColor DARK_AQUA = TextColor.parseColor("#00AAAA");

	public static Component getColorComponent(double a, int b) {
		double value = MathHandler.getUniformPercentage(a, b);
		String message = String.valueOf(value);
		if (value >= 0 && value < 25) {
			return Component.literal("(" + message + "%)").withStyle(s -> s.withColor(RED));
		} else if (value >= 25 && value < 50) {
			return Component.literal("(" + message + "%)").withStyle(s -> s.withColor(ORANGE));
		} else if (value >= 50 && value < 75) {
			return Component.literal("(" + message + "%)").withStyle(s -> s.withColor(YELLOW));
		} else if (value >= 75 && value < 100) {
			return Component.literal("(" + message + "%)").withStyle(s -> s.withColor(GREEN));
		} else {
			return Component.literal("(" + message + "%)");
		}
	}
	public static void appendAltTooltip(List<Component> tooltip) {
		int index = tooltip.indexOf(TooltipUtil.TOOLTIP_HOLD_CTRL);
		if (index != -1) {
			index = tooltip.indexOf(TooltipUtil.TOOLTIP_HOLD_SHIFT);
		}
		if (index != -1) {
			tooltip.add(index+2, Component.translatable("tooltip.tleveling.hold_alt", Component.translatable("key.tleveling.alt").withStyle(s -> s.withColor(TextColor.parseColor("#FF55FF"))).withStyle(s -> s.withItalic(true))));
		} else {
			TinkersLeveling.warnLog("The tool didn't have a shift or ctrl! Could not place the tooltip.");
		}
	}
	public static void handleExpTooltip(ItemTooltipEvent event, Component itemName, ItemStack stack) {
		event.getToolTip().clear();
		event.getToolTip().add(itemName);
		double currentExp = stack.getOrCreateTag().getDouble("toolExp");
		ToolLevel level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		int requiredExp = MathHandler.getRequiredExp(level.getLevel());
		event.getToolTip().add(Component.translatable("tooltip.tleveling.tool_level", level.getName(), Component.literal("(" + level.getLevel() + ")").withStyle(s -> s.withColor(TextColor.parseColor("#555555")))));
		event.getToolTip().add(Component.translatable("tooltip.tleveling.tool_exp", Component.literal(MathHandler.getUniformDecimal(currentExp) + "/" + requiredExp), TooltipHandler.getColorComponent(currentExp, requiredExp)));
	}
}