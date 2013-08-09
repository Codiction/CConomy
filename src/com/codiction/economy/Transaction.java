/*
 * === CODICTION LICENSE ===
 * License: http://codiction.com/redirect.php?do=default_license
 * If you do not agree to the terms refrain yourself from using this source!
 */

package com.codiction.economy;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Codiction
 */
public class Transaction {

    public String text;
    public String time;
    
    public Transaction(String text) {
        time = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss").format(Calendar.getInstance().getTime());
        this.text = text;
    }
    
    public String get() {
        return "<" + time + ">  " + text;
    }
}
