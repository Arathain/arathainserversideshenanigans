package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.arathain.ass.entity.goal.KeepShelterGoal;
import net.arathain.ass.entity.goal.TakeShelterGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
    protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void inmitGoalms(CallbackInfo ci) {
        if(Objects.requireNonNull(this.getServer()).getGameRules().get(ASSGamerules.ADVANCED_AI).get()) {
            this.goalSelector.add(2, new KeepShelterGoal(this));
            this.goalSelector.add(3, new TakeShelterGoal(this));
        }
    }
}
