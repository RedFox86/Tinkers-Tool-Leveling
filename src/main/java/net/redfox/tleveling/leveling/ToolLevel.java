package net.redfox.tleveling.leveling;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ToolLevel {
  private final String id;
  private final int level;
  private final ChatFormatting[] formatting;

  private String displayName;

  public static final ToolLevel[] LEVELS = {
      new ToolLevel("like_new", 1, ChatFormatting.GRAY),
      new ToolLevel("clumsy", 2, ChatFormatting.DARK_RED),
      new ToolLevel("comfortable", 3, ChatFormatting.GREEN),
      new ToolLevel("accustomed", 4, ChatFormatting.DARK_GREEN),
      new ToolLevel("adept", 5, ChatFormatting.DARK_AQUA),
      new ToolLevel("expert", 6, ChatFormatting.AQUA),
      new ToolLevel("master", 7, ChatFormatting.DARK_PURPLE),
      new ToolLevel("grandmaster", 8, ChatFormatting.BLUE),
      new ToolLevel("heroic", 9, ChatFormatting.YELLOW),
      new ToolLevel("legendary", 10, ChatFormatting.GOLD, ChatFormatting.BOLD),
      new ToolLevel("godlike", 11, ChatFormatting.RED, ChatFormatting.BOLD),
      new ToolLevel("awesome", 12, ChatFormatting.DARK_BLUE, ChatFormatting.BOLD),
      new ToolLevel("transcendent", 13, ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD),
  };

  public ToolLevel(String id, int level, ChatFormatting... formatting) {
    this.id = id;
    this.level = level;
    this.formatting = formatting;
  }

  public ToolLevel(String displayName, String id, int level, ChatFormatting... formatting) {
    this.displayName = displayName;
    this.id = id;
    this.level = level;
    this.formatting = formatting;
  }

  public String getId() {
    return id;
  }

  public MutableComponent getComponent() {
    if (displayName == null) return Component.translatable("level.tleveling."+id).withStyle(formatting);
    return Component.literal(displayName).withStyle(formatting);
  }
}