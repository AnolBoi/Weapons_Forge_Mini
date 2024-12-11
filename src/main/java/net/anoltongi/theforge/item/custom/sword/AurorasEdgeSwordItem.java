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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AurorasEdgeSwordItem extends BaseLevelableSwordItem {
    public AurorasEdgeSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.45F, properties);
    }

    private static final int maxLevelAurora = 5;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 7;
            case 2:  return 7.5;
            case 3:  return 7.8;
            case 4:  return 8;
            case 5:  return 8.5;
            default: return 7;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.8;
            case 2:  return 1.83;
            case 3:  return 1.85;
            case 4:  return 1.88;
            case 5:  return 1.9;
            default: return 1.8;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelAurora).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            AABB area = new AABB(player.getX() - 10, player.getY() - 10, player.getZ() - 10,
                    player.getX() + 10, player.getY() + 10, player.getZ() + 10);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive() && (e.getType().getCategory() == MobCategory.MONSTER));
            for (LivingEntity e : entities) {
                e.addEffect(new MobEffectInstance(ModEffects.FREEZING.get(), 160, 1, false, false,true));

                ServerLevel serverLevel = (ServerLevel) level;
                for (int i = 0; i < 10; i++) {
                    double offsetX = (level.random.nextDouble() - 0.5) * 0.5;
                    double offsetY = (level.random.nextDouble());
                    double offsetZ = (level.random.nextDouble() - 0.5) * 0.5;

                    serverLevel.sendParticles(ParticleTypes.SNOWFLAKE,
                            e.getX() + offsetX,
                            e.getY() + offsetY,
                            e.getZ() + offsetZ,
                            3,
                            0, 0, 0, 0.0
                    );
                }
            }
            player.getCooldowns().addCooldown(this, 200);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#FCC910")));
    }
}


