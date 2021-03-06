/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */
package com.codiction.economy;

import com.codiction.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Codiction
 */
public class Economy {

    Main main = (Main) Bukkit.getServer().getPluginManager().getPlugin("CConomy");
    private List<Account> accounts = new ArrayList();

    public Economy() {
        loadAccounts();
    }

    private void loadAccounts() {
        for (File file : main.fileManager.getAccountFiles()) {
            if (file != null) {
                loadAccount(file);
            }
        }
    }

    public void pay(Player from, Player to, double amount) {
        Account payer, receiver;
        payer = getAccount(from);
        receiver = getAccount(to);

        if (payer != null && receiver != null) {
            payer.subtract(amount);
            createPaymentTransaction(payer, receiver, amount);
            receiver.add(amount);
        }
    }

    public void setBalance(Player acc, double amount) {
        Account a = getAccount(acc);
        a.set(amount);
        createOtherTransaction("Balance was set to " + amount, a);
    }

    public void setBalance(Account acc, double amount) {

        acc.set(amount);
        createOtherTransaction("Balance was set to " + amount, acc);
    }

    public void createOtherTransaction(String t, Account a) {
        Transaction tr = new Transaction(t);
        a.addTransaction(tr);
    }

    public void doRefund(Account asker, Account concerned, double amount) {
        asker.add(amount);
        concerned.subtract(amount);
        createRefundTransaction(asker, concerned, amount);
    }

    private void createPaymentTransaction(Account payer, Account receiver, double amount) {
        Transaction t = new Transaction("Paid " + amount + " to " + receiver.owner);
        Transaction t2 = new Transaction("Received " + amount + " from " + payer.owner);
        payer.addTransaction(t);
        receiver.addTransaction(t2);

    }

    private void createRefundTransaction(Account asker, Account concerned, double amount) {
        Transaction t = new Transaction("Received " + amount + " from " + concerned.owner + " (refund)");
        Transaction t2 = new Transaction("Paid " + amount + " to " + asker.owner + " (refund)");
        asker.addTransaction(t);
        concerned.addTransaction(t2);
    }

    private void createCreationTransaction(Account a) {
        Transaction t = new Transaction("Account " + a.owner + " created.");
        a.addTransaction(t);
    }

    public Account getAccount(Player p) {
        for (Account a : accounts) {
            if (a.owner.equals(p.getName())) {
                return a;
            }
        }
        return null;
    }

    public Account getAccount(String name) {
        for (Account a : accounts) {
            if (a.owner.equals(name)) {
                return a;
            }
        }
        return null;
    }

    public void createAccount(String name) {
        if (!accountExists(name)) {
            main.info("New account created for " + name);
            Account a = new Account(name);
            accounts.add(a);
            createCreationTransaction(a);
        }
    }

    public boolean accountExists(String name) {
        for (Account a : accounts) {
            if (a.owner.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void loadAccount(File file) {
        try {

            FileConfiguration conf = YamlConfiguration.loadConfiguration(file);

            main.debug("Loading account: " + file.getName());
            Account a = new Account(conf.getString("owner"));
            a.set(conf.getDouble("balance"));

            File transactions = main.fileManager.getTransactionFile(a.owner);
            main.info(transactions.getPath());
            FileConfiguration transconf = YamlConfiguration.loadConfiguration(transactions);

            a.transactionList = createTransactionList(transconf.getStringList("transactions"));
            accounts.add(a);

        } catch (Exception ex) {
            main.error("Something went terribly wrong while accessing the filesystem.");
            main.error(ex.getMessage());
        }
    }

    private List<Transaction> createTransactionList(List<String> list) {
        List<Transaction> l = new ArrayList();
        for (String s : list) {
            l.add(new Transaction(s));
        }
        return l;
    }
}
