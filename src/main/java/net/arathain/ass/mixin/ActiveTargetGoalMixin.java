package net.arathain.ass.mixin;

import net.arathain.ass.ASS;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {
    @Shadow
    protected LivingEntity targetEntity;
    public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }
    @Inject(at = @At("TAIL"), method = "findClosestTarget")
    protected void findTarget(CallbackInfo callbackInfo) {
        if(targetEntity != null && !ASS.isLookingAt(this.mob, targetEntity)){
            targetEntity = null;
        }
    }

}
