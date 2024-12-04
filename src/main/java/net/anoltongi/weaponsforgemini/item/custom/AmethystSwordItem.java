package net.anoltongi.weaponsforgemini.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class AmethystSwordItem extends SwordItem {
    public AmethystSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 2, -2.4f, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack))
                .withStyle(style -> style.withColor(TextColor.parseColor("#5CFFFF")));
    }
}
