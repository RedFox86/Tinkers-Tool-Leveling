package net.redfox.tleveling.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.redfox.tleveling.TinkersLeveling;

public class ModTags {
  public static class Items {

    public static final TagKey<Item> LEVELABLE = tag("levelable");

    public static final TagKey<Item> MINING = tag("mining");
    public static final TagKey<Item> PICKAXE = tag("pickaxe");
    public static final TagKey<Item> SWORD = tag("sword");
    public static final TagKey<Item> COMBAT = tag("combat");
    public static final TagKey<Item> RANGED = tag("ranged");
    public static final TagKey<Item> ARMOR = tag("armor");;

    private static TagKey<Item> tag(String name) {
      return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TinkersLeveling.MOD_ID, name));
    }
  }
}