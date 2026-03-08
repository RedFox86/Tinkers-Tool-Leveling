package net.redfox.tleveling.leveling;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.config.JsonConfigReader;
import net.redfox.tleveling.util.ModSounds;
import oshi.util.tuples.Pair;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tables.block.entity.table.TinkerStationBlockEntity;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToolExp {
  private static final double EXPONENTIAL_INCREASE = 2.5f;
  private static final double FIRST_LEVEL_EXP_REQUIREMENT = 100;

  private static final JsonArray MODIFIERS = JsonConfigReader.getOrCreateJsonFile("modifiers", JsonConfigReader.MODIFIERS).getAsJsonArray("values");

  public static double getRequiredExp(int approachingLevel) {
    return FIRST_LEVEL_EXP_REQUIREMENT * Math.pow(EXPONENTIAL_INCREASE, Math.max(0, approachingLevel-2));
  }

  public static double getCurrentExp(ItemStack stack) {
    return ToolNBT.getToolExp(stack.getOrCreateTag());
  }

  public static int getToolLevel(ItemStack stack) {
    return ToolNBT.getToolLevel(stack.getOrCreateTag());
  }

  public static void setToolExp(ItemStack stack, double exp) {
    ToolNBT.setToolExp(stack.getOrCreateTag(), exp);
  }

  public static void setToolLevel(ItemStack stack, int level) {
    ToolNBT.setToolLevel(stack.getOrCreateTag(), level);
  }

  public static void addExpToTool(Player player, ItemStack stack, double amount) {
    double currentExp = getCurrentExp(stack) + amount;
    setToolExp(stack, currentExp);
    checkForToolLevelUp(player, stack, currentExp, getToolLevel(stack));
  }

  private static void checkForToolLevelUp(Player player, ItemStack stack, double currentExp, int currentLevel) {
    double requiredExp = getRequiredExp(currentLevel+1);
    if (currentExp < requiredExp) return;
    while (currentExp >= requiredExp) {
      currentExp -= requiredExp;
      currentLevel++;
      player.sendSystemMessage(Component.translatable("message.tleveling."+ToolLevel.LEVELS[Math.max(0, currentLevel-2)].getId(), stack.getDisplayName(), Component.literal("(+1 modifier)")).withStyle(ChatFormatting.DARK_AQUA));

      ModifierId modifier = ModifierId.tryParse(MODIFIERS.get(player.level().random.nextIntBetweenInclusive(0, MODIFIERS.size()-1)).getAsString());
      if (modifier != null) {
        ToolStack toolStack = ToolStack.from(stack);
        player.sendSystemMessage(Component.translatable("message.tleveling."+modifier.toLanguageKey()).withStyle(ChatFormatting.DARK_AQUA));
        toolStack.addModifier(modifier, 1);
        toolStack.getPersistentData().addSlots(SlotType.UPGRADE, 1);
      }
    }

    player.playSound(ModSounds.LEVEL_CHIME.get());
    setToolLevel(stack, currentLevel);
    setToolExp(stack, currentExp);
  }

  private static List<Pair<ModifierId, Double>> getPossibleModifiers(ItemStack stack) {
    List<ModifierId> modifiers = new ArrayList<>();
    ToolStack tool = ToolStack.from(stack);
    List<ModifierEntry> toolModifiers = tool.getModifierList();

    for (JsonElement element : MODIFIERS) {
      if (element.getAsJsonObject().get("item").getAsString().equals(stack.getItemHolder().unwrapKey().get().location().toString())) {
        for (JsonElement modifier : element.getAsJsonObject().get("modifiers").getAsJsonArray()) {
          ModifierId modifierId = ModifierId.tryParse(modifier.getAsJsonObject().get("modifier").getAsString());
          int maxLevel = element.getAsJsonObject().get("max").getAsInt();
          double weight = element.getAsJsonObject().get("weight").getAsDouble();
          List<ModifierId> exceptions = element.getAsJsonObject().get("exceptions").getAsJsonArray().asList().stream().map(exception -> ModifierId.tryParse(exception.getAsString())).toList();

        }

        if (checkForOverlap())
        break;
      }
    }
  }

  private static <E> boolean checkForOverlap(List<E> a, List<E> b) {
    for (E element : a) {
      if (b.contains(element)) {
        return true;
      }
    }
    return false;
  }
}