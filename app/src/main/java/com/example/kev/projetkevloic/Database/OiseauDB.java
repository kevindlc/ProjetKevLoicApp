package com.example.kev.projetkevloic.Database;


        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

        import com.example.kev.projetkevloic.object.Observation;
        import com.example.kev.projetkevloic.object.Oiseau;
        import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskOiseau;

/**
 * Created by carob on 4/28/2017.
 */

public class OiseauDB {


    public String[] colorss = new String[]{"inconnu", "rouge", "bleu", "vert", "jaune", "brun", "gris", "noir", "blanc"};

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {
            DatabaseHelper.OISEAU_ID,
            DatabaseHelper.OISEAU_NAME,
            DatabaseHelper.OISEAU_COLOR,
            DatabaseHelper.OISEAU_POIDS,
            DatabaseHelper.OISEAU_TEXT,
            DatabaseHelper.OISEAU_TAILLE};

    public OiseauDB(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    // create a birds
    public void createOiseau(String nom, String color, String poids, String taille, String text){

        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.OISEAU_NAME, nom);
        values.put(DatabaseHelper.OISEAU_COLOR, color);
        values.put(DatabaseHelper.OISEAU_POIDS, poids);
        values.put(DatabaseHelper.OISEAU_TEXT , text);
        values.put(DatabaseHelper.OISEAU_TAILLE, taille);


        database.insert(DatabaseHelper.TABLE_OISEAU, null, values);

        close();
    }

    public void createOiseau(long id, String nom, String color, String poids, String taille, String text){

        open();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.OISEAU_ID, id);
        values.put(DatabaseHelper.OISEAU_NAME, nom);
        values.put(DatabaseHelper.OISEAU_COLOR, color);
        values.put(DatabaseHelper.OISEAU_POIDS, poids);
        values.put(DatabaseHelper.OISEAU_TEXT , text);
        values.put(DatabaseHelper.OISEAU_TAILLE, taille);


        database.insert(DatabaseHelper.TABLE_OISEAU, null, values);

