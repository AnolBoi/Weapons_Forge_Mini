package net.anoltongi.theforge.item.custom.sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import java.util.UUID;

public abstract class BaseLevelableSwordItem extends SwordItem {
    private static final UUID DAMAGE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID SPEED_UUID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    public BaseLevelableSwordItem(Tier tier,float attackDamage, float attackSpeed, Properties properties) {
        super(tier, (int) attackDamage, attackSpeed, properties);
    }

    public static int getItemLevel(ItemStack stack) {
        if (!stack.hasTag()) return 1;
        int lvl = stack.getTag().getInt("ItemLevel");
        return lvl < 1 ? 1 : lvl;
    }

    public static void setItemLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt("ItemLevel", level);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        if (!stack.hasTag()) {
            setItemLevel(stack, 1);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        stack.getOrCreateTag().putInt("HideFlags", 2);
        if (!level.isClientSide && selected && stack.getItem() instanceof BaseLevelableSwordItem) {
            if (!stack.hasTag()) {
                setItemLevel(stack, 1);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        super.getAttributeModifiers(slot, stack).entries().stream()
                .filter(entry -> !entry.getKey().equals(Attributes.ATTACK_DAMAGE) && !entry.getKey().equals(Attributes.ATTACK_SPEED))
                .forEach(entry -> builder.put(entry.getKey(), entry.getValue()));

        int level = getItemLevel(stack);

        double totalDamage = getDamageForLevel(level);
        double finalSpeed = getSpeedForLevel(level);

        double speedModifier = finalSpeed - 4.0;

        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(DAMAGE_UUID, "Level-based damage", totalDamage, AttributeModifier.Operation.ADDITION));

        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(SPEED_UUID, "Level-based attack speed", speedModifier, AttributeModifier.Operation.ADDITION));

        return builder.build();
    }

    protected abstract double getDamageForLevel(int level);
    protected abstract double getSpeedForLevel(int level);


}
