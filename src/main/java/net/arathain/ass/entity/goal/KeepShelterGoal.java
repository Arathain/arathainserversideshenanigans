package net.arathain.ass.entity.goal;

import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.TameableEntity;

public class KeepShelterGoal extends AvoidSunlightGoal {
    private final PathAwareEntity mob;

    public KeepShelterGoal(PathAwareEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        boolean raining = mob.getEntityWorld().isNight() && mob.getEntityWorld().isRaining();
        boolean isTamed = mob instanceof TameableEntity && ((TameableEntity) mob).isTamed() || mob instanceof HorseBaseEntity && ((HorseBaseEntity) mob).getOwnerUuid() != null;
        return NavigationConditions.hasMobNavigation(this.mob) && raining && !isTamed && !mob.hasPassengers();
        //return raining;
    }
}
