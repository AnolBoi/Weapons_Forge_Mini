package net.anoltongi.theforge.item.custom.sword;

import net.anoltongi.theforge.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class SunkenBladeSwordItem extends BaseLevelableSwordItem {
    public SunkenBladeSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    protected int maxLevelSunken = 3;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 7.5;
            case 2:  return 8;
            case 3:  return 9.5;
            default: return 7.5;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.6;
            case 2:  return 1.62;
            case 3:  return 1.65;
            default: return 1.6;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelSunken).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean didHurt = super.hurtEnemy(stack, target, attacker);

        if (didHurt && !attacker.level().isClientSide()) {
            int level = getItemLevel(stack);

            double extraPercent;
            switch (level) {
                case 1: extraPercent = 0.30; break;
                case 2: extraPercent = 0.40; break;
                case 3: extraPercent = 0.50; break;
                default: extraPercent = 0.0;
            }

            if (target.getMobType() == MobType.WATER) {
                double baseAttackDamage = attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float additionalDamage = (float)(baseAttackDamage * extraPercent);

                DamageSource source;
                if (attacker instanceof Player player) {
                    source = attacker.level().damageSources().playerAttack(player);
                } else {
                    source = attacker.level().damageSources().mobAttack(attacker);
                }

                target.hurt(source, additionalDamage);
            }
        }

        return didHurt;
    }


    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#AF30F2")));
    }
}

