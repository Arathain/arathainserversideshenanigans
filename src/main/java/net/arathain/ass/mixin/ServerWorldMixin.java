package net.arathain.ass.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.server.world.ServerEntityManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLookup;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow public abstract @Nullable Entity getEntityById(int id);

    @Shadow @Final private ServerEntityManager<Entity> entityManager;

    @Shadow protected abstract EntityLookup<Entity> getEntityLookup();

    @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
    private void randomiseEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        Vec3d vec3d = entity.getPos();
        float yaw = entity.getYaw();
        float pitch = entity.getPitch();
        World world = entity.world;
        Random rand = world.getRandom();
        entity = Registry.ENTITY_TYPE.getRandom(rand).create(world);
        if (entity != null) {
            entity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, yaw, pitch);
            EntityType.loadFromEntityNbt(world, null, entity, null);
        }
        cir.cancel();
    }
}
