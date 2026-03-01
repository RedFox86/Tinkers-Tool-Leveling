package net.redfox.tleveling.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
  public static final String KEY_CATEGORY_TLEVELING = "key.category.tleveling.tleveling";
  public static final String KEY_SHOW_EXP = "key.tleveling.show_exp";

  public static final KeyMapping SHOW_EXP_KEY = new KeyMapping(KEY_SHOW_EXP, KeyConflictContext.GUI, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY_TLEVELING);

}