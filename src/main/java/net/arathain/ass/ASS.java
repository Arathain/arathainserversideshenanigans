package net.arathain.ass;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ASS implements ModInitializer {
	public static String MODID = "ass";

	@Override
	public void onInitialize() {
		ASSGamerules.init();
	}
}
