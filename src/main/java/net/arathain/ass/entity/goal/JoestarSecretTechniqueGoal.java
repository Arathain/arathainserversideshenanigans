package net.arathain.ass.entity.goal;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class JoestarSecretTechniqueGoal extends Goal {
    private final PathAwareEntity mob;
    private double x;
    private double y;
    private double z;
    private final double speed;

    public JoestarSecretTechniqueGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return mob.isUsingItem() && mob.getActiveItem().getItem() instanceof CrossbowItem && mob.getTarget() != null && !CrossbowItem.isCharged(mob.getActiveItem()) && EnchantmentHelper.getLevel(Enchantments.MULTISHOT, this.mob.getMainHandStack()) == 0 && this.findPosition() && mob.getTarget().getArmor() > 8;
    }
    public boolean findPosition() {
        Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, Vec3d.ofBottomCenter(this.mob.getPositionTarget()));
        if (vec3d == null) {
            return false;
        } else {
            this.x = vec3d.x;
            this.y = vec3d.y;
            this.z = vec3d.z;
            return true;
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
        this.mob.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(mob, Items.CROSSBOW));
        ((PillagerEntity) mob).setCharging(true);
        if (mob.getTarget() != null) {
            mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, mob.getTarget().getEyePos());
            this.mob.getLookControl().lookAt(mob.getTarget(), 30.0F, 30.0F);
        }
    }

    @Override
    public void tick() {
        int i = this.mob.getItemUseTime();
        ItemStack itemstack = this.mob.getActiveItem();
        if (i >= CrossbowItem.getPullTime(itemstack)) {
            this.mob.stopUsingItem();
            ((PillagerEntity) mob).setCharging(false);
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.stopUsingItem();
        ((PillagerEntity) mob).setCharging(false);
    }

    @Override
    public boolean shouldContinue() {
        return !CrossbowItem.isCharged(mob.getActiveItem()) && ((PillagerEntity) mob).isUsingItem()
                && mob.getActiveItem().getItem() instanceof CrossbowItem && !this.mob.hasPassengers();
    }
}
