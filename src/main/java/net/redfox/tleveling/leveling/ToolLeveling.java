package net.redfox.tleveling.leveling;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.sound.ModSounds;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;
import org.apache.commons.lang3.ArrayUtils;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Random;

public class ToolLeveling {
	private final Player player;
	private final ItemStack stack;
	private double currentExp;
	private final int requiredExp;
	private final ToolLevel level;
	private float damageAmount;
	private Entity entity;
	private BlockState state;
	public ToolLeveling(Player IPlayer) {
		this.player = IPlayer;
		this.stack = player.getMainHandItem();
		this.currentExp = stack.getOrCreateTag().getDouble("toolExp");
		this.level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		this.requiredExp = MathHandler.getRequiredExp(level.getLevel());
		toolLevelUp(levelUpModifier());

	}
	public ToolLeveling(Player IPlayer, BlockState IState) {
		this.player = IPlayer;
		this.stack = player.getMainHandItem();
		if (!stack.is(ModTags.Items.TINKERS_MINING)) {
			this.entity = null;
			this.level = null;
			this.requiredExp = 0;
			return;
		}
		this.state = IState;
		this.currentExp = stack.getOrCreateTag().getDouble("toolExp");
		this.level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		this.requiredExp = MathHandler.getRequiredExp(level.getLevel());
		handleMiningEvent();
	}
	public ToolLeveling(Player IPlayer, Entity IEntity) {
		this.player = IPlayer;
		this.stack = player.getMainHandItem();
		if (!stack.is(ModTags.Items.TINKERS_WEAPONS)) {
			this.entity = null;
			this.level = null;
			this.requiredExp = 0;
			return;
		}
		this.entity = IEntity;
		this.currentExp = stack.getOrCreateTag().getDouble("toolExp");
		this.level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		this.requiredExp = MathHandler.getRequiredExp(level.getLevel());
		handleAttackEvent();
	}
	public ToolLeveling(Player IPlayer, float IAmount, ItemStack IStack) {
		if (!IStack.is(ModTags.Items.TINKERS_ARMOR) || IPlayer.isDeadOrDying()) {
			this.player = null;
			this.stack = null;
			this.level = null;
			this.requiredExp = 0;
			return;
		}
		this.player = IPlayer;
		this.stack = IStack;
		this.damageAmount = IAmount * 3;
		this.currentExp = stack.getOrCreateTag().getDouble("toolExp");
		this.level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		this.requiredExp = MathHandler.getRequiredExp(level.getLevel());
		handleSpecificArmorEvent();
	}

