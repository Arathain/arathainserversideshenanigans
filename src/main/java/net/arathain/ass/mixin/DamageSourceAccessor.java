package net.arathain.ass.mixin;

import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DamageSource.class)
public interface DamageSourceAccessor {
    @Accessor
    boolean getBypassesArmor();
    @Accessor
    void setBypassesArmor(boolean bypassesArmor);

    @Accessor
    float getExhaustion();
    @Accessor
    void setExhaustion(float exhaustion);

    @Accessor
    boolean getUnblockable();
    @Accessor
    void setUnblockable(boolean unblockable);

}
