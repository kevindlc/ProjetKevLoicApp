package com.example.kev.projetkevloic.cloud;

import android.os.AsyncTask;

import com.example.kev.myapplication.backend.oiseauApi.OiseauApi;
import com.example.kev.myapplication.backend.oiseauApi.model.Oiseau;
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

public class EndpointsAsyncTaskOiseau extends AsyncTask<Void, Void, List<Oiseau>> {
    private static OiseauApi oiseauApi = null;
    private static final String TAG = EndpointsAsyncTaskOiseau.class.getName();
    private Oiseau oiseau;
    private DatabaseHelper db;
    private Login login = null;

    public EndpointsAsyncTaskOiseau(DatabaseHelper db, Login login) {
        this.db = db;
        this.login = login;
    }

    public EndpointsAsyncTaskOiseau(Oiseau oiseau, DatabaseHelper db) {
        this.oiseau = oiseau;
        this.db = db;
    }

    @Override
    protected List<Oiseau> doInBackground(Void... params) {
        if (oiseauApi == null) {  // Only do this once
            OiseauApi.Builder builder = new OiseauApi.Builder(AndroidHttp.newCompatibleTransport(),
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

            oiseauApi = builder.build();
        }

        return new ArrayList<Oiseau>();


    }

}





