package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract boolean isBlocking();
    @Shadow public float forwardSpeed;

    @Shadow public abstract ItemStack getMainHandStack();

    @Inject(method = "tick", at = @At("HEAD"))
    private void timck(CallbackInfo ci) {
        if(!(this.getServer() == null) && Objects.requireNonNull(this.getServer()).getGameRules().get(ASSGamerules.NO_DAMAGE_IMMUNITY).get())
        timeUntilRegen = 0;
    }

    @Inject(method = "tryAttack", at = @At("HEAD"))
    private void tryAttamck(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if(!(this.getServer() == null) && Objects.requireNonNull(this.getServer()).getGameRules().get(ASSGamerules.NO_DAMAGE_IMMUNITY).get())
        target.timeUntilRegen = 0;
    }
}
