/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */
package com.codiction.utils;

import com.codiction.Main;
import com.codiction.economy.Account;
import com.codiction.economy.Transaction;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Codiction
 */
public class FileManager {

    Main main = (Main) Bukkit.getServer().getPluginManager().getPlugin("CConomy");
    private File dataFolder, accountFolder, transactionFolder;

    public FileManager() {
        setup();

    }

    private void setup() {
        dataFolder = main.getDataFolder();
        accountFolder = new File(dataFolder.getPath() + "\\accounts");
        transactionFolder = new File(dataFolder.getPath() + "\\transactions");

        main.debug("Data folder: " + dataFolder.getPath());
        main.debug("Account folder: " + accountFolder.getPath());
        main.debug("Transaction folder: " + transactionFolder.getPath());

        if (!dataFolder.exists()) {
            main.info("Preparing for first use...");
            dataFolder.mkdir();
        }

        if (!accountFolder.exists()) {
            accountFolder.mkdir();
        }

        if (!transactionFolder.exists()) {
            transactionFolder.mkdir();
        }

        main.debug("Completed initial filecheck");
    }

    public File[] getAccountFiles() {
        return accountFolder.listFiles();
    }

    public File[] getTransactionFiles() {
        return transactionFolder.listFiles();
    }

    public File getTransactionFile(String name) {
        for (File f : getTransactionFiles()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public void save(Account a) {
        FileConfiguration fc = null;
        fc.set("owner", a.owner);
        fc.set("balance", a.getBalance());
        fc.set("transactionRef", a.owner);

        FileConfiguration tr = null;
        tr.set("owner", a.owner);
        tr.set("transactions", convertToStringList(a.transactionList));

        try {
            fc.save(new File(accountFolder.getPath() + "\\" + a.owner + ".yml"));
            tr.save(new File(transactionFolder.getPath() + "\\" + a.owner + ".yml"));
            main.debug("Saved account: " + a.owner);
        } catch (Exception e) {
            main.error("Could not save account " + a.owner);
        }

    }

    private List<String> convertToStringList(List<Transaction> list) {
        List<String> newList = new ArrayList();
        for (Transaction t : list) {
            newList.add(t.get());
        }
        return newList;
    }

}
