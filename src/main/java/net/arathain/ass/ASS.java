package net.arathain.ass;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ASS implements ModInitializer {

	@Override
	public void onInitialize() {
		ASSGamerules.init();
	}
}
