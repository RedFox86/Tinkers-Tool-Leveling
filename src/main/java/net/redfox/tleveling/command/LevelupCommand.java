package net.redfox.tleveling.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.*;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;
import net.redfox.tleveling.util.NBTHandler;

public class LevelupCommand {
	public LevelupCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").requires(commandSource -> commandSource.hasPermission(2)).then(Commands.literal("levelup").executes(LevelupCommand::levelup)));
	}
	private static int levelup(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!stack.is(ModTags.Items.ALL_TOOLS)) {
			source.sendFailure(Component.literal("This item cannot level up!"));
			return -1;
		}
		ToolLevel level = ToolExpHandler.loadLevelOnTool(stack);
		int requiredExp = MathHandler.getRequiredExp(level.getLevel());
		NBTHandler.saveDoubleNBT(stack.getOrCreateTag(), requiredExp, "toolExp");
		Modifier modifier = ToolModifierHandler.toolLevelUp(stack);
		ToolLevelHandler.toolLevelUp(stack, requiredExp, requiredExp, level, modifier, source.getPlayer());
		source.sendSystemMessage(Component.literal("Tool successfully leveled up."));
		return Command.SINGLE_SUCCESS;
	}
}
