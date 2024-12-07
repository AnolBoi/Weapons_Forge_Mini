package net.anoltongi.theforge.event;

import net.anoltongi.theforge.item.custom.sword.BaseLevelableSwordItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if (stack.getItem() instanceof BaseLevelableSwordItem) {
            tooltip.removeIf(line -> {
                String text = line.getString().trim();

                if (text.contains("When in Main Hand")) {
                    return true;
                }

                if (text.startsWith("+") && (text.contains("Attack Damage") || text.contains("Attack Speed"))) {
                    return true;
                }
                if (text.startsWith("-") && (text.contains("Attack Damage") || text.contains("Attack Speed"))) {
                    return true;
                }
                while (!tooltip.isEmpty() && tooltip.get(tooltip.size() - 1).getString().trim().isEmpty()) {
                    tooltip.remove(tooltip.size() - 1);
                }
                return false;
            });
        }
    }
}