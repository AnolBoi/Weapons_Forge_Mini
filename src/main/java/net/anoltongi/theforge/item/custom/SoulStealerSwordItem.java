package net.anoltongi.theforge.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class SoulStealerSwordItem extends SwordItem {
    public SoulStealerSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 9, -2.45F, properties);
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
                .withStyle(style -> style.withColor(TextColor.parseColor("#FF02FF")));
    }
}