	public Modifier levelUpModifier() {
		Modifier modifier;
		if (stack.is(ModTags.Items.TINKERS_MINING)) {
			modifier = chooseModifier(Modifier.PICKAXE_MODIFIERS.toArray(new Modifier[0]));
		} else if (stack.is(ModTags.Items.TINKERS_MELEE)) {
			modifier = chooseModifier(ArrayUtils.addAll(Modifier.MELEE_MODIFIERS.toArray(new Modifier[0]), Modifier.BONUS_DAMAGE_MODIFIERS.toArray(new Modifier[0])));
		} else if (stack.is(ModTags.Items.TINKERS_RANGED)) {
			if (stack.is(ModTags.Items.TINKERS_CROSSBOW)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.RANGED_MODIFIERS.toArray(new Modifier[0]), ArrayUtils.addAll(Modifier.BONUS_DAMAGE_MODIFIERS.toArray(new Modifier[0]), Modifier.CROSSBOW_MODIFIERS.toArray(new Modifier[0]))));
			} else {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.RANGED_MODIFIERS.toArray(new Modifier[0]), ArrayUtils.addAll(Modifier.BONUS_DAMAGE_MODIFIERS.toArray(new Modifier[0]))));
			}
		} else if (stack.is(ModTags.Items.TINKERS_ARMOR)) {
			if (stack.is(ModTags.Items.TINKERS_HELMET)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS.toArray(new Modifier[0]), Modifier.HELMET_MODIFIERS.toArray(new Modifier[0])));
			} else if (stack.is(ModTags.Items.TINKERS_CHESTPLATE)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS.toArray(new Modifier[0]), Modifier.CHESTPLATE_MODIFIERS.toArray(new Modifier[0])));
			} else if (stack.is(ModTags.Items.TINKERS_LEGGINGS)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS.toArray(new Modifier[0]), Modifier.LEGGINGS_MODIFIERS.toArray(new Modifier[0])));
			} else if (stack.is(ModTags.Items.TINKERS_BOOTS)) {
				modifier = chooseModifier(ArrayUtils.addAll(Modifier.ARMOR_MODIFIERS.toArray(new Modifier[0]), Modifier.BOOTS_MODIFIERS.toArray(new Modifier[0])));
			} else {
				modifier = chooseModifier(new Modifier[]{});
			}
		} else {
			modifier = chooseModifier(new Modifier[]{});
			TinkersLeveling.warnLog("A tool isn't in any tag! " + stack.getDisplayName().getString());
		}
		if (modifier == null) {
			return null;
		}
		upgradeModifier(stack, modifier);
		return modifier;
	}
	public void setBonusModifiers(ItemStack stack, int input) {
		ToolStack tool = ToolStack.from(stack);
		tool.getPersistentData().setSlots(SlotType.UPGRADE, input);
	}
	public int getBonusModifiers(ItemStack stack) {
		ToolStack tool = ToolStack.from(stack);
		return tool.getPersistentData().getSlots(SlotType.UPGRADE);
	}
	private Modifier chooseModifier(Modifier[] specificModifiers) {
		Modifier[] modifiers = ArrayUtils.addAll(specificModifiers, Modifier.GLOBAL_MODIFIERS.toArray(new Modifier[0]));
		Random random = new Random();
		if (modifiers.length == 0) {
			return null;
		}
		return modifiers[random.nextInt(modifiers.length)];
	}

	private void upgradeModifier(ItemStack stack, Modifier modifier) {
		CompoundTag nbt = stack.getOrCreateTag();
		ListTag ticUpgrades = nbt.contains("tic_upgrades", Tag.TAG_LIST) ? nbt.getList("tic_upgrades", Tag.TAG_COMPOUND) : new ListTag();
		if (hasPreviousModifier(stack, modifier)) {
			for (int i = 0; i < ticUpgrades.size(); i++) {
				CompoundTag entry = ticUpgrades.getCompound(i);
				if (entry.getString("name").equals("tconstruct:" + modifier.getName())) {
					int level = entry.getInt("level");
					if (level == modifier.getMax()) {
						levelUpModifier();
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
	private boolean hasPreviousModifier(ItemStack stack, Modifier modifier) {
		boolean contains = false;
		for (int i = 1; i <= modifier.getMax(); i++) {
			contains = stack.getOrCreateTag().getList("tic_upgrades", Tag.TAG_COMPOUND).contains(getModifierUpgrade(modifier, i));
			if (contains) {
				break;
			}
		}
		return contains;
	}
	private CompoundTag getModifierUpgrade(Modifier modifier, int level) {
	
	}
	public void toolLevelUp(Modifier modifier) {
		stack.getOrCreateTag().putDouble("toolExp", currentExp-requiredExp);
		stack.getOrCreateTag().putInt("toolLevel", level.getLevel()+1);
		int levelGap = TinkersLevelingCommonConfigs.LEVEL_BONUS_MODIFIER.get();
		if (levelGap > 0 && levelGap <= 11) {
			if ((level.getLevel() + levelGap) % levelGap == 0) {
				setBonusModifiers(stack, getBonusModifiers(stack)+1);
				player.sendSystemMessage(level.getMessage(stack.getDisplayName(), true));
			} else {
				player.sendSystemMessage(level.getMessage(stack.getDisplayName(), false));
			}
		}
		player.sendSystemMessage(modifier.getMessage());
		player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.LEVEL_CHIME.get(), SoundSource.MASTER, 1f, 1f);
		if (currentExp - requiredExp > MathHandler.getRequiredExp(level.getLevel()+1)) {
			new ToolLeveling(this.player);
		}
	}
	
	public void handleMiningEvent() {
		currentExp = getExpFromBlockState(this.state) + currentExp;
		this.stack.getOrCreateTag().putDouble("toolExp", currentExp);
		if (currentExp >= requiredExp && !level.isMaxLevel()) {
			new ToolLeveling(player);
		}
	}
	public void handleAttackEvent() {
		currentExp = getExpFromEntity(entity) + currentExp;
		stack.getOrCreateTag().putDouble("toolExp", currentExp);
		if (currentExp >= requiredExp && !level.isMaxLevel()) {
			new ToolLeveling(player);
		}
	}
	private void handleSpecificArmorEvent() {
		currentExp = getRandomBonus(damageAmount) + currentExp;
		stack.getOrCreateTag().putDouble("toolExp", currentExp);
		if (currentExp >= requiredExp && !level.isMaxLevel()) {
			toolLevelUp(levelUpModifier());
		}
	}
	public double getExpFromBlockState(BlockState state) {
		double exp;
		if (state.is(ModTags.Blocks.EXP_PICKAXE_NOR_FIVE)) {
			exp = 0.5f;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_TWO)) {
			exp = 2;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_FIVE)) {
			exp = 5;
		} else if (state.is(ModTags.Blocks.EXP_PICKAXE_ADMIN)) {
			if (TinkersLevelingCommonConfigs.ADMIN_MINING_EXP.get()) {
				exp = 100000;
				TinkersLeveling.warnLog("Admin only mining exp was granted. Was this intentional?");
			} else {
				exp = 1;
			}
		} else if (state.is(ModTags.Blocks.EXCLUDED_BLOCKS)) {
			exp = 0;
		} else {
			exp = 1;
		}
		exp = getRandomBonus(exp);
		return exp * 2 * TinkersLevelingCommonConfigs.PICKAXE_EXP_MULTIPLIER.get();
	}
	public double getExpFromEntity(Entity entity) {
		float maxHealth;
		if (entity instanceof LivingEntity e) {
			maxHealth = e.getMaxHealth();
		} else {
			return 0;
		}
		return getRandomBonus(maxHealth) * TinkersLevelingCommonConfigs.KILL_EXP_MULTIPLIER.get();
	}
	public double getRandomBonus(double amount) {
		if (amount == 0) {
			return 0;
		}
		Random random = new Random();
		return amount + random.nextInt(Math.abs(MathHandler.round(amount*100)))/100d;
	}
}