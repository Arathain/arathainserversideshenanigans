package net.arathain.ass;

import draylar.omegaconfig.OmegaConfig;
import net.arathain.ass.config.ASSConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;

public class ASS implements ModInitializer {
	public static String MODID = "ass";
	public static final ASSConfig CONFIG = OmegaConfig.register(ASSConfig.class);


	@Override
	public void onInitialize() {
		ASSGamerules.init();
	}
	public static boolean isLookingAt(LivingEntity entity, LivingEntity target) {
		Vec3d vec3 = entity.getRotationVec(1.0F).normalize();
		Vec3d vec31 = new Vec3d(target.getX() - entity.getX(), target.getEyeY() - entity.getEyeY(), target.getZ() - entity.getZ());
		double lenkth = vec31.length();
		vec31 = vec31.normalize();
		double dotProduct = vec3.dotProduct(vec31);

		double range = target.isInSneakingPose() ? 1D : 6.5D;

		return dotProduct > 1.0D - range / lenkth && canSee(entity, target);

	}
	public static boolean canSee(LivingEntity entity, Entity target) {
		if (target.world != entity.world) {
			return false;
		} else {
			Vec3d vec3 = new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ());
			Vec3d vec31 = new Vec3d(target.getX(), target.getEyeY(), target.getZ());

			return entity.world.raycast(new RaycastContext(vec3, vec31, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity)).getType() == HitResult.Type.MISS;
		}
	}
}
