package com.example.kev.projetkevloic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskOrnitho;
import com.example.kev.projetkevloic.object.Ornithologue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carob on 4/28/2017.
 */

public class OrnithoDB {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public OrnithoDB(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public String[] cantons = new String[]{"Valais", "Vaud", "Genève", "Neuchatel", "Fribourg", "Jura"};

    private String[] allColumns = {
            DatabaseHelper.ORNITHO_ID,
            DatabaseHelper.ORNITHO_USERNAME,
            DatabaseHelper.ORNITHO_PASSWORD,
            DatabaseHelper.ORNITHO_AGE,
            DatabaseHelper.ORNITHO_CANTON};

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public void createOrnitho(String username, String password, String age, String canton) {
        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.ORNITHO_USERNAME, username);
        values.put(DatabaseHelper.ORNITHO_PASSWORD, password);
        values.put(DatabaseHelper.ORNITHO_AGE, age);
        values.put(DatabaseHelper.ORNITHO_CANTON, canton);

        database.insert(DatabaseHelper.TABLE_ORNITHO, null, values);

        close();
    }

    public void createOrnitho(long id, String username, String password, String age, String canton) {
        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.ORNITHO_ID, id);
        values.put(DatabaseHelper.ORNITHO_USERNAME, username);
        values.put(DatabaseHelper.ORNITHO_PASSWORD, password);
        values.put(DatabaseHelper.ORNITHO_AGE, age);
        values.put(DatabaseHelper.ORNITHO_CANTON, canton);

        database.insert(DatabaseHelper.TABLE_ORNITHO, null, values);

        close();
    }

    public void createOrnitho(String username, String password, String age, String canton,
                              com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue or) {
        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.ORNITHO_USERNAME, username);
        values.put(DatabaseHelper.ORNITHO_PASSWORD, password);
        values.put(DatabaseHelper.ORNITHO_AGE, age);
        values.put(DatabaseHelper.ORNITHO_CANTON, canton);

        database.insert(DatabaseHelper.TABLE_ORNITHO, null, values);
        new EndpointsAsyncTaskOrnitho(or,dbHelper ).execute();
        Log.d("OK","OKKKK");




        close();
    }

    public void deleteOrnitho(int o) {
        open();
        database.delete(DatabaseHelper.TABLE_ORNITHO, DatabaseHelper.ORNITHO_ID + " = " + o, null);
        database.delete(DatabaseHelper.TABLE_OBSERVATION, DatabaseHelper.OBSERVATION_ORNITHO + " = " + o , null);

        close();
    }

    // create a list of all ornithologues
    public List<Ornithologue> getAllOrnithos() {
        open();

        List<Ornithologue> ornithologues = new ArrayList<Ornithologue>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORNITHO;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Ornithologue ornitho = new Ornithologue();
                ornitho.setId(Integer.parseInt(cursor.getString(0)));
                ornitho.setUsername(cursor.getString(1));
                ornitho.setPassword(cursor.getString(2));
                ornitho.setAge(cursor.getString(3));
                ornitho.setCanton(cursor.getString(4));

                ornithologues.add(ornitho);
            } while (cursor.moveToNext());
        }

        close();
        return ornithologues;
    }

    public int getLastId() {
        open();


        int lastid = 0;

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORNITHO;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                lastid = (Integer.parseInt(cursor.getString(0)));

            } while (cursor.moveToNext());
        }

        close();
        return lastid + 1;
    }

    // count the number of ornitho - never use
    public int getOrnithoCount() {
        String countQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORNITHO;
        open();

        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.close();
        close();

        return cursor.getCount();
    }

    // modifiy the values of the ornithologue
    public void updateOrnitho(Ornithologue ornithologue) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ORNITHO_USERNAME, ornithologue.getUsername());
        values.put(DatabaseHelper.ORNITHO_PASSWORD, ornithologue.getPassword());
        values.put(DatabaseHelper.ORNITHO_AGE, ornithologue.getAge());
        values.put(DatabaseHelper.ORNITHO_CANTON, ornithologue.getCanton());


        database.update(DatabaseHelper.TABLE_ORNITHO, values, DatabaseHelper.ORNITHO_ID + "=?",
                new String[]{String.valueOf(ornithologue.getId())});

        close();
    }

    // get the ornitologue by the ID
    public Ornithologue getOrnitho(long id) {
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ORNITHO, allColumns, DatabaseHelper.ORNITHO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Ornithologue ornitho = new Ornithologue(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), (cursor.getString(3)), cursor.getString(4));

        close();
        return ornitho;

    }

    // find the position for the canton into the spinner
    public int findCanton(String c) {

        int temp = 0;
        for (String i : cantons) {
            if (i.equals(c)) {
                return temp;
            }
            temp++;
        }
        return 0;

    }

    // verify the username and the password and give the ID
    public int checkOrnitho(String username , String password)
    {
        int id=-1;
        open();

        Cursor cursor=database.rawQuery("SELECT id FROM " + DatabaseHelper.TABLE_ORNITHO + "  WHERE username=? AND password=?",new String[]{username,password});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        close();
        return id;
    }

    public void sqlToCloudOrnithologue(){
        List<Ornithologue> ornithos = getAllOrnithos();
        for (Ornithologue p : ornithos) {
            if(p.getId() > EndpointsAsyncTaskOrnitho.lastid){

                Log.d("ID ADD "+ p.getId(), "ID LAST " + EndpointsAsyncTaskOrnitho.lastid );

                com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue Ornithologue = new com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue();
                Ornithologue.setId( (long) p.getId());
                Ornithologue.setUsername(p.getUsername());
                Ornithologue.setPassword(p.getPassword());
                Ornithologue.setAge(p.getAge());
                Ornithologue.setCanton(p.getCanton());
                new EndpointsAsyncTaskOrnitho(Ornithologue, dbHelper).execute();

            }


        }
        Log.e("debugCloud","all Ornithologue data saved");
    }



    public void cloudToSqlOrnithologueEdit(Ornithologue p){

        com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue orni = new com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue();
        orni.setId((long) p.getId());
        orni.setCanton( p.getCanton());
        orni.setAge( p.getAge());
        orni.setUsername(p.getUsername());
        orni.setPassword(p.getPassword());

        new EndpointsAsyncTaskOrnitho(1,orni, dbHelper).execute();
    }


}