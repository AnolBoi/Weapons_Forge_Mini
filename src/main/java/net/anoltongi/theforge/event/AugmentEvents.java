package net.anoltongi.theforge.event;

import net.anoltongi.theforge.item.custom.augment.AugmentManager;
import net.anoltongi.theforge.item.custom.sword.BaseLevelableSwordItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AugmentEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof BaseLevelableSwordItem)) return;

        // SCOURGE_OF_UNDEAD
        int undeadLvl = BaseLevelableSwordItem.getAugmentLevel(weapon, AugmentManager.SCOURGE_UNDEAD);
        if (undeadLvl > 0 && isUndead(event.getEntity())) {
            // for example: 10% - 50% is levels 1..6 => 0.10..0.50
            double percent = getDamageBonus(undeadLvl, 6, 0.10, 0.50);
            float newDamage = event.getAmount() * (1.0f + (float)percent);
            event.setAmount(newDamage);
        }

        // SCARABS_BANE
        int arthroLvl = BaseLevelableSwordItem.getAugmentLevel(weapon, AugmentManager.SCARABS_BANE);
        if (arthroLvl > 0 && isArthropod(event.getEntity())) {
            double percent = getDamageBonus(arthroLvl, 6, 0.10, 0.50);
            float newDamage = event.getAmount() * (1.0f + (float)percent);
            event.setAmount(newDamage);
        }

        // ABLAZE
        int ablazeLvl = BaseLevelableSwordItem.getAugmentLevel(weapon, AugmentManager.ABLAZE);
        if (ablazeLvl > 0) {
            int burnTime = 3 + (ablazeLvl - 1) * 2; // example
            event.getEntity().setSecondsOnFire(burnTime);
        }
    }

    private static double getDamageBonus(int level, int max, double basePercent, double maxPercent) {
        // If basePercent = 0.10, maxPercent = 0.50, level=6 => 0.50
        // A simple linear scale from level=1 => basePercent, up to level=max => maxPercent
        if (level <= 1) return basePercent;
        if (level >= max) return maxPercent;
        double step = (maxPercent - basePercent) / (max - 1);
        return basePercent + step * (level - 1);
    }

    private static boolean isUndead(LivingEntity entity) {
        // check if entity is undead (zombie, skeleton, etc.)
        return entity.getMobType() == MobType.UNDEAD;
    }
    private static boolean isArthropod(LivingEntity entity) {
        // check if entity is spider, cave spider, etc.
        return entity.getMobType() == MobType.ARTHROPOD;
    }
    @SubscribeEvent
    public static void onLooting(LootingLevelEvent event) {
        if (event.getDamageSource() == null) return;
        Entity attacker = event.getDamageSource().getEntity();
        if (!(attacker instanceof Player player)) return;
        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof BaseLevelableSwordItem)) return;
        int matLvl = BaseLevelableSwordItem.getAugmentLevel(weapon, AugmentManager.MATERIALIST);
        if (matLvl > 0) {
            // Add to existing looting level
            event.setLootingLevel(event.getLootingLevel() + matLvl);
        }
    }

}

