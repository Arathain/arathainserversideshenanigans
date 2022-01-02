package net.arathain.ass.entity.goal;

import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;

public class TakeShelterGoal extends EscapeSunlightGoal {
    public TakeShelterGoal(PathAwareEntity mob) {
        super(mob, 1.35D);
    }

    @Override
    public boolean canStart() {
        boolean raining = mob.getEntityWorld().isNight() && mob.getEntityWorld().hasRain(mob.getBlockPos());
        boolean isTamed = mob instanceof TameableEntity && ((TameableEntity) mob).isTamed() || mob instanceof HorseBaseEntity && ((HorseBaseEntity) mob).getOwnerUuid() != null;
        return raining && !isTamed && !this.mob.hasPassengers() && mob.getEntityWorld().isSkyVisible(mob.getBlockPos()) && this.targetShadedPos();
    }
}
