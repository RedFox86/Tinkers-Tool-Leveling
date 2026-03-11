package net.redfox.tleveling.leveling;

import net.minecraft.nbt.CompoundTag;

public class ToolNBT {
  public static final String CURRENT_EXP_PATH = "currentExperience";
  public static final String CURRENT_LEVEL_PATH = "currentToolLevel";

  public static int getToolLevel(CompoundTag tag) {
    if (tag.contains(CURRENT_LEVEL_PATH)) {
      return tag.getInt(CURRENT_LEVEL_PATH);
    }
    tag.putInt(CURRENT_LEVEL_PATH, 1);
    return 1;
  }

  public static double getToolExp(CompoundTag tag) {
    if (tag.contains(CURRENT_EXP_PATH)) {
      return tag.getDouble(CURRENT_EXP_PATH);
    }
    tag.putDouble(CURRENT_EXP_PATH, 0);
    return 0;
  }

  public static void setToolLevel(CompoundTag tag, int level) {
    tag.putInt(CURRENT_LEVEL_PATH, level);
  }

  public static void setToolExp(CompoundTag tag, double exp) {
    tag.putDouble(CURRENT_EXP_PATH, exp);
  }
}