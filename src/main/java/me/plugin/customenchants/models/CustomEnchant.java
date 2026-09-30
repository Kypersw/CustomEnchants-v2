package me.plugin.customenchants.models;

import org.bukkit.inventory.ItemStack;
import java.util.List;

public class CustomEnchant {

    public enum Category {
        ATTACK("§c⚔ Saldırı"),
        DEFENSE("§b🛡 Savunma"),
        UTILITY("§e🛠 Yardımcı"),
        PASSIVE("§a🌿 Pasif"),
        APEX("§5🔮 Özel (Apex)");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private final String id;
    private final String name;
    private final Category category;
    private final int maxLevel;
    private final int baseCostExp;
    private final List<String> description;

    public CustomEnchant(String id, String name, Category category, int maxLevel, int baseCostExp, List<String> description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.maxLevel = maxLevel;
        this.baseCostExp = baseCostExp;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getMaxLevel() { return maxLevel; }
    public int getBaseCostExp() { return baseCostExp; }
    public List<String> getDescription() { return description; }
}