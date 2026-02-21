package net.redfox.tleveling.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.redfox.tleveling.TinkersLeveling;

public class ModTags {
  public static class Item {

    public static final TagKey<net.minecraft.world.item.Item> LEVELABLE = tag("levelable");

    private static TagKey<net.minecraft.world.item.Item> tag(String name) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TinkersLeveling.MOD_ID, name));
    }
  }
}