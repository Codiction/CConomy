/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */
package com.codiction;

import com.codiction.economy.Account;
import com.codiction.economy.Economy;
import com.codiction.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Codiction
 */
public class Main extends JavaPlugin {

    public boolean consoleColorSupport = false;
    public boolean chatColorSupport = false;

    public Economy economy;
    public FileManager fileManager;

    @Override
    public void onEnable() {
        //a bit of pre-init
        consoleColorSupport = getConfig().getBoolean("other.colors.console");
        chatColorSupport = getConfig().getBoolean("other.colors.chat");

        //actual init
        init();
    }

    private void init() {
        fileManager = new FileManager();
        economy = new Economy();
        
        economy.createAccount("Aristocracy101");
        economy.setBalance(economy.getAccount("Aristocracy101"),2.25);
        economy.getAccount("Aristocracy101").listTransactions();
    }

    public void info(String msg) {
        if (consoleColorSupport) {
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<CConomy> INFO: " + ChatColor.GREEN + msg + ChatColor.RESET);
        } else {
            getServer().getConsoleSender().sendMessage("<CConomy> INFO: " + msg);
        }

    }

    public void error(String msg) {
        if (consoleColorSupport) {
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<CConomy> ERROR: " + ChatColor.RED + msg + ChatColor.RESET);
        } else {
            getServer().getConsoleSender().sendMessage("<CConomy> ERROR: " + msg);
        }

    }

    public void warn(String msg) {
        if (consoleColorSupport) {
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<CConomy> WARNING: " + ChatColor.GOLD + msg + ChatColor.RESET);
        } else {
            getServer().getConsoleSender().sendMessage("<CConomy> WARNING: " + msg);
        }

    }

    public void debug(String msg) {
        if (consoleColorSupport) {
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<CConomy> DEBUG: " + ChatColor.DARK_AQUA + msg + ChatColor.RESET);
        } else {
            getServer().getConsoleSender().sendMessage("<CConomy> DEBUG: " + msg);
        }

    }
}
