package net.anoltongi.theforge.item.custom.sword;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtUtils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.UUID;

public abstract class BaseLevelableSwordItem extends SwordItem {
    // Unique UUIDs for attribute modifiers
    private static final UUID DAMAGE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID SPEED_UUID  = UUID.fromString("00000000-0000-0000-0000-000000000002");

    public BaseLevelableSwordItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        // The constructor calls super with int attackDamage
        // so we cast 'attackDamage' to int. Just note that any decimal portion is lost.
        super(tier, (int) attackDamage, attackSpeed, properties);
    }

    // region: LEVEL LOGIC

    /**
     * Returns the current level of the sword. Defaults to 1 if no tag is present or value < 1.
     */
    public static int getItemLevel(ItemStack stack) {
        if (!stack.hasTag()) return 1;
        int lvl = stack.getTag().getInt("ItemLevel");
        return lvl < 1 ? 1 : lvl;
    }

    /**
     * Sets the sword's level in NBT (minimum 1).
     */
    public static void setItemLevel(ItemStack stack, int level) {
        level = Math.max(level, 1);
        stack.getOrCreateTag().putInt("ItemLevel", level);
    }

    // endregion


    // region: AUGMENT LOGIC

    /**
     * Retrieves all augments from the sword's "Augments" compound:
     * {
     *   "Augments": {
     *      "scourge_undead": 2,
     *      "scarabs_bane": 4
     *   }
     * }
     * as a map of (augmentId -> level).
     */
    public static Map<String, Integer> getAugments(ItemStack stack) {
        Map<String, Integer> result = new HashMap<>();
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Augments", Tag.TAG_COMPOUND)) {
            CompoundTag augTag = tag.getCompound("Augments");
            for (String key : augTag.getAllKeys()) {
                result.put(key, augTag.getInt(key));
            }
        }
        return result;
    }

    /**
     * Returns the level for a specific augmentId. If none present, returns 0.
     */
    public static int getAugmentLevel(ItemStack stack, String augmentId) {
        return getAugments(stack).getOrDefault(augmentId, 0);
    }

    /**
     * Sets (or updates) the augment level for a given augmentId in the sword's NBT.
     * If level <= 0, consider removing it or ignoring.
     */
    public static void setAugmentLevel(ItemStack stack, String augmentId, int level) {
        if (level <= 0) return;  // or remove from NBT if you prefer
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag augTag = tag.getCompound("Augments");
        augTag.putInt(augmentId, level);
        tag.put("Augments", augTag);
    }

    // endregion


    // region: LIFECYCLE

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);
        if (!stack.hasTag()) {
            setItemLevel(stack, 1);
        }
    }

    /**
     * Ensures HideFlags=2 (hide vanilla attribute lines) and if no NBT, sets level=1.
     */
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

    // endregion


    // region: ATTRIBUTE MODIFIERS

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        // Filter out vanilla ATTACK_DAMAGE / ATTACK_SPEED from super's modifiers
        super.getAttributeModifiers(slot, stack).entries().stream()
                .filter(entry -> !entry.getKey().equals(Attributes.ATTACK_DAMAGE) && !entry.getKey().equals(Attributes.ATTACK_SPEED))
                .forEach(entry -> builder.put(entry.getKey(), entry.getValue()));

        int level = getItemLevel(stack);

        double totalDamage = getDamageForLevel(level);
        double finalSpeed  = getSpeedForLevel(level);

        double speedModifier = finalSpeed - 4.0;

        // Add our custom damage/speed based on sword level
        builder.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(DAMAGE_UUID, "Level-based damage", totalDamage, AttributeModifier.Operation.ADDITION));

        builder.put(Attributes.ATTACK_SPEED,
                new AttributeModifier(SPEED_UUID, "Level-based attack speed", speedModifier, AttributeModifier.Operation.ADDITION));

        return builder.build();
    }

    /**
     * For each level, define how much damage is added. You might add logic or switch statements.
     */
    protected abstract double getDamageForLevel(int level);

    /**
     * For each level, define final attacks per second. Usually 1.6 for normal swords at level 1, etc.
     */
    protected abstract double getSpeedForLevel(int level);

    // endregion


    // region: TOOLTIP

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        // Display the current sword level
        int itemLevel = getItemLevel(stack);
        tooltip.add(Component.literal("Sword Level: " + itemLevel).withStyle(ChatFormatting.GRAY));

        // Show the augments. For example:
        Map<String, Integer> augMap = getAugments(stack);
        if (!augMap.isEmpty()) {
            tooltip.add(Component.literal("Augments:").withStyle(ChatFormatting.DARK_AQUA));
            for (Map.Entry<String, Integer> entry : augMap.entrySet()) {
                String augmentId = entry.getKey();
                int augLevel = entry.getValue();
                // Display: e.g. " - scourge_undead (Level 2)"
                // Or if you have a manager that can give user-friendly names, do so here.
                tooltip.add(Component.literal(" - " + augmentId + " (Level " + augLevel + ")")
                        .withStyle(ChatFormatting.DARK_GREEN));
            }
        }
    }

    // endregion
}
