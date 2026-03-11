package net.redfox.tleveling.leveling;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.redfox.tleveling.util.ModKeybinds;
import net.redfox.tleveling.util.ModTags;
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;

import java.util.List;

public class TooltipDisplay {
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
    double percentage = Math.round((currentExp / neededExp)*100);
    Component percentageComponent = Component.literal(percentage + "%").withStyle(switch ((int) (percentage / 25)) {
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
    String raw = String.format("%.2f", num);
    String[] parts = raw.split("\\.");
    String number = parts[0];
    String result = "";
    int j = 0;
    for (int i = number.length() - 1; i >= 0; i--) {
      result = number.charAt(i) + result;
      j++;
      if (j == 3 && i > 0) {
        result = "," + result;
        j = 0;
      }
    }
    return result + "." + parts[1];
  }
}