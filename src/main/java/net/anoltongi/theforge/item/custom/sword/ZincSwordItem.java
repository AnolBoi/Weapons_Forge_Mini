package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ZincSwordItem extends BaseLevelableSwordItem {
    public ZincSwordItem(Properties properties) {
        super(Tiers.IRON, 0, -2.4f, properties);
    }

    protected int maxLevelZinc = 5;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 4.5;
            case 2:  return 4.7;
            case 3:  return 4.9;
            case 4:  return 5.2;
            case 5:  return 5.5;
            default: return 4.5;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.6;
            case 2:  return 1.6;
            case 3:  return 1.61;
            case 4:  return 1.62;
            case 5:  return 1.65;
            default: return 1.6;
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelZinc).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#7FCF5D")));
    }
}