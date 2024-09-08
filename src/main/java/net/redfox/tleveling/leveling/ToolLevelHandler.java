package net.redfox.tleveling.leveling;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.sound.ModSounds;
import net.redfox.tleveling.util.NBTHandler;

public class ToolLevelHandler {
	public static void toolLevelUp(ItemStack stack, double currentExp, int requiredExp, ToolLevel level, Modifier modifier, Player player) {
		NBTHandler.saveDoubleNBT(stack.getOrCreateTag(), currentExp-requiredExp, "toolExp");
		ToolExpHandler.saveLevelOnTool(stack, ToolLevel.TOOL_LEVELS[level.getLevel()+1]);
		if ((level.getLevel()+3) % 3 == 0) {
			ToolModifierHandler.setBonusModifiers(stack, ToolModifierHandler.getBonusModifiers(stack)+1);
			player.sendSystemMessage(level.getMessage(stack.getDisplayName(), true));
		} else {
			player.sendSystemMessage(level.getMessage(stack.getDisplayName(), false));
		}
		player.sendSystemMessage(modifier.getMessage());
		player.getLevel().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.LEVEL_CHIME.get(), SoundSource.MASTER, 1f, 1f);
	}
}