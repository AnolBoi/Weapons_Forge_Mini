package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class StarCallerSwordItem extends BaseLevelableSwordItem{
    public StarCallerSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4.5F, -2.45F, properties);
    }

    protected int maxLevelStar = 10;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 7.5;
            case 2:  return 7.7;
            case 3:  return 7.9;
            case 4:  return 8.1;
            case 5:  return 8.4;
            case 6:  return 8.8;
            case 7:  return 9.5;
            case 8:  return 10;
            case 9:  return 10.5;
            case 10: return 11.5;
            default: return 8.5;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.6;
            case 2:  return 1.62;
            case 3:  return 1.64;
            case 4:  return 1.66;
            case 5:  return 1.68;
            case 6:  return 1.7;
            case 7:  return 1.72;
            case 8:  return 1.75;
            case 9:  return 1.78;
            case 10: return 1.78;
            default: return 1.6;
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelStar).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            double radius = 5.0D;

            AABB area = player.getBoundingBox().inflate(radius);

            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, area,
                    (entity) -> entity != player && entity.isAlive());

            int duration = 2;
            int amplifier = 40;

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

        for (int i = 0; i < 20; i++) {
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
                .withStyle(style -> style.withColor(TextColor.parseColor("#FCC910")));
    }
}