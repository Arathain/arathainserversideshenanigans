package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.arathain.ass.entity.goal.BallsyCrossbowAttackGoal;
import net.arathain.ass.entity.goal.JoestarSecretTechniqueGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity {
    protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "initGoals", at = @At("TAIL"))
    private void addGoals(CallbackInfo ci) {
        if(!(this.getServer() == null) && Objects.requireNonNull(this.getServer()).getGameRules().get(ASSGamerules.ADVANCED_AI).get()) {
        this.goalSelector.add(1, new JoestarSecretTechniqueGoal(this, 0.9D));
        }
        this.goalSelector.add(2, new BallsyCrossbowAttackGoal<>((PillagerEntity) (Object) this, 1.0D, 3.0f));

    }
}
