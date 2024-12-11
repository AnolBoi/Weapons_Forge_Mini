package net.anoltongi.theforge.item.custom.sword;

import net.anoltongi.theforge.effect.ModEffects;
import net.anoltongi.theforge.entity.BlackHoleEntity;
import net.anoltongi.theforge.init.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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

public class CallOfTheVoidSwordItem extends BaseLevelableSwordItem {
    public CallOfTheVoidSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 9, -2.45F, properties);
    }

    protected int maxLevelVoid = 10;

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

            var look = player.getLookAngle();
            double distance = 5.0;
            double spawnX = player.getX() + look.x * distance;
            double spawnY = player.getY() + look.y * distance;
            double spawnZ = player.getZ() + look.z * distance;

            if (level.getBlockState(new BlockPos((int) spawnX, (int) spawnY, (int) spawnZ)).isAir()) {
                BlackHoleEntity blackHole = new BlackHoleEntity(ModEntities.BLACK_HOLE.get(), level);
                blackHole.setPos(spawnX, spawnY, spawnZ);
                level.addFreshEntity(blackHole);
            } else {
                player.displayClientMessage(Component.literal("Not enough space to create a black hole!"), true);
            }

            BlackHoleEntity blackHole = new BlackHoleEntity(ModEntities.BLACK_HOLE.get(), level);
            blackHole.setPos(spawnX, spawnY, spawnZ);
            level.addFreshEntity(blackHole);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelVoid).withStyle(ChatFormatting.GRAY));

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
