package me.badbones69.crazyenchantments.commands;

import me.badbones69.crazyenchantments.Methods;
import me.badbones69.crazyenchantments.api.enums.Messages;
import me.badbones69.crazyenchantments.controllers.BlackSmith;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlackSmithCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.PLAYERS_ONLY.getMessage());
            return true;
        }
        if (hasPermission(sender)) {
            BlackSmith.openBlackSmith((Player) sender);
        }
        return true;
    }

    private boolean hasPermission(CommandSender sender) {
        return Methods.hasPermission(sender, "blacksmith", true);
    }

}