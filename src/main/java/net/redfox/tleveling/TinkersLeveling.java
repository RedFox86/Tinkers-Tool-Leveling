package net.redfox.tleveling;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.redfox.tleveling.config.TinkersLevelingCommonConfigs;
import net.redfox.tleveling.sound.ModSounds;
import org.slf4j.Logger;

import java.util.Random;

@Mod(TinkersLeveling.MOD_ID)
public class TinkersLeveling {
	public static final String MOD_ID = "tleveling";
	public static final Random RANDOM = new Random();
	private static final Logger LOGGER = LogUtils.getLogger();

	public TinkersLeveling() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		LOGGER.debug("Eventbus registered.");
		modEventBus.addListener(this::commonSetup);

//		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TinkersLevelingClientConfigs.SPEC, "tinkersleveling-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TinkersLevelingCommonConfigs.SPEC, "tinkersleveling-common.toml");

		MinecraftForge.EVENT_BUS.register(this);
		ModSounds.register(modEventBus);

		//Look at the Modifer class to learn how to add new modifiers
	}

	public static void debugLog(String msg) {
		LOGGER.debug(msg);
	}
	public static void warnLog(String msg) {
		LOGGER.warn(msg);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {

		}
	}
}