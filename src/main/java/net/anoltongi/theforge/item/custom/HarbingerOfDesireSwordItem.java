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

public class HarbingerOfDesireSwordItem extends SwordItem {
        public HarbingerOfDesireSwordItem(Properties properties) {
            super(Tiers.DIAMOND, 9, -2.45F, properties);
        }

        @Override
        public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            boolean result = super.hurtEnemy(stack, target, attacker);

            if (attacker instanceof Player player) {
                if (!player.level().isClientSide) {
                    float attackStrength = player.getAttackStrengthScale(0.5F);

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
                    double strengthChance = 0.25;
                    RandomSource random = player.getRandom();
                    if (random.nextDouble() < strengthChance) {
                        int duration = 200;
                        int amplifier = 0;
                        boolean ambient = false;
                        boolean visible = false;
                        boolean showIcon = true;

                        MobEffectInstance strengthEffect = new MobEffectInstance(
                                MobEffects.DAMAGE_BOOST,
                                duration,
                                amplifier,
                                ambient,
                                visible,
                                showIcon);
                        player.addEffect(strengthEffect);
                    }
                }
            }
            return result;
        }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#FF02FF")));
    }
}
