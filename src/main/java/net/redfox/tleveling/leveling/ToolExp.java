package net.redfox.tleveling.leveling;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.redfox.tleveling.config.JsonConfigReader;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.util.ModSounds;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Triplet;
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
  private static final double EXPONENTIAL_INCREASE = TinkersLevelingCommonConfigs.LEVELUP_INCREASE.get();
  private static final double FIRST_LEVEL_EXP_REQUIREMENT = TinkersLevelingCommonConfigs.LEVELUP_EXP_REQUIRED.get();

  private static final JsonArray MODIFIERS = JsonConfigReader.getOrCreateJsonFile("modifiers", JsonConfigReader.MODIFIERS).getAsJsonArray("values");

  public static final List<Item> BREAK_BLOCKS = new ArrayList<>();
  public static final List<Item> DAMAGE_ENTITIES = new ArrayList<>();
  public static final List<Item> TILLS = new ArrayList<>();
  public static final List<Item> SHEARS = new ArrayList<>();
  public static final List<Item> TAKE_DAMAGES = new ArrayList<>();

  public static void init() {
    for (JsonElement element : MODIFIERS) {
      for (JsonElement source : element.getAsJsonObject().get("source").getAsJsonArray()) {
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(element.getAsJsonObject().get("item").getAsString()));
        switch (source.getAsString()) {
          case "tleveling:break_block" -> BREAK_BLOCKS.add(item);
          case "tleveling:damage_entity" -> DAMAGE_ENTITIES.add(item);
          case "tleveling:till" -> TILLS.add(item);
          case "tleveling:shear" -> SHEARS.add(item);
          case "tleveling:take_damage" -> TAKE_DAMAGES.add(item);
        }
      }
    }
  }


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
    double currentExp = getCurrentExp(stack) + amount * (2 * Math.random());
    setToolExp(stack, currentExp);
    checkForToolLevelUp(player, stack, currentExp, getToolLevel(stack));
  }

  private static void checkForToolLevelUp(Player player, ItemStack stack, double currentExp, int currentLevel) {
    double requiredExp = getRequiredExp(currentLevel+1);
    if (currentExp < requiredExp) return;
    while (currentExp >= requiredExp) {
      ToolStack toolStack = ToolStack.from(stack);
      currentExp -= requiredExp;
      Component bonusModifier = TinkersLevelingCommonConfigs.LEVEL_BONUS_MODIFIER.get() != 0 &&  currentLevel % TinkersLevelingCommonConfigs.LEVEL_BONUS_MODIFIER.get() == 0 ? Component.literal("(+1 modifier)") : Component.empty();
      currentLevel++;
      String toolLevelId = currentLevel-2 < ToolLevel.LEVELS.length ? ToolLevel.LEVELS[Math.max(0, currentLevel-2)].getId() : ToolLevel.LEVELS[ToolLevel.LEVELS.length-1].getId();
      player.sendSystemMessage(Component.translatable("message.tleveling."+toolLevelId, Component.translatable(stack.getDescriptionId()), bonusModifier).withStyle(ChatFormatting.DARK_AQUA));
      toolStack.getPersistentData().addSlots(SlotType.UPGRADE, 1);

      Pair<ModifierId, String> modifierAndMessage = chooseModifier(stack);
      if (modifierAndMessage.getA() != null) {
        player.sendSystemMessage(Component.literal(modifierAndMessage.getB()).withStyle(ChatFormatting.DARK_AQUA));
        toolStack.addModifier(modifierAndMessage.getA(), 1);
      }
    }

    player.playSound(ModSounds.LEVEL_CHIME.get());
    setToolLevel(stack, currentLevel);
    setToolExp(stack, currentExp);
  }

  private static Pair<ModifierId, String> chooseModifier(ItemStack stack) {
    List<Triplet<ModifierId, Double, String>> possibleModifiers = new ArrayList<>();
    ToolStack tool = ToolStack.from(stack);
    List<ModifierEntry> toolModifiers = tool.getModifierList();
    List<ModifierId> toolModifierIds = toolModifiers.stream().map(mod -> mod.getModifier().getId()).toList();

    for (JsonElement element : MODIFIERS) {
      if (element.getAsJsonObject().get("item").getAsString().equals(stack.getItemHolder().unwrapKey().get().location().toString())) {
        for (JsonElement modifier : element.getAsJsonObject().get("modifiers").getAsJsonArray()) {
          ModifierId modifierId = ModifierId.tryParse(modifier.getAsJsonObject().get("modifier").getAsString());
          int maxLevel = modifier.getAsJsonObject().get("max").getAsInt();
          double weight = modifier.getAsJsonObject().get("weight").getAsDouble();
          String message = modifier.getAsJsonObject().get("message").getAsString();
          List<ModifierId> exceptions = modifier.getAsJsonObject().get("exceptions").getAsJsonArray().asList().stream().map(exception -> ModifierId.tryParse(exception.getAsString())).toList();
          if (checkForOverlap(toolModifierIds, exceptions)) continue;
          if (toolModifierIds.contains(modifierId)) {
            if (toolModifiers.stream().filter(thing -> thing.getModifier().getId().equals(modifierId)).findFirst().get().getLevel() == maxLevel) {
              continue;
            }
          }
          possibleModifiers.add(new Triplet<>(modifierId, weight, message));
        }
      }
    }

    if (possibleModifiers.isEmpty()) {
      return new Pair<>(null, null);
    }

    double sum = 0;
    for (var triplet : possibleModifiers) {
      sum+=triplet.getB();
    }
    double randomValue = Math.random() * sum;
    double current = 0;
    for (var triplet : possibleModifiers) {
      current += triplet.getB();
      if (randomValue <= current) {
        return new Pair<>(triplet.getA(), triplet.getC());
      }
    }
    return new Pair<>(possibleModifiers.get(possibleModifiers.size()-1).getA(), possibleModifiers.get(possibleModifiers.size()-1).getC());
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