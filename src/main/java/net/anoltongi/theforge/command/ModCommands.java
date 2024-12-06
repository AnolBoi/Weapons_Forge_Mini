package net.anoltongi.theforge.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ModCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setItemLevel")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("level", IntegerArgumentType.integer(1))
                        .executes((context) -> {
                            CommandSourceStack source = context.getSource();
                            ServerPlayer player = source.getPlayerOrException();
                            int level = IntegerArgumentType.getInteger(context, "level");

                            ItemStack held = player.getMainHandItem();
                            if (held.getItem() instanceof net.anoltongi.theforge.item.custom.BaseLevelableSwordItem) {
                                net.anoltongi.theforge.item.custom.BaseLevelableSwordItem.setItemLevel(held, level);
                                player.sendSystemMessage(Component.literal("Set item level to " + level));
                            } else {
                                player.sendSystemMessage(Component.literal("You are not holding a levelable sword."));
                            }
                            return 1; // success
                        })
                )
        );
    }
}
