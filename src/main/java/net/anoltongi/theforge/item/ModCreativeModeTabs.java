package net.anoltongi.theforge.item;

import net.anoltongi.theforge.TheForgeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheForgeMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("theforge_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GILDED_ROSE_QUARTZ_SWORD.get()))
                    .title(Component.translatable("creativetab.theforge"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.AMETHYST_SWORD.get());
                        output.accept(ModItems.GILDED_BLAZE_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_ELVEN_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_LAVENDER_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_MANA_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_ROSE_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_SMOKEY_QUARTZ_SWORD.get());
                        output.accept(ModItems.GILDED_SUNNY_QUARTZ_SWORD.get());
                        output.accept(ModItems.SILVER_SWORD.get());
                        output.accept(ModItems.BLOODLUST_SWORD.get());
                        output.accept(ModItems.SOULSTEALER_SWORD.get());
                        output.accept(ModItems.HARBINGER_OF_DESIRE_SWORD.get());
                        output.accept(ModItems.STAR_CALLER_SWORD.get());
                        output.accept(ModItems.GLOOM_BLADE_SWORD.get());
                        output.accept(ModItems.CUSTOM_WOODEN_SWORD.get());
                        output.accept(ModItems.CUSTOM_STONE_SWORD.get());
                        output.accept(ModItems.COPPER_SWORD.get());
                        output.accept(ModItems.ZINC_SWORD.get());
                        output.accept(ModItems.SILVER_SWORD.get());
                        output.accept(ModItems.CUSTOM_IRON_SWORD.get());
                        output.accept(ModItems.CUSTOM_GOLDEN_SWORD.get());
                        output.accept(ModItems.CUSTOM_DIAMOND_SWORD.get());
                        output.accept(ModItems.BRASS_SWORD.get());
                        output.accept(ModItems.CUSTOM_NETHERITE_SWORD.get());

                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
