package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Shadow public abstract int getArmor();

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Shadow protected float lastDamageTaken;

    @Shadow public abstract DamageTracker getDamageTracker();

    @Shadow public abstract float getHealth();

    @Shadow public abstract @Nullable LivingEntity getAttacker();

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
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void armouredDamageNegation(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        AtomicReference<Float> negationThreshold = new AtomicReference<>(this.getArmor() / 8f);
        this.getArmorItems().forEach(stack -> {
            if(stack.getItem() instanceof ArmorItem armor) {
                negationThreshold.updateAndGet(v -> v + armor.getToughness() / 4f);
            }
        });
        boolean canNegate = negationThreshold.get() >= amount;
        if(canNegate) {
            amount = 1;
        }
    }
}
