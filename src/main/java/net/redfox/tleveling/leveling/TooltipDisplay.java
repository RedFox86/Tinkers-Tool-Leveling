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

  public static void insertExpTooltip(List<Component> tooltip, Player player, ItemStack stack) {
    double currentExp = ToolExp.getCurrentExp(stack);
    double neededExp = ToolExp.getRequiredExp(ToolExp.getToolLevel(stack)+1);
    double percentage = (currentExp / neededExp) * 100;

    int toolLevel = ToolExp.getToolLevel(stack);

    tooltip.subList(1, tooltip.size()).clear();

    tooltip.add(1, Component.translatable("tooltip.tleveling.tool_level", ToolLevel.LEVELS[toolLevel-1].getComponent(), Component.literal("(" + toolLevel + ")").withStyle(ChatFormatting.DARK_GRAY)));
    tooltip.add(2, Component.translatable("tooltip.tleveling.tool_exp", Component.literal(currentExp+"/"+neededExp), Component.literal(percentage+"%")));
  }
}