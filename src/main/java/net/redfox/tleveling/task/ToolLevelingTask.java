package net.redfox.tleveling.task;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.leveling.Modifier;
import net.redfox.tleveling.leveling.ToolLevel;
import net.redfox.tleveling.sound.ModSounds;
import net.redfox.tleveling.util.MathHandler;
import org.apache.commons.lang3.ArrayUtils;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolLevelingTask {
	protected Player player;
	protected Modifier[] allModifiers;
	protected ItemStack stack;
	protected CompoundTag nbt;
	protected double currentExp;
	protected int requiredExp;
	protected ToolLevel level;
	protected ToolLevelingTask(Player taskPlayer) {
		player = taskPlayer;
		stack = player.getMainHandItem();
		nbt = stack.getOrCreateTag();
		currentExp = stack.getOrCreateTag().getDouble("toolExp");
		level = ToolLevel.TOOL_LEVELS[stack.getOrCreateTag().getInt("toolLevel")];
		requiredExp = MathHandler.getRequiredExp(level.getLevel());
	}
	protected boolean upgradeTool() {
		levelTool();
		if (currentExp - requiredExp > MathHandler.getRequiredExp(level.getLevel()+1)) {
			return true;
		}
		return false;
	}
	
	public void levelTool() {
		Modifier modifier = getRandomModifier(allModifiers);
		nbt.putDouble("toolExp", currentExp-requiredExp);
		nbt.putInt("toolLevel", level.getLevel()+1);
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
	}
	private void setBonusModifiers(ItemStack stack, int input) {
		ToolStack tool = ToolStack.from(stack);
		tool.getPersistentData().setSlots(SlotType.UPGRADE, input);
	}
	private int getBonusModifiers(ItemStack stack) {
		ToolStack tool = ToolStack.from(stack);
		return tool.getPersistentData().getSlots(SlotType.UPGRADE);
	}
	private Modifier getRandomModifier(Modifier[] all) {
		Modifier[] available = all.clone();
		ArrayUtils.removeElements(available, getMaxModifiersOnTool());
		if (available.length == 0) {
			return null;
		}
		return available[TinkersLeveling.RANDOM.nextInt(available.length)];
	}
	private Modifier[] getMaxModifiersOnTool() {
		List<Modifier> modifiers = new ArrayList<>();
		ListTag toolUpgrades = nbt.contains("tic_upgrades", Tag.TAG_LIST) ? nbt.getList("tic_upgrades", Tag.TAG_COMPOUND) : new ListTag();
		for (int i = 0; i < toolUpgrades.size(); i++) {
			CompoundTag entry = toolUpgrades.getCompound(i);
			Modifier modifier = Modifier.getModifierFromID(entry.getString("name"));
			if (entry.getInt("level") == modifier.getMax()) {
				modifiers.add(modifier);
			}
		}
		return modifiers.toArray(new Modifier[0]);
	}
	private double getRandomBonus(double amount) {
		if (amount == 0) {
			return 0;
		}
		return amount + TinkersLeveling.RANDOM.nextInt(Math.abs(MathHandler.round(amount*100)))/100d;
	}
}