package net.anoltongi.theforge.item.custom.sword;

import net.anoltongi.theforge.effect.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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

public class GloomBladeSwordItem extends BaseLevelableSwordItem {
    public GloomBladeSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 9, -2.45F, properties);
    }

    protected int maxLevelGloom = 10;

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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            AABB area = new AABB(player.getX() - 10, player.getY() - 10, player.getZ() - 10,
                    player.getX() + 10, player.getY() + 10, player.getZ() + 10);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive());
            for (LivingEntity e : entities) {
                e.addEffect(new MobEffectInstance(ModEffects.FEAR.get(), 100, 0, false, false,true));
            }
            switch (getItemLevel(stack)) {
                case 1:
                case 2:
                case 3:
                case 4:
                    player.getCooldowns().addCooldown(this, 600);
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    player.getCooldowns().addCooldown(this, 500);
                    break;
                case 10:
                    player.getCooldowns().addCooldown(this, 400);
                    break;
                default:
                    player.getCooldowns().addCooldown(this, 600);
                    break;
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelGloom).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#FCC910")));
    }
}
