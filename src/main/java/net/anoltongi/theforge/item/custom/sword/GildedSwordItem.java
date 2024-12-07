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

public class GildedSwordItem extends BaseLevelableSwordItem {
    public GildedSwordItem(Properties props) {
        super(Tiers.DIAMOND, 0f, -2.4f, props);
    }

    protected int maxLevelGilded = 10;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 5.0;
            case 2:  return 5.3;
            case 3:  return 5.6;
            case 4:  return 6.1;
            case 5:  return 6.5;
            case 6:  return 7.0;
            case 7:  return 7.5;
            case 8:  return 8.0;
            case 9:  return 9.0;
            case 10: return 11.0;
            default: return 5.0;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.6;
            case 2:  return 1.61;
            case 3:  return 1.63;
            case 4:  return 1.64;
            case 5:  return 1.65;
            case 6:  return 1.66;
            case 7:  return 1.67;
            case 8:  return 1.68;
            case 9:  return 1.69;
            case 10: return 1.7;
            default: return 1.6;
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelGilded).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal(" "));

        double damage = getDamageForLevel(itemLevel);
        tooltip.add(Component.literal(damage+1 + " Attack Damage ").withStyle(ChatFormatting.DARK_GREEN));

        double finalSpeed = getSpeedForLevel(itemLevel);
        tooltip.add(Component.literal(String.format("%.2f", finalSpeed) + " Attack Speed ").withStyle(ChatFormatting.DARK_GREEN));
    }
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#54CBF0")));
    }
}