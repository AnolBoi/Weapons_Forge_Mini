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

public class CustomWoodenSwordItem extends BaseLevelableSwordItem {
    public CustomWoodenSwordItem(Item.Properties properties) {
        super(Tiers.WOOD, 0, -2.45F, properties);
    }

    protected int maxLevelWooden = 1;

    @Override
    protected double getDamageForLevel(int level) {
        switch (level) {
            case 1:  return 4.5;
            default: return 4.5;
        }
    }

    @Override
    protected double getSpeedForLevel(int level) {
        switch (level) {
            case 1:  return 1.6;
            default: return 1.6;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Current Level: " + itemLevel + "/" + maxLevelWooden).withStyle(ChatFormatting.GRAY));

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

