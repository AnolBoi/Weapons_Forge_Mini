package net.anoltongi.theforge.item;
import net.anoltongi.theforge.TheForgeMod;
import net.anoltongi.theforge.item.custom.sword.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheForgeMod.MOD_ID);

    public static final RegistryObject<Item> GILDED_ROSE_QUARTZ_SWORD = ITEMS.register("gilded_rose_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_BLAZE_QUARTZ_SWORD = ITEMS.register("gilded_blaze_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_ELVEN_QUARTZ_SWORD = ITEMS.register("gilded_elven_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_SUNNY_QUARTZ_SWORD = ITEMS.register("gilded_sunny_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_LAVENDER_QUARTZ_SWORD = ITEMS.register("gilded_lavender_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_MANA_QUARTZ_SWORD = ITEMS.register("gilded_mana_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_SMOKEY_QUARTZ_SWORD = ITEMS.register("gilded_smokey_quartz_sword",
            () -> new GildedSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword",
            () -> new AmethystSwordItem(new Item.Properties().durability(1200)));

    public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword",
            () -> new SilverSwordItem(new Item.Properties().durability(600)));

    public static final RegistryObject<Item> BLOODLUST_SWORD = ITEMS.register("bloodlust_sword",
            () -> new BloodlustSwordItem(new Item.Properties().durability(1800)));

    public static final RegistryObject<Item> SOULSTEALER_SWORD = ITEMS.register("soulstealer_sword",
            () -> new SoulStealerSwordItem(new Item.Properties().durability(1800)));

    public static final RegistryObject<Item> HARBINGER_OF_DESIRE_SWORD = ITEMS.register("harbinger_of_desire_sword",
            () -> new HarbingerOfDesireSwordItem(new Item.Properties().durability(1800)));

    public static final RegistryObject<Item> STAR_CALLER_SWORD = ITEMS.register("star_caller_sword",
            () -> new StarCallerSwordItem(new Item.Properties().durability(1700)));

    public static final RegistryObject<Item> CUSTOM_DIAMOND_SWORD = ITEMS.register("custom_diamond_sword",
            () -> new CustomDiamondSwordItem(new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> CUSTOM_GOLDEN_SWORD = ITEMS.register("custom_golden_sword",
            () -> new CustomGoldenSwordItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item> CUSTOM_IRON_SWORD = ITEMS.register("custom_iron_sword",
            () -> new CustomIronSwordItem(new Item.Properties().durability(600)));

    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new CustomIronSwordItem(new Item.Properties().durability(120)));

    public static final RegistryObject<Item> CUSTOM_NETHERITE_SWORD = ITEMS.register("custom_netherite_sword",
            () -> new CustomNetheriteSwordItem(new Item.Properties().durability(2200)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}