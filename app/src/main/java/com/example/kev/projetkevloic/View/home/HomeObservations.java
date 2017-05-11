package com.example.kev.projetkevloic.View.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kev.projetkevloic.Database.ObserverDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.add.addObserv;
import com.example.kev.projetkevloic.View.show.show_observ;
import com.example.kev.projetkevloic.adapter.ObservAdapter;
import com.example.kev.projetkevloic.object.Observation;

import java.util.List;

public class HomeObservations extends AppCompatActivity {

    ListView mListView;
    int ID_USER;

    ObserverDB bDB = new ObserverDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_observations);

        mListView = (ListView) findViewById(R.id.listView);

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        ID_USER = intent.getIntExtra("ID_USER", 0);

        afficherListeObserv();
    }


    private void afficherListeObserv() {
        List<Observation> observs = bDB.getAllObservations();

        ObservAdapter adapter = new ObservAdapter(HomeObservations.this, observs);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                Intent intent = new Intent(HomeObservations.this, show_observ.class);
                finish();

                Observation obs = (Observation) arg0.getItemAtPosition(position);
                intent.putExtra("orni", obs.getOrni());
                intent.putExtra("oiseau", obs.getOiseau());
                intent.putExtra("text", obs.getText());
                intent.putExtra("orniName", obs.getOrniN());
                intent.putExtra("oiseauName", obs.getOiseauN());
                intent.putExtra("id", obs.getId());
                intent.putExtra("ID_USER", ID_USER);

                startActivity(intent);
            }
        });
    }

    public void Retour(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adddelete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.AddButton:
                finish();
                intent = new Intent(this, addObserv.class);
                intent.putExtra("ID_USER", ID_USER);
                this.startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
