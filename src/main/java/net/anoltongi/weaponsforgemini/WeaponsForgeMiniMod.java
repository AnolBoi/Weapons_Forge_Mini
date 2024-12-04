package net.anoltongi.weaponsforgemini;

import com.mojang.logging.LogUtils;
import net.anoltongi.weaponsforgemini.block.ModBlocks;
import net.anoltongi.weaponsforgemini.effect.ModEffects;
import net.anoltongi.weaponsforgemini.item.ModCreativeModeTabs;
import net.anoltongi.weaponsforgemini.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeaponsForgeMiniMod.MOD_ID)
public class WeaponsForgeMiniMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "weaponsforgemini";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public WeaponsForgeMiniMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEffects.EFFECTS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            //event.accept(ModItems.temp);

        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(ModBlocks.ALEXANDRITE_BLOCK);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.AMETHYST_SWORD);
            event.accept(ModItems.GILDED_BLAZE_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_ELVEN_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_LAVENDER_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_MANA_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_ROSE_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_SMOKEY_QUARTZ_SWORD);
            event.accept(ModItems.GILDED_SUNNY_QUARTZ_SWORD);
            event.accept(ModItems.SILVER_SWORD);
            event.accept(ModItems.SOULSTEALER_SWORD);
            event.accept(ModItems.BLOODLUST_SWORD);
            event.accept(ModItems.HARBINGER_OF_DESIRE_SWORD);
        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}