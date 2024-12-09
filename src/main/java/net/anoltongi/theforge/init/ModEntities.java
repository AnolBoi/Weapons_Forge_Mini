package net.anoltongi.theforge.init;

import net.anoltongi.theforge.TheForgeMod;
import net.anoltongi.theforge.entity.BlackHoleEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TheForgeMod.MOD_ID);

    public static final RegistryObject<EntityType<BlackHoleEntity>> BLACK_HOLE = ENTITIES.register("black_hole",
            () -> EntityType.Builder.<BlackHoleEntity>of(BlackHoleEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F) // Adjust size as needed
                    .build("black_hole"));
}
