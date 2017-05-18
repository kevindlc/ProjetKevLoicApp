package com.example.kev.projetkevloic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskObserv;
import com.example.kev.projetkevloic.object.Observation;
import com.example.kev.projetkevloic.object.Oiseau;
import com.example.kev.projetkevloic.object.Ornithologue;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by carob on 5/1/2017.
 */

public class ObserverDB {


    // SALUT LOIC

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    public int test = 0;


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

    public ObserverDB(DatabaseHelper db) {
    }

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

    public void createObservation(int id, int idOiseau, int idOrnitho, String text){

        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.OBSERVATION_ID, id);
        values.put(DatabaseHelper.OBSERVATION_OISEAU, idOiseau);
        values.put(DatabaseHelper.OBSERVATION_ORNITHO, idOrnitho);
        values.put(DatabaseHelper.OBSERVATION_TEXT, text);


        database.insert(DatabaseHelper.TABLE_OBSERVATION, null, values);
        com.example.kev.myapplication.backend.observationApi.model.Observation obs = new com.example.kev.myapplication.backend.observationApi.model.Observation();
        obs.setText(text);
        obs.setId((long)id);
        obs.setOiseau((long) idOiseau);
        obs.setOrni((long) idOrnitho);

        new EndpointsAsyncTaskObserv(3,obs,dbHelper).execute();

        close();
    }

    // delete une observation
    public void deleteObservation(int o){
        open();
        database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_ID + " = " + o , null);
        new EndpointsAsyncTaskObserv(2,o,dbHelper).execute();
        close();
    }


    public void deleteObservationOrni(int o){
        open();
        database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_ORNITHO + " = " + o , null);
        close();
    }

    public void deleteObservationOis(int o){
        open();
        database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_OISEAU + " = " + o , null);
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
                Log.d("-"+cursor.getString(0)+"_", "0 -");
                Log.d("-"+cursor.getString(1)+"_", "1 -");
                Log.d("-"+cursor.getString(2)+"_", "2 -");
                Log.d("-"+cursor.getString(3)+"_", "3 -");





                o.setId(Integer.parseInt(cursor.getString(0)));
                o.setText(cursor.getString(1));
                o.setOiseau(Integer.parseInt(cursor.getString(2)));
                o.setOrni(Integer.parseInt(cursor.getString(3)));

                int idOiseau = (o.getOiseau());


                Log.d(idOiseau+"", "OISEAUX N -");

                String idO = getNameOiseau(idOiseau);
                o.setOiseauN(idO);

                int idOrni = Integer.parseInt(cursor.getString(3));
                Log.d(idOrni+"", "ICI");
                String idOr= getNameOrnitho(idOrni);
                o.setOrniN(idOr);

                observations.add(o);

            }while (cursor.moveToNext());
        }

        close();
        return observations;
    }


    // retourne une liste de toutes les observations
    public int getLastID(){

        open();


        int i = 0;
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OBSERVATION;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Observation o = new Observation();
                i = (Integer.parseInt(cursor.getString(0)));
                          }while (cursor.moveToNext());
        }

        close();
        i ++;
        return i;
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
    public String getNameOiseau(long id){
        String name = "" ;


        open();

        List<Oiseau> oiss = new ArrayList<Oiseau>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                int idOiseau = Integer.parseInt(cursor.getString(0));
               if(id ==idOiseau){
                   name = cursor.getString(1);
               }

            }while (cursor.moveToNext());
        }

        close();


        return name;

    }

    // retourne le nom d'un ornitho par rapport à son ID
    public String getNameOrnitho(long id){
        String name = "" ;
        open();

        List<Ornithologue> oiss = new ArrayList<Ornithologue>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORNITHO;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                int idOrni = Integer.parseInt(cursor.getString(0));
                if(id ==idOrni){
                    name = cursor.getString(1);
                }

            }while (cursor.moveToNext());
        }
        close();
        return name;

    }

//  we export the data to the cloud
    public void sqlToCloudObservation(){
        List<Observation> observs = getAllObservations();
        for (Observation p : observs) {
            if(p.getId() > EndpointsAsyncTaskObserv.lastid) {

                com.example.kev.myapplication.backend.observationApi.model.Observation Observation = new com.example.kev.myapplication.backend.observationApi.model.Observation();
                Observation.setId((long) p.getId());
                Observation.setOrni((long) p.getOrni());
                Observation.setOiseau((long) p.getOiseau());
                Observation.setText(p.getText());
                new EndpointsAsyncTaskObserv(Observation, dbHelper).execute();
            }
        }
    }

    // edit or add we an specific id
    public void sqlToCloudObservationEdit(Observation p){

                com.example.kev.myapplication.backend.observationApi.model.Observation Observation = new com.example.kev.myapplication.backend.observationApi.model.Observation();
                Observation.setId((long) p.getId());
                Observation.setOrni((long) p.getOrni());
                Observation.setOiseau((long) p.getOiseau());
                Observation.setText(p.getText());

                new EndpointsAsyncTaskObserv(1,Observation, dbHelper).execute();

    }


    // we import the data from the cloud
    public void cloudToSqlObservation(List<com.example.kev.myapplication.backend.observationApi.model.Observation> observs){
        SQLiteDatabase sqlDB = dbHelper.getReadableDatabase();
        sqlDB.delete(dbHelper.TABLE_OBSERVATION, null, null);

        for (com.example.kev.myapplication.backend.observationApi.model.Observation p : observs) {
            ContentValues values = new ContentValues();
            values.put(dbHelper.OBSERVATION_ID,(long) p.getId());
            values.put(dbHelper.OBSERVATION_ORNITHO, (long) p.getOrni());
            values.put(dbHelper.OBSERVATION_OISEAU, (long) p.getOiseau());
            values.put(dbHelper.OBSERVATION_TEXT, p.getText());
            sqlDB.insert(dbHelper.TABLE_OBSERVATION, null, values);
        }
        sqlDB.close();
    }

    public void deleteObservationOiseau(int i) {
        List<Observation> observs = getAllObservations();
        for (Observation p : observs) {
            if(p.getOiseau() == i) {

                database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_ID + " = " + p.getId() , null);

            }
        }
    }

    // when we delete an bird, we need to delete all the observations where the bird is the deleted bird
    public void deleteObservationsOISEAU(int idDeleteBird){
        open();

        List<Observation> observations = new ArrayList<Observation>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OBSERVATION;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                // only if the idDeleteBird from the birds is the same in the delete bird
                if(Integer.parseInt(cursor.getString(2)) == idDeleteBird){
                    deleteObservation(Integer.parseInt(cursor.getString(0)));
                }

            }while (cursor.moveToNext());
        }

        close();
    }

    // delete the observation in the cloud
    public void deleteCloudObservation(Observation p){

        // transform the bird
        com.example.kev.myapplication.backend.observationApi.model.Observation Observation = new com.example.kev.myapplication.backend.observationApi.model.Observation();
        Observation.setId((long) p.getId());
        Observation.setOrni((long) p.getOrni());
        Observation.setOiseau((long) p.getOiseau());
        Observation.setText(p.getText());

        // delete the bird in the cloud
        new EndpointsAsyncTaskObserv(2,Observation, dbHelper).execute();

    }
}

