/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */

package com.codiction.economy;

import com.codiction.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Codiction
 */
public class Account {
    
    Main main = (Main) Bukkit.getServer().getPluginManager().getPlugin("CConomy");

    public String owner;
    private Player player;
    
    private double balance;
    public List<Transaction> transactionList = new ArrayList();
    
    
    public Account(String owner) {
        this.owner = owner;

    }
    
    public void listTransactions() {
        for(Transaction t : transactionList) {
            main.debug(t.get());
        }
    }
    
    public void subtract(double amount) {
        if(balance >= amount) {
            balance -= amount;
            save();
        }
    }
    
    public void addTransaction(Transaction t) {
        transactionList.add(t);
        save();
    }
    
    public void add(double amount) {
        balance += amount;
        save();
    }
    
    public void set(double value) {
        balance = value;
        save();
    }
    
    public double getBalance() {
        return balance;
    }
    
    private void save() {
        main.fileManager.save(this);
    }
}
