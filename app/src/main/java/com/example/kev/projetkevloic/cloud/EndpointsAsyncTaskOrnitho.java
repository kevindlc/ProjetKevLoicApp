package com.example.kev.projetkevloic.cloud;

import android.os.AsyncTask;

import com.example.kev.myapplication.backend.ornithologueApi.OrnithologueApi;
import com.example.kev.myapplication.backend.ornithologueApi.model.Ornithologue;
import com.example.kev.projetkevloic.Database.DatabaseHelper;
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
    private Login login = null;

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
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            ornithologueApi = builder.build();
        }

        return new ArrayList<Ornithologue>();



    }


}