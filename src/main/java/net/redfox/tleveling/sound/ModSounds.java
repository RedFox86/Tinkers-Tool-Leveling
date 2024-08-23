package net.redfox.tleveling.sound;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.redfox.tleveling.TinkersLeveling;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TinkersLeveling.MOD_ID);

	public static final RegistryObject<SoundEvent> LEVEL_CHIME = registerSoundEvent("level_chime");

	private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
		TinkersLeveling.debugLog("Sound registered: " + name);
		return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(TinkersLeveling.MOD_ID, name)));
	}

	public static void register(IEventBus eventBus) {
		TinkersLeveling.debugLog("Eventbus successfully registered.");
		SOUND_EVENTS.register(eventBus);
	}
}
