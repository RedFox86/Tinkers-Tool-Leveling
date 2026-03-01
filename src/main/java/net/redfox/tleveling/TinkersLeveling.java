package net.redfox.tleveling;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.redfox.tleveling.util.ModSounds;
import org.slf4j.Logger;

@Mod(TinkersLeveling.MOD_ID)
public class TinkersLeveling {
	public static final String MOD_ID = "tleveling";
	public static final Logger LOGGER = LogUtils.getLogger();

	public TinkersLeveling(FMLJavaModLoadingContext context) {
		IEventBus modEventBus = context.getModEventBus();

		MinecraftForge.EVENT_BUS.register(this);

    ModSounds.register(modEventBus);
  }
}