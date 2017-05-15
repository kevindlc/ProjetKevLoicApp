package com.example.kev.projetkevloic.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kev.myapplication.backend.ornithologueApi.OrnithologueApi;
import com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue;
import com.example.kev.projetkevloic.Database.DatabaseHelper;
import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.activity.Login;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev on 11.05.2017.
 */

public class EndpointsAsyncTaskOrnitho extends AsyncTask<Void, Void, List<Ornithologue>> {
    private static OrnithologueApi ornithologueApi = null;
    private static final String TAG = EndpointsAsyncTaskOrnitho.class.getName();
    private Ornithologue ornithologue;
    private DatabaseHelper db;
    public Login login = null;
    public static long lastid;


    public EndpointsAsyncTaskOrnitho() {
    }

    public EndpointsAsyncTaskOrnitho(DatabaseHelper db, Login login) {
        this.db = db;
        this.login = login;
    }

    public EndpointsAsyncTaskOrnitho(Ornithologue ornithologue, DatabaseHelper db) {
        this.ornithologue = ornithologue;
        this.db = db;
    }

    @Override
    protected List<Ornithologue> doInBackground(Void... params) {
        if(ornithologueApi == null) {  // Only do this once
            OrnithologueApi.Builder builder = new OrnithologueApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://findthebird-167412.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            ornithologueApi = builder.build();
        }




        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(ornithologue != null){
                ornithologueApi.insert(ornithologue).execute();
                Log.i(TAG, "insert actor");
            }
            // and for instance return the list of all employees
            return ornithologueApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Ornithologue>();
        }
    }

    @Override
    protected void onPostExecute(List<Ornithologue> result) {

        Log.d("ON VERA", "INTOC ecutendoipintstaskornitho11");

        if(result != null) {
            for (Ornithologue s:result) {

                Log.d("ON VERA", "INTOC ecutendoipintstaskornitho222");

                long id = s.getId();
                lastid = id;
                String username = s.getUsername();
                String password = s.getPassword();
                String age = s.getAge();
                String canton = s.getCanton();

                Login.rDB.createOrnitho(id, username, password, age , canton);
            }
        }


    }




}