package me.plugin.customenchants.listeners;

import me.plugin.customenchants.CustomEnchants;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EnchantEffectListener implements Listener {

    private final CustomEnchants plugin;

    public EnchantEffectListener(CustomEnchants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack weapon = player.getInventory().getItemInMainHand();
        if (!weapon.hasItemMeta()) return;

        ItemMeta meta = weapon.getItemMeta();

        // 1. Vampirlik Büyüsü Mantığı
        NamespacedKey vampKey = new NamespacedKey(plugin, "custom_vampirism");
        if (meta.getPersistentDataContainer().has(vampKey, PersistentDataType.INTEGER)) {
            int level = meta.getPersistentDataContainer().get(vampKey, PersistentDataType.INTEGER);
            double healAmount = level * 1.5; // Seviye başına 1.5 kalp can yenileme
            
            double newHealth = Math.min(player.getHealth() + healAmount, player.getMaxHealth());
            player.setHealth(newHealth);
            player.sendMessage("§a[Vampirlik] §f" + healAmount + " §cheal emildi!");
        }

        // 2. Yıldırım Büyüsü Mantığı
        NamespacedKey lightningKey = new NamespacedKey(plugin, "custom_yildirim");
        if (meta.getPersistentDataContainer().has(lightningKey, PersistentDataType.INTEGER)) {
            int level = meta.getPersistentDataContainer().get(lightningKey, PersistentDataType.INTEGER);
            if (Math.random() < (level * 0.15)) { // Seviye başına %15 şans
                event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
                player.sendMessage("§e[Çekiç Darbesi] Yıldırım indirildi!");
            }
        }
    }
}