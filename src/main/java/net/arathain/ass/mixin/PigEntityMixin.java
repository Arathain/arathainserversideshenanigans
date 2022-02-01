package net.arathain.ass.mixin;

import net.arathain.ass.ASSGamerules;
import net.arathain.ass.entity.goal.KeepShelterGoal;
import net.arathain.ass.entity.goal.TakeShelterGoal;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {
    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "initGoals", at = @At("TAIL"))
    private void inmitGoalms(CallbackInfo ci) {
        if(!(this.getServer() == null) && this.getServer().getGameRules().get(ASSGamerules.ADVANCED_AI).get()) {
            this.goalSelector.add(2, new KeepShelterGoal(this));
            this.goalSelector.add(3, new TakeShelterGoal(this));
        }
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        PassiveEntity passiveEntity = this.createChild(world, other);
        if (passiveEntity != null) {
            ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
            if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
                serverPlayerEntity = other.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, passiveEntity);
            }

            this.setBreedingAge(6000);
            other.setBreedingAge(6000);
            this.resetLoveTicks();
            other.resetLoveTicks();
            for (int i = 0; i < 2 + this.getRandom().nextInt(6); ++i) {
                passiveEntity = this.createChild(world, other);
                passiveEntity.setBaby(true);
                passiveEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
                world.spawnEntityAndPassengers(passiveEntity);
                world.sendEntityStatus(this, (byte) 18);
                if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(6) + 1));
                }
            }

        }
    }
}
