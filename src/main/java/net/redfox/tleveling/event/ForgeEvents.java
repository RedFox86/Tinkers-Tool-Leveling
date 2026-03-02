package net.redfox.tleveling.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.leveling.ToolExp;
import net.redfox.tleveling.leveling.TooltipDisplay;
import net.redfox.tleveling.util.ModKeybinds;
import net.redfox.tleveling.util.ModTags;

public class ForgeEvents {
  private static boolean skip = false;
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
  public static class Client {
    @SubscribeEvent
    public static void onTooltipDisplay(ItemTooltipEvent event) {
      if (!event.getItemStack().is(ModTags.Items.LEVELABLE)) return;
      if (event.getEntity() == null) return;
      if (event.getToolTip() == null || event.getToolTip().isEmpty()) return;

      long window = Minecraft.getInstance().getWindow().getWindow();
      if (InputConstants.isKeyDown(window, InputConstants.KEY_LSHIFT) || InputConstants.isKeyDown(window, InputConstants.KEY_RSHIFT) || InputConstants.isKeyDown(window, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(window, InputConstants.KEY_RCONTROL)) return;
      if (InputConstants.isKeyDown(window, ModKeybinds.SHOW_EXP_KEY.getKey().getValue())) {
        TooltipDisplay.insertExpTooltip(event.getToolTip(), event.getItemStack());
      } else {
        TooltipDisplay.insertAltTooltip(event.getToolTip());
      }
    }
  }

  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TinkersLeveling.MOD_ID)
  public static class Server {
    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
      if (event.getLevel().isClientSide()) return;
      if (isDuplicateEvent()) return;
      ToolExp.addExpToTool(event.getPlayer(), event.getPlayer().getMainHandItem(), 5);
    }
  }

  public static boolean isDuplicateEvent() {
    skip = !skip;
    return !skip;
  }
}