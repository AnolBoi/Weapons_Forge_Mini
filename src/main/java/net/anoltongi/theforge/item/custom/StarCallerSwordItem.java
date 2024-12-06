package net.anoltongi.theforge.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class StarCallerSwordItem extends SwordItem {
    public StarCallerSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 6, -2.45F, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            double radius = 5.0D;

            AABB area = player.getBoundingBox().inflate(radius);

            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area,
                    (entity) -> entity != player && entity.isAlive());

            int duration = 5;
            int amplifier = 30;

            for (LivingEntity target : nearbyEntities) {
                target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, amplifier, false, false, true));
            }

            player.getCooldowns().addCooldown(this, 100);

            level.playSound(null, player.blockPosition(), SoundEvents.ILLUSIONER_CAST_SPELL,
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if (level.isClientSide) {
            spawnSparkleParticles(level, player);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    private void spawnSparkleParticles(Level level, Player player) {
        double px = player.getX();
        double py = player.getY() + player.getEyeHeight(player.getPose());
        double pz = player.getZ();

        for (int i = 0; i < 15; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 0.5;
            double offsetY = (level.random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (level.random.nextDouble() - 0.5) * 0.5;

            level.addParticle(ParticleTypes.ASH,
                    px + offsetX, py + offsetY, pz + offsetZ,
                    0, 0.02, 0);
        }
    }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#FC9F10")));
    }
}