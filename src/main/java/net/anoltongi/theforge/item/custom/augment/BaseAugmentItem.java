package net.anoltongi.theforge.item.custom.augment;

import net.minecraft.world.item.Item;

public class BaseAugmentItem extends Item {
    private final String augmentId;

    // This is the constructor you need:
    public BaseAugmentItem(String augmentId, Item.Properties properties) {
        super(properties);
        this.augmentId = augmentId;
    }

    public String getAugmentId() {
        return augmentId;
    }
}