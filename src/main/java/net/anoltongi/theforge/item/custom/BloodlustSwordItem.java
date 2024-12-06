package net.anoltongi.theforge.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class BloodlustSwordItem extends SwordItem {
    public BloodlustSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            player.hurt(level.damageSources().generic(), 4.0F);

            CompoundTag tag = itemStack.getOrCreateTag();
            tag.putBoolean("EnhancedDamage", true);

            player.getCooldowns().addCooldown(this, 100);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (attacker instanceof Player player) {
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

            CompoundTag tag = stack.getOrCreateTag();
            if (tag != null && tag.getBoolean("EnhancedDamage")) {
                float extraDamage = 4.0F;
                target.hurt(player.level().damageSources().playerAttack(player), extraDamage);

                tag.putBoolean("EnhancedDamage", false);
            }
        }

        return result;
    }
    @Override
    public boolean isFoil(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.getBoolean("EnhancedDamage")) {
            return true;
        }
        return super.isFoil(stack);
    }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#FF02FF")));
    }
}
