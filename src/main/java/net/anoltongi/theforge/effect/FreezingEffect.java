package net.anoltongi.theforge.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FreezingEffect extends MobEffect {

    public FreezingEffect() {
        super(MobEffectCategory.HARMFUL, 0x99D9EA);
        addAttributeModifier(
                net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED,
                "3fa03f66-4bd4-11ec-81d3-0242ac130003",
                -0.4D,
                net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        Level level = living.level();
        if (!level.isClientSide()) {
            if (level.getGameTime() % 20 == 0) {
                float damage = 1.0F + (amplifier * 0.5F);
                living.hurt(level.damageSources().magic(), damage);
            }
        }
    }
}