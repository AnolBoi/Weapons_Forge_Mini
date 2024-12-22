package net.anoltongi.theforge.item.custom.sword;

import net.anoltongi.theforge.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class GlacialWaltzSwordItem extends BaseLevelableSwordItem {
    public GlacialWaltzSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    private static final int maxLevelGlacial = 8;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 4;
            case 2:  return 5;
            case 3:  return 6;
            case 4:  return 6.5;
            case 5:  return 7;
            case 6: return 7.3;
            case 7: return 7.7;
            case 8: return 8;
            default: return 4;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.7;
            case 2:  return 1.73;
            case 3:  return 1.77;
            case 4:  return 1.8;
            case 5:  return 1.83;
            case 6: return 1.87;
            case 7: return 1.9;
            case 8: return 1.95;
            default: return 1.7;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelGlacial).withStyle(ChatFormatting.GRAY));

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
            if (!player.level().isClientSide()) {
                int maxDuration = 200;
                int addDuration = 100;

                MobEffectInstance existing = target.getEffect(ModEffects.FREEZING.get());
                if (existing != null) {
                    int currentDuration = existing.getDuration();
                    int newDuration = Math.min(currentDuration + addDuration, maxDuration);
                    target.addEffect(new MobEffectInstance(
                            ModEffects.FREEZING.get(),
                            newDuration,
                            existing.getAmplifier(),
                            existing.isAmbient(),
                            existing.showIcon()
                    ));
                } else {
                    target.addEffect(new MobEffectInstance(
                            ModEffects.FREEZING.get(),
                            addDuration,
                            1,
                            false,
                            false,
                            true
                    ));
                }
            }
        }

        return result;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#AF30F2")));
    }
}
