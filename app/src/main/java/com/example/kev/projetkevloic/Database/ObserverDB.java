package com.example.kev.projetkevloic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kev.projetkevloic.object.Observation;
import com.example.kev.projetkevloic.object.Oiseau;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by carob on 5/1/2017.
 */

public class ObserverDB {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.OBSERVATION_ID,
            DatabaseHelper.OBSERVATION_OISEAU,
            DatabaseHelper.OBSERVATION_ORNITHO,
            DatabaseHelper.OBSERVATION_TEXT};

    private String[] allColumnsOiseau = {
            DatabaseHelper.OISEAU_ID,
            DatabaseHelper.OISEAU_NAME,
            DatabaseHelper.OISEAU_COLOR,
            DatabaseHelper.OISEAU_POIDS,
            DatabaseHelper.OISEAU_TEXT,
            DatabaseHelper.OISEAU_TAILLE};

    private String[] allColumnsOrnitho = {
            DatabaseHelper.ORNITHO_ID,
            DatabaseHelper.ORNITHO_USERNAME,
            DatabaseHelper.ORNITHO_PASSWORD,
            DatabaseHelper.ORNITHO_AGE,
            DatabaseHelper.ORNITHO_CANTON};

    public ObserverDB(Context context) { dbHelper = new DatabaseHelper(context);};

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    // créer une observation
    public void createObservation(int idOiseau, int idOrnitho, String text){

        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.OBSERVATION_OISEAU, idOiseau);
        values.put(DatabaseHelper.OBSERVATION_ORNITHO, idOrnitho);
        values.put(DatabaseHelper.OBSERVATION_TEXT, text);


        database.insert(DatabaseHelper.TABLE_OBSERVATION, null, values);

        close();
    }

    // delete une observation
    public void deleteObservation(int o){
        open();
        database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_ID + " = " + o , null);
        close();
    }

    // retourne une liste de toutes les observations
    public List<Observation> getAllObservations(){

        open();

        List<Observation> observations = new ArrayList<Observation>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OBSERVATION;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Observation o = new Observation();
                o.setId(Integer.parseInt(cursor.getString(0)));
                o.setText(cursor.getString(1));
                o.setOiseau(Integer.parseInt(cursor.getString(2)));
                o.setOrni(Integer.parseInt(cursor.getString(3)));

                int idOiseau = Integer.parseInt(cursor.getString(2));
                String idO = getNameOiseau(idOiseau);
                o.setOiseauN(idO);
                o.setOrniN(getNameOrnitho(Integer.parseInt(cursor.getString(3))));

                observations.add(o);

            }while (cursor.moveToNext());
        }

        close();
        return observations;
    }

    // compte le nombre d'observations - useless
    public int getObsCount(){
        String countQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OBSERVATION;
        open();

        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.close();
        close();

        return cursor.getCount();
    }

    // met à jour un observation
    public void updateObservation(Observation obs){
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.OBSERVATION_TEXT, obs.getText());
        values.put(DatabaseHelper.OBSERVATION_OISEAU, obs.getOiseau());
        values.put(DatabaseHelper.OBSERVATION_ORNITHO, obs.getOrni());



        database.update(DatabaseHelper.TABLE_OBSERVATION, values, DatabaseHelper.OBSERVATION_ID + "=?" ,
                new String[]{String.valueOf(obs.getId())});

        close();
    }

    // retourne une observation
    Observation getObservation(int id){
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_OBSERVATION, allColumns , DatabaseHelper.OBSERVATION_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)  {
            cursor.moveToFirst();
        }

        Observation observation = new Observation(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), (cursor.getString(3)));

        close();
        return observation;

    }

    // retourn le nom d'un oiseau par rapport à son ID
    public String getNameOiseau(int id){
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_OISEAU, allColumnsOiseau , DatabaseHelper.OISEAU_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)  {
            cursor.moveToFirst();
        }

        String name = cursor.getString(1);

        close();
        return name;

    }

    // retourne le nom d'un ornitho par rapport à son ID
    public String getNameOrnitho(int id){
        Log.d("----", id+"");
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ORNITHO, allColumnsOrnitho , DatabaseHelper.ORNITHO_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)  {
            cursor.moveToFirst();
        }

        String name = cursor.getString(1);

        close();
        return name;

    }


}

