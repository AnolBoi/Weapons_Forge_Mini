package net.anoltongi.theforge.handler;

import net.anoltongi.theforge.effect.ModEffects;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FearLogicHandler {
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        if (entity.hasEffect(ModEffects.FEAR.get())) {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10.0);
            if (nearestPlayer != null) {
                double dx = entity.getX() - nearestPlayer.getX();
                double dz = entity.getZ() - nearestPlayer.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist < 0.001) dist = 0.001;
                dx /= dist;
                dz /= dist;

                double speed = 1.2;
                double targetX = entity.getX() + dx * 10;
                double targetZ = entity.getZ() + dz * 10;

                if (entity instanceof Mob mob) { // Java 16+ pattern matching
                    if (mob.getNavigation() != null) {
                        mob.getNavigation().moveTo(targetX, entity.getY(), targetZ, speed);
                    }
                } else {
                    entity.setDeltaMovement(dx * speed, entity.getDeltaMovement().y, dz * speed);
                }
            }
        }
    }
}