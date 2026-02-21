package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.util.MathHandler;
import org.w3c.dom.Text;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;

import java.util.List;

public class TooltipHandler {
	public static final TextColor GRAY = TextColor.parseColor("#AAAAAA");
	public static final TextColor DARK_GRAY = TextColor.parseColor("#555555");
	public static final TextColor DARK_RED = TextColor.parseColor("AA0000");
	public static final TextColor RED = TextColor.parseColor("#FF5555");
	public static final TextColor ORANGE = TextColor.parseColor("#FFAA00");
	public static final TextColor YELLOW = TextColor.parseColor("#FFFF55");
	public static final TextColor GREEN = TextColor.parseColor("#55FF55");
	public static final TextColor BLUE = TextColor.parseColor("#5555FF");
	public static final TextColor DARK_AQUA = TextColor.parseColor("#00AAAA");
	public static final TextColor DARK_GREEN = TextColor.parseColor("#00AA00");

	public static Component getColoredPercentageComponent(double a, int b) {
		double value = MathHandler.getUniformPercentage(a, b);
		int percentage = (int)(value/25);
		return Component.literal("(" + value + ")").withStyle(s ->
				s.withColor(switch (percentage) {
						case 0 -> DARK_RED;
						case 1 -> RED;
						case 2 -> YELLOW;
						case 3 -> GREEN;
						case 4 -> DARK_GREEN;
						default -> GRAY;
				}));
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
	public static void handleExpTooltip(List<Component> tooltip, ItemStack stack) {
		tooltip.clear();
		tooltip.add(tooltip.get(0));
		double currentExp = stack.getOrCreateTag().getDouble("toolExp");
		ToolLevel level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		int requiredExp = MathHandler.getRequiredExp(level.getLevel());
		tooltip.add(Component.translatable("tooltip.tleveling.tool_level", level.getName(), Component.literal("(" + level.getLevel() + ")").withStyle(s -> s.withColor(TextColor.parseColor("#555555")))));
		tooltip.add(Component.translatable("tooltip.tleveling.tool_exp", Component.literal(MathHandler.getUniformLongDecimal(MathHandler.getUniformDecimal(currentExp)) + "/" + MathHandler.getUniformLongDecimal(requiredExp)), TooltipHandler.getColoredPercentageComponent(currentExp, requiredExp)));
	}
}