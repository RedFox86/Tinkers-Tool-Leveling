package net.redfox.tleveling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.redfox.tleveling.leveling.ToolExpHandler;
import net.redfox.tleveling.util.MathHandler;
import net.redfox.tleveling.util.ModTags;
import net.redfox.tleveling.util.NBTHandler;

public class ExpSetCommand {
	public ExpSetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("tleveling").then(Commands.literal("set").then(Commands.argument("value", IntegerArgumentType.integer(0)).executes(ExpSetCommand::setExp))));
	}
	private static int setExp(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		Player player = source.getPlayer();
		if (player == null) {
			return -1;
		}
		ItemStack stack = player.getMainHandItem();
		if (!stack.is(ModTags.Items.ALL_TOOLS)) {
			source.sendFailure(Component.literal("This item does not have tool exp!"));
			return -1;
		}
		int value = IntegerArgumentType.getInteger(context, "value");
		source.sendSystemMessage(Component.literal("Set tool exp to " + value + "."));
		NBTHandler.saveDoubleNBT(stack.getOrCreateTag(), value, "toolExp");
		return 1;
	}
}
