package net.arathain.ass.mixin;

import net.arathain.ass.ASS;
import net.minecraft.entity.EntityType;
import net.minecraft.util.collection.Weight;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpawnSettings.SpawnEntry.class)
public class SpawnEntryMixin {
    @Mutable
    @Shadow @Final public EntityType<?> type;

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/util/collection/Weight;II)V", at = @At("TAIL"))
    private void cursedInit(EntityType<?> type, Weight weight, int i, int k, CallbackInfo ci) {
        if(ASS.CONFIG.cursedHellMode)
        this.type = Registry.ENTITY_TYPE.getRandom(new Random());
    }
}
