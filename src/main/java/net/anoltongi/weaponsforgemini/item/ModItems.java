package net.anoltongi.weaponsforgemini.item;
import net.anoltongi.weaponsforgemini.WeaponsForgeMiniMod;
import net.anoltongi.weaponsforgemini.item.custom.AmethystSwordItem;
import net.anoltongi.weaponsforgemini.item.custom.BloodlustSwordItem;
import net.anoltongi.weaponsforgemini.item.custom.HarbingerOfDesireSwordItem;
import net.anoltongi.weaponsforgemini.item.custom.SoulStealerSwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WeaponsForgeMiniMod.MOD_ID);

    public static final RegistryObject<Item> GILDED_ROSE_QUARTZ_SWORD = ITEMS.register("gilded_rose_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_BLAZE_QUARTZ_SWORD = ITEMS.register("gilded_blaze_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_ELVEN_QUARTZ_SWORD = ITEMS.register("gilded_elven_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_SUNNY_QUARTZ_SWORD = ITEMS.register("gilded_sunny_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_LAVENDER_QUARTZ_SWORD = ITEMS.register("gilded_lavender_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_MANA_QUARTZ_SWORD = ITEMS.register("gilded_mana_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> GILDED_SMOKEY_QUARTZ_SWORD = ITEMS.register("gilded_smokey_quartz_sword",
            () -> new SwordItem(Tiers.DIAMOND, 2, -2.4f, new Item.Properties().durability(1600)));

    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword",
            () -> new AmethystSwordItem(new Item.Properties().durability(1000)));

    public static final RegistryObject<Item> SILVER_SWORD = ITEMS.register("silver_sword",
            () -> new SwordItem(Tiers.IRON, 2, -2.42f, new Item.Properties().durability(250)));

    public static final RegistryObject<Item> BLOODLUST_SWORD = ITEMS.register("bloodlust_sword",
            () -> new BloodlustSwordItem(new Item.Properties().durability(1800)));

    public static final RegistryObject<Item> SOULSTEALER_SWORD = ITEMS.register("soulstealer_sword",
            () -> new SoulStealerSwordItem(new Item.Properties().durability(1800)));

    public static final RegistryObject<Item> HARBINGER_OF_DESIRE_SWORD = ITEMS.register("harbinger_of_desire_sword",
            () -> new HarbingerOfDesireSwordItem(new Item.Properties().durability(1800)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}