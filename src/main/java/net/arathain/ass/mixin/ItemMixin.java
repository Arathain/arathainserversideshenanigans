package net.arathain.ass.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void eat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem().equals(Items.SPIDER_EYE)) {
            user.removeStatusEffect(StatusEffects.WEAKNESS);
            user.removeStatusEffect(StatusEffects.MINING_FATIGUE);
            user.removeStatusEffect(StatusEffects.SLOWNESS);
        }
        if(stack.getItem().equals(Items.ROTTEN_FLESH)) {
            user.removeStatusEffect(StatusEffects.JUMP_BOOST);
            user.removeStatusEffect(StatusEffects.SPEED);
        }
        if(stack.getItem().equals(Items.GOLDEN_APPLE) || stack.getItem().equals(Items.GOLDEN_CARROT) || stack.getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)) {
            user.removeStatusEffect(StatusEffects.WITHER);
        }
    }
}
