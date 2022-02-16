package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkItemMixin {
    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"), cancellable = true)
    private void finishUse(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(world.getGameRules().getBoolean(ASSGamerules.ADVANCED_EFFECT_CLEARING)) {
            user.removeStatusEffect(StatusEffects.NAUSEA);
            user.removeStatusEffect(StatusEffects.BLINDNESS);
            user.removeStatusEffect(StatusEffects.NIGHT_VISION);
            user.removeStatusEffect(StatusEffects.HUNGER);
            user.removeStatusEffect(StatusEffects.SATURATION);
            user.removeStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
            user.removeStatusEffect(StatusEffects.BAD_OMEN);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 1));
            cir.setReturnValue(stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack);
            cir.cancel();
        }
    }
}
