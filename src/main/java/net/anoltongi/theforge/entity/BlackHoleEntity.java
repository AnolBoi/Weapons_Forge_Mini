package net.anoltongi.theforge.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class BlackHoleEntity extends Entity {
    private static final double DAMAGE_THRESHOLD = 1.0D;
    private static final double SUCK_RADIUS = 7.0D;
    private static final double SUCK_STRENGTH = 0.30D; // Higher means stronger pull
    private static final int DAMAGE_INTERVAL = 20; // ticks between damage applications
    private static final int MAX_LIFETIME = 200;
    private int damageCooldown = 0;
    private int lifetime = 0;


    public BlackHoleEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {

            applySuckingEffect();

            applyDamage();

            lifetime++;
            if (lifetime >= MAX_LIFETIME) {
                discard();
            }
        }
    }

    private void applySuckingEffect() {
        AABB area = new AABB(
                getX() - SUCK_RADIUS, getY() - SUCK_RADIUS, getZ() - SUCK_RADIUS,
                getX() + SUCK_RADIUS, getY() + SUCK_RADIUS, getZ() + SUCK_RADIUS
        );



        for (Entity entity : level().getEntities(this, area)) {
            boolean isHostileOrPlayer = (entity instanceof LivingEntity living) &&
                    (living.getType().getCategory() == net.minecraft.world.entity.MobCategory.MONSTER || entity instanceof net.minecraft.world.entity.player.Player);
            if (entity instanceof LivingEntity living && !(entity instanceof BlackHoleEntity)) {
                double dx = this.getX() - living.getX();
                double dy = this.getY() - living.getY();
                double dz = this.getZ() - living.getZ();
                double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (dist <= DAMAGE_THRESHOLD) {
                    float maxDamage = 1.0F;
                    float damage = (float) (maxDamage * (1.0 - (dist / SUCK_RADIUS)));
                }
                if (dist < 0.01) dist = 0.01;

                dx /= dist;
                dy /= dist;
                dz /= dist;

                dx *= 1.5;
                dy *= 2;
                dz *= 1.5;

                double force = SUCK_STRENGTH / (dist + 0.6);
                living.setDeltaMovement(living.getDeltaMovement().add(dx * force, dy * force, dz * force));
            }
        }
    }

    private void applyDamage() {
        if (damageCooldown > 0) {
            damageCooldown--;
            return;
        }

        AABB area = new AABB(
                getX() - SUCK_RADIUS, getY() - SUCK_RADIUS, getZ() - SUCK_RADIUS,
                getX() + SUCK_RADIUS, getY() + SUCK_RADIUS, getZ() + SUCK_RADIUS
        );

        for (Entity entity : level().getEntities(this, area)) {
            if (entity instanceof LivingEntity living && !(entity instanceof BlackHoleEntity)) {
                double dx = getX() - living.getX();
                double dy = getY() - living.getY();
                double dz = getZ() - living.getZ();
                double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

                float maxDamage = 1.0F;
                float damage = (float) (maxDamage * (1.0 - (dist / SUCK_RADIUS)));


                if (damage < 0.4F) {
                    damage = 0.4F;
                }

                living.hurt(level().damageSources().magic(), damage);
            }
        }
        damageCooldown = DAMAGE_INTERVAL;
    }
}