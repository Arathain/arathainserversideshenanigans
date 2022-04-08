package net.arathain.ass.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {
    @Inject(method = "onUserDamaged", at = @At("HEAD"), cancellable = true)
    public void betterThorns(LivingEntity user, Entity attacker, int level, CallbackInfo ci) {
        Random random = user.getRandom();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.THORNS, user);
        user.getWorld().getOtherEntities(user, Box.of(user.getPos(), 2.5f, 2.5f, 2.5f)).forEach(entity -> {
            entity.damage(DamageSource.thorns(user), getDmgAmount(level, random));
        });
        if (entry != null) {
            entry.getValue().damage(2, user, entity -> entity.sendEquipmentBreakStatus((EquipmentSlot)((Object)((Object)entry.getKey()))));
        }
        user.setVelocity(Vec3d.ZERO);
        user.world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1, 1, true);
        ci.cancel();
    }
    private static int getDmgAmount(int level, Random random) {
        if (level > 10) {
            return level - 10;
        }
        return 1 + level * random.nextInt(3);
    }
}
