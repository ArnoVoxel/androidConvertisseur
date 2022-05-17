package com.example.convmonnaie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Connexion extends SQLiteOpenHelper {

    private static final String DB_NAME = "devises";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "monnaies";

    //private static final String ID_COL = "id";

    private static final String MONNAIE_COL = "monnaie";

    private static final String TAUX_COL = "taux";

    public Connexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version ){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " +TABLE_NAME+" ("
                +MONNAIE_COL+" TEXT PRIMARY KEY, "
                +TAUX_COL+" double NOT NULL );";

        Log.d("Connexion", query);

        String insertValues = "INSERT INTO "+TABLE_NAME+" ("+MONNAIE_COL+", "+TAUX_COL+") VALUES \n" +
                "('Dirham', 8.5656),\n" +
                "('Dollars CA', 1.1),\n" +
                "('Dollars US', 1),\n" +
                "('Euro', 0.7697),\n" +
                "('Franc', 5.049),\n" +
                "('Franc CFA', 503.17),\n" +
                "('Livre', 0.6405),\n" +
                "('Yen', 76.6908);";

        db.execSQL(query);
        db.execSQL(insertValues);
        Log.d("Connexion", "après execution create TABLE et insert");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TABLE_NAME);
        onCreate(db);
    }

    public SQLiteDatabase getReadableDatabase(){
        Log.d("Connexion", "getReadable");
        return super.getReadableDatabase();
    }

}
