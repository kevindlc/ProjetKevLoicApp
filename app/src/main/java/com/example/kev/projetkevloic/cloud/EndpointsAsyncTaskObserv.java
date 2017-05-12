package com.example.kev.projetkevloic.cloud;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.kev.myapplication.backend.observationApi.model.Observation;
import com.example.kev.myapplication.backend.observationApi.ObservationApi;
import com.example.kev.projetkevloic.Database.DatabaseHelper;
import com.example.kev.projetkevloic.Database.ObserverDB;
import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.View.home.MainActivity;
import com.example.kev.projetkevloic.activity.Login;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev on 11.05.2017.
 */


//TEST COMMIT

public class EndpointsAsyncTaskObserv extends AsyncTask<Void, Void, List<Observation>> {
    private static ObservationApi observationApi = null;
    private static final String TAG = EndpointsAsyncTaskObserv.class.getName();
    private Observation observation;
    private DatabaseHelper db;
    private Login login = null;
    private Context context;


    public EndpointsAsyncTaskObserv(Context context) {
        this.context = context;
    }


    @Override
    protected List<Observation> doInBackground(Void... params) {
        if (observationApi == null) {  // Only do this once
            ObservationApi.Builder builder = new ObservationApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
            // options for running against local devappserver
            // - 10.0.2.2 is localhost's IP address in Android emulator
            // - turn off compression when running against local devappserver
            // if you deploy on the cloud backend, use your app name
            // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://findthebird-167107.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            observationApi = builder.build();
        }

        try {
            if (observation != null) {
                observationApi.insert(observation).execute();
                Log.i(TAG, "insert person");
            }
            return observationApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Observation>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    protected void onPostExecute(List<com.example.kev.projetkevloic.object.Observation> result) {

        if (result != null) {
            ObserverDB oDB = new ObserverDB(db);
            // peut etre probleme dans oDB
            //     oDB.cloudToSqlPerson(observation);
        }

        if (login != null) {
            //    login.personOK = true;
            //   login.check();

        }
    }


}