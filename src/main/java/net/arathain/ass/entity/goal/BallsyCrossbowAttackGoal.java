package net.arathain.ass.entity.goal;

import net.arathain.ass.mixin.PillagerEntityMixin;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.CrossbowAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.CrossbowItem;

public class BallsyCrossbowAttackGoal<T extends HostileEntity & RangedAttackMob & CrossbowUser> extends CrossbowAttackGoal<T> {
    private final T actor;
    private final double speeed;
    public BallsyCrossbowAttackGoal(T actor, double speed, float range) {
        super(actor, speed, range);
        speeed = speed;
        this.actor = actor;
    }
    @Override
    public boolean canStart() {
        return this.isValidTarget() && this.isHoldingCrossbow();
    }

    private boolean isHoldingCrossbow() {
        return EnchantmentHelper.getLevel(Enchantments.MULTISHOT, this.actor.getMainHandStack()) > 0
                && this.actor.isHolding(is -> is.getItem() instanceof CrossbowItem);
    }

    private boolean isValidTarget() {
        return this.actor.getTarget() != null && this.actor.getTarget().isAlive();
    }

    @Override
    public void tick() {
        if(actor != null) {
            this.actor.getNavigation().startMovingTo(this.actor.getTarget(), this.speeed);
        }
        super.tick();

    }
}
