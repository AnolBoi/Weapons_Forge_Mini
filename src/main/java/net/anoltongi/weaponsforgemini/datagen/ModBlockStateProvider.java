package net.anoltongi.weaponsforgemini.datagen;

import net.anoltongi.weaponsforgemini.WeaponsForgeMiniMod;
import net.anoltongi.weaponsforgemini.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WeaponsForgeMiniMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.ALEXANDRITE_BLOCK);

    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
