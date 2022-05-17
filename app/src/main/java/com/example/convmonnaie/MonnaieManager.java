package com.example.convmonnaie;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MonnaieManager {

    public static final String DB_PATH = "/data/data/com.example.convmonnaie/databases/";
    public static final String DB_NAME = "devises";

    static String path = DB_PATH + DB_NAME;
    static SQLiteDatabase bdd = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);


    public static Map<String, Double> listeMonnaies() {
        String sql = "SELECT * FROM monnaies";
        Cursor c = bdd.rawQuery(sql, null);
        Map tableBDD = new HashMap();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    tableBDD.put(c.getString(0), Double.valueOf(c.getString(1)));
                    Log.i("monnaie", c.getString(0)+" : "+ c.getString(1));
                } while (c.moveToNext());
            }
        }
        return tableBDD;
    }
}
