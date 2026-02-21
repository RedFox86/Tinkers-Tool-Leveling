package net.redfox.tleveling.leveling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.redfox.tleveling.util.ModTags;
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;

import java.util.List;

public class TooltipDisplay {
  public static void onTooltipDisplay(ItemTooltipEvent event) {
    ItemStack stack = event.getItemStack();
    if (!stack.is(ModTags.Item.LEVELABLE)) return;

    Player player = event.getEntity();
    if (player == null) return;

    List<Component> tooltip = event.getToolTip();
    if (tooltip == null) return;

    tooltip.indexOf()
  }
}