package com.example.kev.projetkevloic.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by carob on 4/28/2017.
 */


public class DatabaseHelper extends SQLiteOpenHelper {



    public static final String DATABASE_NAME = "birdl.db";


    public static final String TABLE_OISEAU = " oiseaux_table ";
    public static final String OISEAU_ID = "ID";
    public static final String OISEAU_NAME = "NOM";
    public static final String OISEAU_TEXT = "TEXT";
    public static final String OISEAU_COLOR = "COLOR";
    public static final String OISEAU_POIDS = "POIDS";
    public static final String OISEAU_TAILLE = "TAILLE";


    public static final String TABLE_ORNITHO = "ornitho_table";
    public static final String ORNITHO_ID = "ID";
    public static final String ORNITHO_USERNAME = "USERNAME";
    public static final String ORNITHO_PASSWORD = "PASSWORD";
    public static final String ORNITHO_AGE = "AGE";
    public static final String ORNITHO_CANTON = "CANTON";


    public static final String TABLE_OBSERVATION = "observation_table";
    public static final String OBSERVATION_ID = "ID";
    public static final String OBSERVATION_TEXT = "TEXT";
    public static final String OBSERVATION_OISEAU = "ID_OISEAU";
    public static final String OBSERVATION_ORNITHO = "ID_ORNITHO";


    // query créant la table oiseau
    String query = "CREATE TABLE " + TABLE_OISEAU +
            "(" + OISEAU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            OISEAU_NAME + " TEXT, " +
            OISEAU_COLOR + " TEXT, " +
            OISEAU_POIDS+ " TEXT, " +
            OISEAU_TEXT + " TEXT, " +
            OISEAU_TAILLE + " TEXT " +
            ")";


    // query créant la table ornitho
    String query1 = "CREATE TABLE " + TABLE_ORNITHO +
            "(" + ORNITHO_ID + " INTEGER PRIMARY KEY," +
            ORNITHO_USERNAME + " TEXT," +
            ORNITHO_PASSWORD + " TEXT," +
            ORNITHO_AGE + " TEXT," +
            ORNITHO_CANTON + " TEXT" +
            ")";


    // query créant la table observations
    String query2 = "CREATE TABLE " + TABLE_OBSERVATION +
            "(" + OBSERVATION_ID + " INTEGER PRIMARY KEY, " +
            OBSERVATION_TEXT + " TEXT, " +
            OBSERVATION_OISEAU + " INTEGER, " +
            OBSERVATION_ORNITHO + " INTEGER, " +
            "FOREIGN KEY(" + OBSERVATION_OISEAU + ") REFERENCES " + TABLE_OISEAU + "( " + OISEAU_ID + ")," +
            "FOREIGN KEY(" + OBSERVATION_ORNITHO + ") REFERENCES " + TABLE_ORNITHO + "( " + ORNITHO_ID + ")" +
            ")";


    private static Context c;
    private static DatabaseHelper INSTANCE = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       //deleteTable(db);

        // exeecution des requete
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IFF EXIST " + TABLE_OISEAU);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_ORNITHO);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_OBSERVATION);

        //deleteTable(db);


        onCreate(db);
    }

    // delete all the tables
    public void deleteTable(SQLiteDatabase db){
        db.delete(TABLE_OISEAU,null,null);
        db.delete(TABLE_ORNITHO,null,null);
        db.delete(TABLE_OBSERVATION,null,null);
    }

    public void resetDatabase(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OISEAU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORNITHO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATION);

        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);


    }



}


