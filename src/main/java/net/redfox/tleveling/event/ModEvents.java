package net.redfox.tleveling.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.redfox.tleveling.TinkersLeveling;
import net.redfox.tleveling.command.ExpGetCommand;
import net.redfox.tleveling.command.ExpSetCommand;
import net.redfox.tleveling.command.LevelGetCommand;
import net.redfox.tleveling.command.LevelSetCommand;
import net.redfox.tleveling.command.LevelupCommand;
import net.redfox.tleveling.util.ModKeybinds;

public class ModEvents {
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = TinkersLeveling.MOD_ID, value = Dist.CLIENT)
  public static class Client {
    @SubscribeEvent
    public static void onKeyMappingsRegister(RegisterKeyMappingsEvent event) {
      event.register(ModKeybinds.SHOW_EXP_KEY);
    }
  }
}