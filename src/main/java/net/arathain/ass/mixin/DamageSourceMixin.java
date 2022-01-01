package net.arathain.ass.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DamageSource.class)
public class DamageSourceMixin {
    @Inject(method = "arrow", at = @At(value = "RETURN"), cancellable = true)
    private static void changeCrossbowPiercingSource(PersistentProjectileEntity projectile, Entity attacker, CallbackInfoReturnable<DamageSource> cir){
        DamageSource source = cir.getReturnValue() ;
        PersistentProjectileEntity arrow = ((PersistentProjectileEntity) source.getSource());

        if(arrow != null && arrow.isShotFromCrossbow() && arrow.getPierceLevel() > 0){
            Random random = new Random();
            if(random.nextInt(5 - MathHelper.clamp(arrow.getPierceLevel(), 0, 4)) == 0) {
                DamageSourceAccessor accessor = (DamageSourceAccessor) source;
                accessor.setBypassesArmor(true);
                accessor.setExhaustion(0.0F);
                accessor.setUnblockable(true);
                cir.setReturnValue(source);
            }
        }
    }
}
