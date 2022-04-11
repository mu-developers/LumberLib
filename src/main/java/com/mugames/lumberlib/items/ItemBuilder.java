package com.mugames.lumberlib.items;

import com.google.common.collect.Lists;
import com.mugames.lumberlib.LumberLib;
import com.mugames.lumberlib.colors.Color;
import com.mugames.lumberlib.version.Version;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.logging.Level;

public class ItemBuilder {

    public static ItemStack newItemStack(@NotNull final Material type) {
        return newItemStack(type, 1);
    }

    public static ItemStack newItemStack(@NotNull final Material type, final int amount) {
        return new ItemStack(type, amount);
    }

    public static ItemBuilder builder(@NotNull final Material type) {
        ItemStack itemStack = new ItemStack(type, 1);
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder builder(@NotNull final Material type, final int amount) {
        ItemStack itemStack = new ItemStack(type, amount);
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder builder(@NotNull final ItemStack itemStack) {
        return new ItemBuilder(itemStack.clone());
    }

    private final ItemStack itemStack;
    private ItemMeta itemMeta;

    private ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setType(@NotNull final Material type) {
        itemStack.setType(type);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(@NotNull final String name) {
        itemMeta.setDisplayName(Color.colored(name));
        return this;
    }

    public ItemBuilder setLore(@Nullable final List<String> lore) {
        itemMeta.setLore(Color.colored(lore));
        return this;
    }

    public ItemBuilder insertLore(String lore, int line) {
        List<String> loreList = getLore();
        List<String> newLoreList = Lists.newArrayList();
        int size = loreList.size();
        int lineNumber = getLineNumber(size, line);
        for (int i = 0; i < size; i++) {
            if (i == lineNumber) {
                newLoreList.add(Color.colored(lore));
            }
            newLoreList.add(loreList.get(i));
        }
        itemMeta.setLore(newLoreList);
        return this;
    }

    public ItemBuilder extractLore(int line) {
        List<String> loreList = getLore();
        List<String> newLoreList = Lists.newArrayList();
        int size = loreList.size();
        int lineNumber = getLineNumber(size, line);
        for (int i = 0; i < size; i++) {
            if (i != lineNumber)
                newLoreList.add(loreList.get(i));
        }
        itemMeta.setLore(newLoreList);
        return this;
    }

    public ItemBuilder appendLore(List<String> lore) {
        List<String> loreList = getLore();
        loreList.addAll(Color.colored(lore));
        itemMeta.setLore(loreList);
        return this;
    }

    public ItemBuilder appendLore(String lore) {
        List<String> loreList = getLore();
        loreList.add(Color.colored(lore));
        itemMeta.setLore(loreList);
        return this;
    }

    public ItemBuilder clearLore() {
        itemMeta.setLore(null);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        itemMeta.addEnchant(enchant, level, true);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchant) {
        itemMeta.removeEnchant(enchant);
        return this;
    }

    public ItemBuilder clearEnchant() {
        for (Enchantment enchant : itemMeta.getEnchants().keySet()) {
            itemMeta.removeEnchant(enchant);
        }
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... flag) {
        itemMeta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag... flag) {
        itemMeta.removeItemFlags(flag);
        return this;
    }

    public ItemBuilder clearItemFlag() {
        for (ItemFlag flag : itemMeta.getItemFlags()) {
            itemMeta.removeItemFlags(flag);
        }
        return this;
    }

    public ItemBuilder setUnbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder removeUnbreakable() {
        itemMeta.setUnbreakable(false);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        Version version = LumberLib.getVersion();
        if (Version.isUnder(version, Version.V_1_13)) {
            itemStack.setDurability((short) durability);
        }
        else {
            if (itemMeta instanceof Damageable)
                ((Damageable) itemMeta).setDamage(durability);
        }
        return this;
    }

    public ItemBuilder repair() {
        Version version = LumberLib.getVersion();
        if (Version.isUnder(version, Version.V_1_13)) {
            itemStack.setDurability((short) 0);
        }
        else {
            if (itemMeta instanceof Damageable)
                ((Damageable) itemMeta).setDamage(0);
        }
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        itemMeta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeAttributeModifier(Attribute attribute) {
        itemMeta.removeAttributeModifier(attribute);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(int red, int green, int blue) {
        if (itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(org.bukkit.Color.fromRGB(red, green, blue));
        }
        return this;
    }

    public ItemBuilder setLeatherArmorColor(org.bukkit.Color color) {
        if (itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(color);
        }
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        Version version = LumberLib.getVersion();
        if (Version.isUnder(version, Version.V_1_13)) {
            Bukkit.getLogger().log(Level.WARNING, "This method is supported from version 1.13 or later.");
        }
        else {
            itemMeta.setCustomModelData(customModelData);
        }
        return this;
    }

    public ItemBuilder setItemMeta(@NotNull final ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private List<String> getLore() {
        List<String> lore = itemMeta.getLore();
        return (lore == null) ? Lists.newArrayList() : lore;
    }

    private int getLineNumber(int size, int line) {
        if (line > size) return size - 1;
        if (line < 1) return 0;
        return line - 1;
    }

}
