package net.anoltongi.theforge.item.custom.sword;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BrassSwordItem extends BaseLevelableSwordItem {
    public BrassSwordItem(Item.Properties properties) {
        super(Tiers.DIAMOND, 0, -2.45F, properties);
    }

    protected int maxLevelBrass = 5;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 7.2;
            case 2:  return 8;
            case 3:  return 8.5;
            case 4:  return 9.5;
            case 5:  return 11;
            default: return 7.2;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.52;
            case 2:  return 1.54;
            case 3:  return 1.56;
            case 4:  return 1.58;
            case 5:  return 1.6;
            default: return 1.52;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelBrass).withStyle(ChatFormatting.GRAY));

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