package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import javax.annotation.Nullable;
import java.util.List;

public class BloodlustSwordItem extends BaseLevelableSwordItem {
    public BloodlustSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    protected int maxLevelBloodlust = 5;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 7;
            case 2:  return 8.5;
            case 3:  return 8.8;
            case 4:  return 9.5;
            case 5:  return 10;
            default: return 7;
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
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelBloodlust).withStyle(ChatFormatting.GRAY));

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
                .withStyle(style -> style.withColor(TextColor.parseColor("#FCC910")));
    }
}
