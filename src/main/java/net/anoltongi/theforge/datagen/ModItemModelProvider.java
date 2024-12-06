package net.anoltongi.theforge.datagen;

import net.anoltongi.theforge.TheForgeMod;
import net.anoltongi.theforge.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheForgeMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        handheldItem(ModItems.GILDED_ROSE_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_BLAZE_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_LAVENDER_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_MANA_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_SUNNY_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_ELVEN_QUARTZ_SWORD);
        handheldItem(ModItems.GILDED_SMOKEY_QUARTZ_SWORD);
        handheldItem(ModItems.AMETHYST_SWORD);
        handheldItem(ModItems.SILVER_SWORD);
        handheldItem(ModItems.BLOODLUST_SWORD);
        handheldItem(ModItems.SOULSTEALER_SWORD);
        handheldItem(ModItems.HARBINGER_OF_DESIRE_SWORD);
        handheldItem(ModItems.STAR_CALLER_SWORD);

    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(TheForgeMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheForgeMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(TheForgeMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
