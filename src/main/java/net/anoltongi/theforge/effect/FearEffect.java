package net.anoltongi.theforge.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FearEffect extends MobEffect {
    public FearEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
