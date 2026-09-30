package me.plugin.customenchants.commands;

import me.plugin.customenchants.CustomEnchants;
import me.plugin.customenchants.listeners.EnchantTableListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor {

    private final CustomEnchants plugin;

    public EnchantCommand(CustomEnchants plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            new EnchantTableListener(plugin).openMainGUI(player);
            return true;
        }
        sender.sendMessage("Bu komutu sadece oyuncular kullanabilir!");
        return true;
    }
}