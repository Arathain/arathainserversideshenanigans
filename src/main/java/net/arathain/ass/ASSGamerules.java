package net.arathain.ass;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ASSGamerules {
    public static GameRules.Key<GameRules.BooleanRule> ADVANCED_AI;
    public static GameRules.Key<GameRules.BooleanRule> CURSED_HELL_MODE;
    public static GameRules.Key<GameRules.BooleanRule> NO_DAMAGE_IMMUNITY;
    public static GameRules.Key<GameRules.BooleanRule> ADVANCED_EFFECT_CLEARING;

    public static void init() {
        ADVANCED_AI = registerGamerule("biggerBrainAi", GameRuleFactory.createBooleanRule(true));
        ADVANCED_EFFECT_CLEARING = registerGamerule("advancedEffectClearing", GameRuleFactory.createBooleanRule(true));
        CURSED_HELL_MODE = registerGamerule("cursedHellMode", GameRuleFactory.createBooleanRule(false));
        NO_DAMAGE_IMMUNITY = registerGamerule("noDamageImmunity", GameRuleFactory.createBooleanRule(false));
    }

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, GameRules.Category.MOBS, type);
    }
}
