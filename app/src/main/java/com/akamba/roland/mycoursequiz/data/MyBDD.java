package com.akamba.roland.mycoursequiz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rolland on 16/11/2015.
 */
public class MyBDD extends SQLiteOpenHelper{


    public MyBDD(Context context,String bddName,SQLiteDatabase.CursorFactory factory,int version){
        super(context,bddName,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE joueur(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT NOT NULL, prenom TEXT);");
        db.execSQL("CREATE TABLE jeu(id INTEGER PRIMARY KEY AUTOINCREMENT, totalPoint INTEGER, idJoueur INTEGER NOT NULL, themeJeu TEXT NOT NULL, dateJeu TEXT NOT NULL);");
        db.execSQL("CREATE TABLE choix(id INTEGER PRIMARY KEY AUTOINCREMENT, isResponse flag INTEGER,libelle TEXT NOT NULL, idQuestion INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE question(id INTEGER PRIMARY KEY AUTOINCREMENT, libelle TEXT NOT NULL, themeJeu TEXT NOT NULL, idResponse INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
