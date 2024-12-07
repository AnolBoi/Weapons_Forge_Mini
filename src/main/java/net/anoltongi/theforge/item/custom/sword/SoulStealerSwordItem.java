package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SoulStealerSwordItem extends BaseLevelableSwordItem {
    public SoulStealerSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 9, -2.45F, properties);
    }

    protected int maxLevelSoul = 5;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 9.5;
            case 2:  return 10;
            case 3:  return 10.8;
            case 4:  return 11.5;
            case 5:  return 12;
            default: return 9.5;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.55;
            case 2:  return 1.58;
            case 3:  return 1.6;
            case 4:  return 1.62;
            case 5:  return 1.65;
            default: return 1.55;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelSoul).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }



    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player) {
            if (!player.level().isClientSide) {
                float attackStrength = player.getAttackStrengthScale(0.5F);

                applyOrExtendRegen(player);

                if (attackStrength >= 1.0F) {
                    float lifestealAmount = 2.0f;
                    player.heal(lifestealAmount);
                } else {
                    double chance = 0.1;
                    RandomSource random = player.getRandom();
                    if (random.nextDouble() < chance) {
                        float lifestealAmount = 1.0f;
                        player.heal(lifestealAmount);
                    }
                }
            }
        }
        return result;
    }

    private void applyOrExtendRegen(Player player) {
        int extraDuration = 100;
        MobEffectInstance currentEffect = player.getEffect(MobEffects.REGENERATION);

        int amplifier = 0;
        int duration;
        boolean ambient = false;
        boolean visible = false;
        boolean showIcon = true;

        if (currentEffect == null) {
            duration = 100;
        } else {
            duration = Math.min(currentEffect.getDuration() + extraDuration, 600);
            amplifier = currentEffect.getAmplifier();
        }

        MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION, duration, amplifier, ambient, visible, showIcon);
        player.addEffect(regeneration);
    }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#F71EF7")));
    }
}