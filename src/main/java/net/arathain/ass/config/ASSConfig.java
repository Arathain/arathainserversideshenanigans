package net.arathain.ass.config;

import draylar.omegaconfig.api.Config;
import net.arathain.ass.ASS;

public class ASSConfig implements Config {
    public boolean cursedHellMode = false;
    @Override
    public String getName() {
        return "ass";
    }
    @Override
    public String getModid() {
        return ASS.MODID;
    }
    @Override
    public String getExtension() {
        return "toml";
    }
}
