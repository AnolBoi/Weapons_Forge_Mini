package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.data.SoundDefinition;

import javax.annotation.Nullable;
import java.util.List;

public class SeismicBladeSwordItem extends BaseLevelableSwordItem {
    private static final int maxLevelSeismic = 10;
    private static final double SLAM_RADIUS = 8.0;

    public SeismicBladeSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1: return 10.0;
            case 2: return 10.5;
            case 3: return 11.0;
            case 4: return 11.5;
            case 5: return 12.0;
            case 6: return 12.5;
            case 7: return 13.0;
            case 8: return 13.5;
            case 9: return 14.0;
            case 10: return 15.0;
            default: return 10.0;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1: return 1.3;
            case 2: return 1.32;
            case 3: return 1.34;
            case 4: return 1.36;
            case 5: return 1.38;
            case 6: return 1.4;
            case 7: return 1.4;
            case 8: return 1.4;
            case 9: return 1.4;
            case 10: return 1.4;
            default: return 1.3;
        }
    }
    private int getSmashDamageForLevel(int level) {
       switch(level) {
           case 1: return 4;
           case 2: return 4;
           case 3: return 4;
           case 4: return 4;
           case 5: return 4;
           case 6: return 4;
           case 7: return 5;
           case 8: return 5;
           case 9: return 5;
           case 10: return 5;
           default: return 6;
       }

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelSeismic).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal((damage + 1) + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));

        int smashDmg = getSmashDamageForLevel(itemLevel);
        if (smashDmg > 0) {
            tooltip.add(Component.literal(smashDmg + ".0 Smash Damage").withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#F71EF7")));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            AABB area = new AABB(
                    player.getX() - SLAM_RADIUS, player.getY() - SLAM_RADIUS, player.getZ() - SLAM_RADIUS,
                    player.getX() + SLAM_RADIUS, player.getY() + SLAM_RADIUS, player.getZ() + SLAM_RADIUS
            );

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive() &&
                            (e.getType().getCategory() == MobCategory.MONSTER || e instanceof Player)
            );

            int itemLevel = getItemLevel(stack);
            int smashDmg = getSmashDamageForLevel(itemLevel);

            for (LivingEntity e : entities) {
                e.setNoGravity(true);
                e.setDeltaMovement(e.getDeltaMovement().x, 1.0, e.getDeltaMovement().z);
                e.setNoGravity(false);

                //double baseAttackDamage = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float totalDamage = (float)(smashDmg);

                DamageSource smashSource = player.level().damageSources().playerAttack(player);
                e.hurt(smashSource, totalDamage);

                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 15; i++) {
                        double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
                        double offsetY = level.random.nextDouble();
                        double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;
                        serverLevel.sendParticles(ParticleTypes.WHITE_ASH,
                                e.getX() + offsetX,
                                e.getY() + offsetY,
                                e.getZ() + offsetZ,
                                1,
                                0, 0, 0, 0.0);
                    }
                }
                level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM,
                        SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            player.getCooldowns().addCooldown(this, 300);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
