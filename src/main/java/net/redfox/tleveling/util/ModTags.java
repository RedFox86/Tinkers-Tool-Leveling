package net.redfox.tleveling.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.redfox.tleveling.TinkersLeveling;

public class ModTags {
	public static class Blocks {

		public static final TagKey<Block> EXP_PICKAXE_NOR_FIVE = tag("exp_pickaxe_nor_five");
		public static final TagKey<Block> EXP_PICKAXE_TWO = tag("exp_pickaxe_two");
		public static final TagKey<Block> EXP_PICKAXE_FIVE = tag("exp_pickaxe_five");
		public static final TagKey<Block> EXCLUDED_BLOCKS = tag("excluded_blocks");
		public static final TagKey<Block> EXP_PICKAXE_ADMIN = tag("exp_pickaxe_admin");

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(TinkersLeveling.MOD_ID, name));
		}
	}
	public static class Items {
		public static final TagKey<Item> ALL_TOOLS = tag("tinker_tools");
		public static final TagKey<Item> TINKERS_ARMOR = tag("tinker_armor");
		public static final TagKey<Item> TINKERS_MINING = tag("tinker_mining");
		public static final TagKey<Item> TINKERS_SCYTHE = tag("tinker_scythe");
		public static final TagKey<Item> TINKERS_MELEE = tag("tinker_melee");
		public static final TagKey<Item> TINKERS_RANGED = tag("tinker_ranged");
		public static final TagKey<Item> TINKERS_CROSSBOW = tag("tinker_crossbow");
		public static final TagKey<Item> TINKERS_WEAPONS = tag("tinker_weapon");

		public static final TagKey<Item> TINKERS_HELMET = tag("tinker_helmet");
		public static final TagKey<Item> TINKERS_CHESTPLATE = tag("tinker_chestplate");
		public static final TagKey<Item> TINKERS_LEGGINGS = tag("tinker_leggings");
		public static final TagKey<Item> TINKERS_BOOTS = tag("tinker_boots");

		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(TinkersLeveling.MOD_ID, name));
		}
	}
	public static class EntityTypes {
		public static final TagKey<EntityType<?>> EXCLUDED_ENTITIES = tag("excluded_entities");
		public static final TagKey<EntityType<?>> BOSS_ENTITIES = tag("boss_entities");
		//Why must EntityTypeTags create method be private? Now I have no consistancy :(

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(TinkersLeveling.MOD_ID, name));
		}
	}
}