        close();
    }

    // delete the bird in the sqlite db and the cloud
    public void deleteOiseau(Oiseau o){
        long id = o.getId();
        open();
        database.delete(DatabaseHelper.TABLE_OISEAU, DatabaseHelper.OISEAU_ID + " = " + id , null);
        new EndpointsAsyncTaskOiseau(2,(int)id, dbHelper).execute();
        close();
    }

    // delete a bird from one ID
    public void deleteOiseau(int o){
        open();
        Log.d("---", o+"");
        database.delete(DatabaseHelper.TABLE_OISEAU, DatabaseHelper.OISEAU_ID + " = " + o , null);
        new EndpointsAsyncTaskOiseau(2,o, dbHelper).execute();

        close();
    }

    // create a List of all the birds
    public List<Oiseau> getAllOiseaux(){
        open();

        List<Oiseau> oiseaux = new ArrayList<Oiseau>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Oiseau oiseau = new Oiseau();
                oiseau.setId(Integer.parseInt(cursor.getString(0)));
                oiseau.setNom(cursor.getString(1));
                oiseau.setColor(cursor.getString(2));
                oiseau.setPoids(cursor.getString(3));
                oiseau.setTaille(cursor.getString(4));
                oiseau.setText(cursor.getString(5));

                oiseaux.add(oiseau);
            }while (cursor.moveToNext());
        }

        close();
        return oiseaux;
    }


    public int getLastIdFree(){
        open();

        int lastid =0;
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
               lastid = (Integer.parseInt(cursor.getString(0)));

            }while (cursor.moveToNext());
        }

        lastid = lastid +1;
        close();
        return lastid;
    }

    // count the number of birds - never use
    public int getOiseauCount(){
        String countQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;
        open();

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        close();

        return cursor.getCount();
    }

    // update a birds
    public void updateOiseau(Oiseau oiseau){
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.OISEAU_NAME, oiseau.getNom());
        values.put(DatabaseHelper.OISEAU_COLOR, oiseau.getColor());
        values.put(DatabaseHelper.OISEAU_POIDS, oiseau.getPoids());
        values.put(DatabaseHelper.OISEAU_TAILLE, oiseau.getTaille());
        values.put(DatabaseHelper.OISEAU_TEXT, oiseau.getText());

         database.update(DatabaseHelper.TABLE_OISEAU, values, DatabaseHelper.OISEAU_ID + "=?" ,
                new String[]{String.valueOf(oiseau.getId())});

        close();
    }

    // get the birds by their ID
    Oiseau getOiseau(int id){
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_OISEAU, allColumns , DatabaseHelper.OISEAU_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null)  {
            cursor.moveToFirst();
        }

        Oiseau oiseau = new Oiseau(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), (cursor.getString(3)), cursor.getString(4), cursor.getString(5));

        close();
        return oiseau;

    }

    // create a List of the name of birds
    public ArrayList<String> getAllOiseauxName(){
        open();

        ArrayList<String> nameOiseaux = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                nameOiseaux.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        close();
        return nameOiseaux;
    }

    // get the id if we give the name
    public int getIdByName(String name){
        open();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_OISEAU;
        Cursor cursor = database.rawQuery(selectQuery, null);

        int ret = -1;
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(1).equals(name)){
                    ret = Integer.parseInt(cursor.getString(0));
                }
            }while (cursor.moveToNext());
        }

        close();
        return ret;

    }

    // find the position of the color into the spinner
    public int findColor(String c) {
        Log.d(c,c);
        String[] colorss = new String[]{"inconnu", "rouge", "bleu", "vert", "jaune", "brun", "gris", "noir", "blanc"};

        int temp = 0;
        for (String i : colorss) {
            if (i.equals(c)) {
                return temp;
            }
            temp++;
        }
        return 0;

    }


    //  import the sql lite db in the cloud
    public void sqlToCloudOiseau(){
        List<Oiseau> people = getAllOiseaux();

        for (Oiseau o : people) {
            if(o.getId() > EndpointsAsyncTaskOiseau.lastid) {
                com.example.kev.myapplication.backend.oiseauApi.model.Oiseau Oiseau = new com.example.kev.myapplication.backend.oiseauApi.model.Oiseau();
                Oiseau.setId((long) o.getId());
                Oiseau.setNom(o.getNom());
                Oiseau.setColor(o.getColor());
                Oiseau.setPoids(o.getPoids());
                Oiseau.setTaille(o.getTaille());
                Oiseau.setText(o.getText());

                new EndpointsAsyncTaskOiseau(Oiseau, dbHelper).execute();
            }
        }
    }

    // edit an bird in the cloud
    public void sqlToCloudOiseauEdit(Oiseau o){

                com.example.kev.myapplication.backend.oiseauApi.model.Oiseau Oiseau = new com.example.kev.myapplication.backend.oiseauApi.model.Oiseau();
                Oiseau.setId((long) o.getId());
                Oiseau.setNom(o.getNom());
                Oiseau.setColor(o.getColor());
                Oiseau.setPoids(o.getPoids());
                Oiseau.setTaille(o.getTaille());
                Oiseau.setText(o.getText());

                new EndpointsAsyncTaskOiseau(1,Oiseau, dbHelper).execute();
            }



            // we  import the data from the cloud
    public void cloudToSqlOiseau(List<com.example.kev.myapplication.backend.oiseauApi.model.Oiseau> oiseaux){
        SQLiteDatabase sqldbHelper = dbHelper.getReadableDatabase();
        sqldbHelper.delete(dbHelper.TABLE_OISEAU, null, null);

        for (com.example.kev.myapplication.backend.oiseauApi.model.Oiseau o : oiseaux) {
            ContentValues values = new ContentValues();
            values.put(dbHelper.OISEAU_ID, (long) o.getId());
            values.put(dbHelper.OISEAU_COLOR,o.getColor());
            values.put(dbHelper.OISEAU_NAME, o.getNom());
            values.put(dbHelper.OISEAU_POIDS, o.getPoids());
            values.put(dbHelper.OISEAU_TEXT, o.getText());


            sqldbHelper.insert(dbHelper.TABLE_OISEAU ,null, values);
        }
        sqldbHelper.close();

    }




}