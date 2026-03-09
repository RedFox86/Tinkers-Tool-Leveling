package net.redfox.tleveling.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.leveling.ToolExp;
import net.redfox.tleveling.leveling.TooltipDisplay;
import net.redfox.tleveling.util.ModKeybinds;
import net.redfox.tleveling.util.ModTags;
import slimeknights.tconstruct.library.events.TinkerToolEvent;

import java.util.ArrayList;
import java.util.List;

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
      if (!ToolExp.BREAK_BLOCKS.contains(event.getPlayer().getMainHandItem().getItem())) return;

      ToolExp.addExpToTool(event.getPlayer(), event.getPlayer().getMainHandItem(), 5* TinkersLevelingCommonConfigs.PICKAXE_EXP_MULTIPLIER.get());
    }
    @SubscribeEvent
    public static void onLivingEntityHurt(LivingHurtEvent event) {
      if (event.getEntity().level().isClientSide()) return;
      if (event.getEntity() instanceof Player player) {
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.HEAD).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.HEAD), event.getAmount() * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.CHEST).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.CHEST), event.getAmount() * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.LEGS).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.LEGS), event.getAmount() * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
        if (ToolExp.TAKE_DAMAGES.contains(player.getItemBySlot(EquipmentSlot.FEET).getItem())) {
          ToolExp.addExpToTool(player, player.getItemBySlot(EquipmentSlot.FEET), event.getAmount() * TinkersLevelingCommonConfigs.ARMOR_EXP_MULTIPLIER.get());
        }
      }
    }
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
      if (event.getEntity().level().isClientSide()) return;
      if (!ToolExp.DAMAGE_ENTITIES.contains(event.getEntity().getMainHandItem().getItem())) return;

      ToolExp.addExpToTool(event.getEntity(), event.getEntity().getMainHandItem(), 5 * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get());
    }

    @SubscribeEvent
    public static void onTillFarmland(BlockEvent.BlockToolModificationEvent event) {
      if (event.isSimulated()) return;
      if (!event.getToolAction().equals(ToolActions.HOE_TILL)) return;
      if (!ToolExp.TILLS.contains(event.getHeldItemStack().getItem())) return;

      ToolExp.addExpToTool(event.getPlayer(), event.getHeldItemStack(), 5);
    }
    @SubscribeEvent
    public static void onShear(TinkerToolEvent.ToolShearEvent event) {
      ToolExp.addExpToTool(event.getPlayer(), event.getStack(), 5);
    }
  }
  public static boolean isDuplicateEvent() {
    skip = !skip;
    return !skip;
  }
}