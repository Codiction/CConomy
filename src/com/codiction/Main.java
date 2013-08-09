/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */
package com.codiction;

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
        economy = new Economy();
        fileManager = new FileManager();
    }

    public void info(String msg) {
        if (consoleColorSupport) {
            System.out.println(ChatColor.LIGHT_PURPLE + "<CConomy>" + ChatColor.GREEN + msg + ChatColor.RESET);
        } else {
            System.out.println("<CConomy>" + msg);
        }

    }

    public void error(String msg) {
        if (consoleColorSupport) {
            System.out.println(ChatColor.LIGHT_PURPLE + "<CConomy>" + ChatColor.RED + msg + ChatColor.RESET);
        } else {
            System.out.println("<CConomy>" + msg);
        }

    }

    public void warn(String msg) {
        if (consoleColorSupport) {
            System.out.println(ChatColor.LIGHT_PURPLE + "<CConomy>" + ChatColor.GOLD + msg + ChatColor.RESET);
        } else {
            System.out.println("<CConomy>" + msg);
        }

    }

    public void debug(String msg) {
        if (consoleColorSupport) {
            System.out.println(ChatColor.LIGHT_PURPLE + "<CConomy>" + ChatColor.DARK_AQUA + msg + ChatColor.RESET);
        } else {
            System.out.println("<CConomy>" + msg);
        }

    }
}
