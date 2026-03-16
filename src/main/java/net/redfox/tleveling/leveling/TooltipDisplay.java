package net.redfox.tleveling.leveling;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.util.ModKeybinds;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;

import java.text.DecimalFormat;
import java.util.List;

public class TooltipDisplay {
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

  public static void insertAltTooltip(List<Component> tooltip) {
    int index = tooltip.indexOf(TooltipUtil.TOOLTIP_HOLD_CTRL);
    if (index == -1) index = tooltip.indexOf(TooltipUtil.TOOLTIP_HOLD_SHIFT);
    if (index == -1) return;

    MutableComponent appendComponent;
    if (ModKeybinds.SHOW_EXP_KEY.isDefault()) {
      appendComponent = Component.translatable("tooltip.tleveling.default_alt");
    } else {
      appendComponent = Component.keybind(ModKeybinds.KEY_SHOW_EXP);
    }

    tooltip.add(index, Component.translatable("tooltip.tleveling.hold_alt", appendComponent.withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.ITALIC)));
  }

  public static void insertExpTooltip(List<Component> tooltip, ItemStack stack) {
    double currentExp = ToolExp.getCurrentExp(stack);
    double neededExp = ToolExp.getRequiredExp(ToolExp.getToolLevel(stack)+1);
    double percentage = (currentExp / neededExp) * 100;
    Component percentageComponent = Component.literal(formatNumber(percentage) + "%").withStyle(switch ((int) (percentage / 25)) {
      case 0 -> ChatFormatting.RED;
      case 1 -> ChatFormatting.YELLOW;
      case 2 -> ChatFormatting.GREEN;
      case 3 -> ChatFormatting.DARK_GREEN;
      default -> ChatFormatting.WHITE;
    });

    int toolLevel = ToolExp.getToolLevel(stack);
    Component tooltipComponent = toolLevel-1 < ToolLevel.LEVELS.length ? ToolLevel.LEVELS[toolLevel-1].getComponent() : ToolLevel.LEVELS[ToolLevel.LEVELS.length-1].getComponent();

    tooltip.subList(1, tooltip.size()).clear();

    tooltip.add(Component.empty());
    tooltip.add(Component.translatable("tooltip.tleveling.tool_level", tooltipComponent, Component.literal("(" + toolLevel + ")").withStyle(ChatFormatting.DARK_GRAY)));
    tooltip.add(Component.translatable("tooltip.tleveling.tool_exp", Component.literal(formatNumber(currentExp)+"/"+formatNumber(neededExp)), percentageComponent));
  }

  private static String formatNumber(double num) {
    return DECIMAL_FORMAT.format(num);
  }
}