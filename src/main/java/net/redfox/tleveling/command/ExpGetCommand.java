package net.redfox.tleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.ToolExpHandler;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;

public class ExpGetCommand {
	public ExpGetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").then(Commands.literal("get").executes((command) -> getExp(command.getSource()))));
	}
	private int getExp(CommandSourceStack source) {
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!stack.is(ModTags.Items.ALL_TOOLS)) {
			source.sendFailure(Component.literal("This item does not have tool exp!"));
			return -1;
		}
		int exp = MathHandler.round(ToolExpHandler.loadExpOnTool(stack));
		int requiredExp = MathHandler.getRequiredExp(ToolExpHandler.loadLevelOnTool(stack).getLevel());
		source.sendSystemMessage(Component.literal("This tool has " + exp + " exp out of required " + requiredExp + "."));
		return exp;
	}
}
