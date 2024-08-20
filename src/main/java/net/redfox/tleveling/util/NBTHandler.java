package net.redfox.tleveling.util;

import net.minecraft.nbt.CompoundTag;

public class NBTHandler {
	public static void saveNBTData(CompoundTag nbt, double value, String key) {
		nbt.putDouble(key, value);
	}
	public static double loadNBTData(CompoundTag nbt, String key) {
		return nbt.getDouble(key);
	}
}