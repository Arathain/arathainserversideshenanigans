package net.arathain.ass;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ASSGamerules {
    public static GameRules.Key<GameRules.BooleanRule> ADVANCED_AI;

    public static void init() {
        ADVANCED_AI = registerGamerule("biggerBrainAi", GameRuleFactory.createBooleanRule(false));
    }

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> registerGamerule(String name, GameRules.Type<T> type) {
        return GameRuleRegistry.register(name, GameRules.Category.MOBS, type);
    }
}
