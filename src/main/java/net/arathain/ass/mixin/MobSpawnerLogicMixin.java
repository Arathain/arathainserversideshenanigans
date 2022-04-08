package net.arathain.ass.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @Shadow private MobSpawnerEntry spawnEntry;

    @Inject(method = "setEntityId", at = @At("TAIL"))
    private void cursedSetEntityId(EntityType<?> type, CallbackInfo ci) {
        this.spawnEntry.getNbt().putString("id", Registry.ENTITY_TYPE.getRandom(new Random()).toString());
    }
}
