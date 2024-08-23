package net.redfox.tleveling.leveling;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.redfox.tleveling.util.MathHandler;

public class TooltipHandler {
	public static final TextColor RED = TextColor.parseColor("#FF5555");
	public static final TextColor ORANGE = TextColor.parseColor("#FFAA00");
	public static final TextColor YELLOW = TextColor.parseColor("#FFFF55");
	public static final TextColor GREEN = TextColor.parseColor("#55FF55");

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
}