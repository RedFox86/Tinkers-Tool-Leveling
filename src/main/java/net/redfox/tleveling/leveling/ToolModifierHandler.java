package net.redfox.tleveling.leveling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.util.ModTags;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class ToolModifierHandler {

	public static Modifier toolLevelUp(ItemStack stack) {
		Modifier modifier;
		if (stack.is(ModTags.Items.TINKERS_MINING)) {
			modifier = chooseModifier(Modifier.PICKAXE_MODIFIERS);
		} else if (stack.is(ModTags.Items.TINKERS_MELEE)) {
			modifier = chooseModifier(ArrayUtils.addAll(Modifier.MELEE_MODIFIERS, Modifier.BONUS_DAMAGE_MODIFIERS));
		} else if (stack.is(ModTags.Items.TINKERS_RANGED)) {
			if (stack.is(ModTags.Items.TINKERS_CROSSBOW)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.RANGED_MODIFIERS, ArrayUtils.addAll(Modifier.BONUS_DAMAGE_MODIFIERS, Modifier.CROSSBOW_MODIFIERS)));
			} else {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.RANGED_MODIFIERS, ArrayUtils.addAll(Modifier.BONUS_DAMAGE_MODIFIERS)));
			}
		} else if (stack.is(ModTags.Items.TINKERS_ARMOR)) {
			if (stack.is(ModTags.Items.TINKERS_HELMET)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS, Modifier.HELMET_MODIFIERS));
			} else if (stack.is(ModTags.Items.TINKERS_CHESTPLATE)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS, Modifier.CHESTPLATE_MODIFIERS));
			} else if (stack.is(ModTags.Items.TINKERS_LEGGINGS)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS, Modifier.LEGGINGS_MODIFIERS));
			} else if (stack.is(ModTags.Items.TINKERS_BOOTS)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS, Modifier.BOOTS_MODIFIERS));
			} else {
				modifier = chooseModifier(new Modifier[]{});
			}
		} else {
			modifier = chooseModifier(new Modifier[]{});
			TinkersLeveling.warnLog("A tool isn't in any tag! " + stack.getDisplayName().getString());
		}
		upgradeModifier(stack, modifier);
		return modifier;
	}

	private static Modifier chooseModifier(Modifier[] specificModifiers) {
		Modifier[] modifiers = ArrayUtils.addAll(specificModifiers, Modifier.GLOBAL_MODIFIERS);
		Random random = new Random();
		return modifiers[random.nextInt(modifiers.length)];
	}

	private static void upgradeModifier(ItemStack stack, Modifier modifier) {
		CompoundTag nbt = stack.getOrCreateTag();
		ListTag ticUpgrades = nbt.contains("tic_upgrades", Tag.TAG_LIST) ? nbt.getList("tic_upgrades", Tag.TAG_COMPOUND) : new ListTag();
		if (hasPreviousModifier(stack, modifier)) {
			for (int i = 0; i < ticUpgrades.size(); i++) {
				CompoundTag entry = ticUpgrades.getCompound(i);
				if (entry.getString("name").equals("tconstruct:" + modifier.getName())) {
					int level = entry.getInt("level");
					if (level == modifier.getMax()) {
						toolLevelUp(stack);
					} else {
						entry.putInt("level", level+1);
						ticUpgrades.set(i, entry);
						nbt.put("tic_upgrades", ticUpgrades);
						stack.setTag(nbt);
					}
				}
			}
		} else {
			CompoundTag modifierUpgrade = getModifierUpgrade(modifier, 1);
			ticUpgrades.add(modifierUpgrade);
			nbt.put("tic_upgrades", ticUpgrades);
			stack.setTag(nbt);
		}
	}
	private static boolean hasPreviousModifier(ItemStack stack, Modifier modifier) {
		boolean contains = false;
		for (int i = 1; i <= modifier.getMax(); i++) {
			contains = stack.getOrCreateTag().getList("tic_upgrades", Tag.TAG_COMPOUND).contains(getModifierUpgrade(modifier, i));
			if (contains) {
				break;
			}
		}
		return contains;
	}
	private static CompoundTag getModifierUpgrade(Modifier modifier, int level) {
		CompoundTag modifierUpgrade = new CompoundTag();
		modifierUpgrade.putString("name", "tconstruct:" + modifier.getName());
		modifierUpgrade.putInt("level", level);
		return modifierUpgrade;
	}//FIX!!
}