package me.plugin.customenchants.listeners;

import me.plugin.customenchants.CustomEnchants;
import me.plugin.customenchants.models.CustomEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class EnchantTableListener implements Listener {

    private final CustomEnchants plugin;
    private final String GUI_TITLE = "§8Özel Büyü Masası";

    public EnchantTableListener(CustomEnchants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTableInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
                event.setCancelled(true);
                openMainGUI(event.getPlayer());
            }
        }
    }

    public void openMainGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, GUI_TITLE);

        // Kategori Butonları
        gui.setItem(10, createGuiItem(Material.REDSTONE, "§c⚔ Saldırı Büyüleri", "§7Saldırı gücünüzü artıran büyüler."));
        gui.setItem(11, createGuiItem(Material.DIAMOND_CHESTPLATE, "§b🛡 Savunma Büyüleri", "§7Karakterinizi koruyan büyüler."));
        gui.setItem(12, createGuiItem(Material.GOLDEN_PICKAXE, "§e🛠 Yardımcı Büyüler", "§7Madencilik ve toplama büyüleri."));
        gui.setItem(13, createGuiItem(Material.OAK_SAPLING, "§a🌿 Pasif Büyüler", "§7Kalıcı ve pasif efektler."));
        gui.setItem(14, createGuiItem(Material.NETHER_STAR, "§5🔮 Özel (Apex) Büyüler", "§7Efsanevi ve güçlü yetenekler."));

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(GUI_TITLE) && !event.getView().getTitle().startsWith("§8Kategori:")) return;
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getType() == Material.AIR) return;

        // Kategori Tıklamaları
        if (currentItem.hasItemMeta() && currentItem.getItemMeta().hasDisplayName()) {
            String name = currentItem.getItemMeta().getDisplayName();
            if (name.contains("Saldırı")) openCategoryGUI(player, CustomEnchant.Category.ATTACK);
            else if (name.contains("Savunma")) openCategoryGUI(player, CustomEnchant.Category.DEFENSE);
            else if (name.contains("Yardımcı")) openCategoryGUI(player, CustomEnchant.Category.UTILITY);
            else if (name.contains("Pasif")) openCategoryGUI(player, CustomEnchant.Category.PASSIVE);
            else if (name.contains("Özel")) openCategoryGUI(player, CustomEnchant.Category.APEX);

            // Büyü Basma İşlemi
            if (event.getView().getTitle().startsWith("§8Kategori:")) {
                handleEnchantUpgrade(player, currentItem);
            }
        }
    }

    private void openCategoryGUI(Player player, CustomEnchant.Category category) {
        Inventory catGui = Bukkit.createInventory(null, 54, "§8Kategori: " + category.getDisplayName());

        plugin.getEnchantManager().getEnchantments().values().stream()
                .filter(e -> e.getCategory() == category)
                .forEach(e -> {
                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta meta = book.getItemMeta();
                    meta.setDisplayName("§6" + e.getName());

                    List<String> lore = new ArrayList<>(e.getDescription());
                    lore.add(" ");
                    lore.add("§7Maksimum Seviye: §e" + e.getMaxLevel());
                    lore.add("§7Gerekli EXP Seviyesi: §a" + e.getBaseCostExp());
                    lore.add("§eElinizdeki eşyaya basmak için tıklayın!");

                    meta.setLore(lore);
                    
                    // Custom NBT verisi ekleme (PersistentDataContainer)
                    NamespacedKey key = new NamespacedKey(plugin, "enchant_id");
                    meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, e.getId());

                    book.setItemMeta(meta);
                    catGui.addItem(book);
                });

        player.openInventory(catGui);
    }

    // Seviye Atlatma ve Büyü Basma Mantığı
    private void handleEnchantUpgrade(Player player, ItemStack clickedBook) {
        ItemMeta meta = clickedBook.getItemMeta();
        if (meta == null) return;

        NamespacedKey key = new NamespacedKey(plugin, "enchant_id");
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        String enchantId = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        CustomEnchant enchant = plugin.getEnchantManager().getEnchant(enchantId);

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand.getType() == Material.AIR) {
            player.sendMessage("§cBüyü basmak için elinizde bir eşya tutmalısınız!");
            return;
        }

        // Oyuncu Seviye Kontrolü
        if (player.getLevel() < enchant.getBaseCostExp()) {
            player.sendMessage("§cYetersiz tecrübe seviyesi! Gerekli: " + enchant.getBaseCostExp());
            return;
        }

        // Eşya Üzerindeki Büyü Seviyesini Öğrenme / Yükseltme
        ItemMeta itemMeta = mainHand.getItemMeta();
        NamespacedKey enchantKey = new NamespacedKey(plugin, "custom_" + enchant.getId());
        int currentLevel = itemMeta.getPersistentDataContainer().getOrDefault(enchantKey, PersistentDataType.INTEGER, 0);

        if (currentLevel >= enchant.getMaxLevel()) {
            player.sendMessage("§cBu eşyadaki büyü zaten maksimum seviyede!");
            return;
        }

        int nextLevel = currentLevel + 1;
        
        // Seviye Ve Lore Güncelleme
        itemMeta.getPersistentDataContainer().set(enchantKey, PersistentDataType.INTEGER, nextLevel);
        
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.removeIf(line -> line.contains(enchant.getName()));
        lore.add("§d" + enchant.getName() + " " + toRoman(nextLevel));
        itemMeta.setLore(lore);

        mainHand.setItemMeta(itemMeta);

        // Seviye Düşürme
        player.setLevel(player.getLevel() - enchant.getBaseCostExp());
        player.sendMessage("§aBaşarıyla §e" + enchant.getName() + " " + toRoman(nextLevel) + " §abüyüsü basıldı!");
    }

    private ItemStack createGuiItem(Material mat, String name, String lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(List.of(lore));
        item.setItemMeta(meta);
        return item;
    }

    private String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> String.valueOf(number);
        };
    }
}