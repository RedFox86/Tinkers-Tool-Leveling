package net.redfox.tleveling.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.redfox.tleveling.TinkersLeveling;

public class ModTags {
  public static class Items {

    public static final TagKey<Item> LEVELABLE = tag("levelable");

    private static TagKey<Item> tag(String name) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TinkersLeveling.MOD_ID, name));
    }
  }
}