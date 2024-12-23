package net.anoltongi.theforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.anoltongi.theforge.item.custom.sword.BaseLevelableSwordItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.anoltongi.theforge.item.custom.augment.AugmentManager;

import static net.anoltongi.theforge.item.custom.augment.AugmentManager.AUGMENT_MAX_LEVELS;

public class ModCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {

        // /setItemLevel <level>
        dispatcher.register(
                Commands.literal("setItemLevel")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    ServerPlayer player = source.getPlayerOrException();
                                    int level = IntegerArgumentType.getInteger(context, "level");

                                    ItemStack held = player.getMainHandItem();
                                    if (held.getItem() instanceof BaseLevelableSwordItem) {
                                        BaseLevelableSwordItem.setItemLevel(held, level);
                                        player.sendSystemMessage(Component.literal("Set item level to " + level));
                                    } else {
                                        player.sendSystemMessage(Component.literal("You are not holding a levelable sword."));
                                    }
                                    return 1;
                                })
                        )
        );

        // /addAugment <augmentId> <level>
        dispatcher.register(
                Commands.literal("addAugment")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("augmentId", StringArgumentType.string())
                                .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            ServerPlayer player = source.getPlayerOrException();
                                            String augmentId = StringArgumentType.getString(context, "augmentId");
                                            int augLevel = IntegerArgumentType.getInteger(context, "level");

                                            // Fix: Access AUGMENT_MAX_LEVELS from your newly public manager
                                            Integer maxAllowed = AugmentManager.AUGMENT_MAX_LEVELS.get(augmentId);
                                            if (maxAllowed != null && augLevel > maxAllowed) {
                                                augLevel = maxAllowed;
                                            }

                                            ItemStack held = player.getMainHandItem();
                                            if (!(held.getItem() instanceof BaseLevelableSwordItem)) {
                                                player.sendSystemMessage(Component.literal("You are not holding a levelable sword."));
                                                return 0;
                                            }

                                            // Optionally check if sword is at max level
                                            int swordLvl = BaseLevelableSwordItem.getItemLevel(held);
                                            int maxLvl = 10; // or BaseLevelableSwordItem.getMaxLevel(held) if you implement that
                                            if (swordLvl < maxLvl) {
                                                player.sendSystemMessage(Component.literal(
                                                        "Sword must be max level before applying augments."));
                                                return 0;
                                            }

                                            BaseLevelableSwordItem.setAugmentLevel(held, augmentId, augLevel);
                                            player.sendSystemMessage(Component.literal(
                                                    "Applied augment '" + augmentId + "' level " + augLevel + " to your sword."));
                                            return 1;
                                        })
                                ))
        );

        // /removeAugments
        dispatcher.register(
                Commands.literal("removeAugments")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            ServerPlayer player = source.getPlayerOrException();
                            ItemStack held = player.getMainHandItem();
                            if (!(held.getItem() instanceof BaseLevelableSwordItem)) {
                                player.sendSystemMessage(Component.literal("You are not holding a levelable sword."));
                                return 0;
                            }
                            // remove the "Augments" tag from the item
                            if (held.hasTag() && held.getTag().contains("Augments")) {
                                held.getTag().remove("Augments");
                                player.sendSystemMessage(Component.literal("All augments removed from your sword."));
                            } else {
                                player.sendSystemMessage(Component.literal("No augments to remove."));
                            }
                            return 1;
                        })
        );
    }
}