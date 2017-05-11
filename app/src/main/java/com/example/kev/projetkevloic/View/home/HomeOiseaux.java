package com.example.kev.projetkevloic.View.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.add.addOiseau;
import com.example.kev.projetkevloic.View.show.show_oiseau;
import com.example.kev.projetkevloic.adapter.OiseauAdapter;
import com.example.kev.projetkevloic.object.Oiseau;

import java.util.List;

public class HomeOiseaux extends AppCompatActivity {

    ListView mListView;
    OiseauDB oDB;
    int ID_USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_oiseaux);
        oDB = new OiseauDB(this);

        mListView = (ListView) findViewById(R.id.listView);

        afficherListeOiseaux();
    }

    private void afficherListeOiseaux() {
        List<Oiseau> oiseaux = oDB.getAllOiseaux();

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        ID_USER = intent.getIntExtra("ID_USER", 0);

        OiseauAdapter adapter = new OiseauAdapter(HomeOiseaux.this, oiseaux);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView arg0, View view, int position, long id) {
                Intent intent = new Intent(HomeOiseaux.this, show_oiseau.class);
                finish();

                Oiseau ois = (Oiseau) arg0.getItemAtPosition(position);
                intent.putExtra("ID", ois.getId());
                intent.putExtra("NOM", ois.getNom());
                intent.putExtra("COLOR", ois.getColor());
                intent.putExtra("POIDS", ois.getPoids());
                intent.putExtra("TEXT", ois.getText());
                intent.putExtra("TAILLE", ois.getTaille());
                intent.putExtra("ID_USER", ID_USER);


                startActivity(intent);
            }
        });
    }

    public void Retour(View view) {
        Intent intent = new Intent(HomeOiseaux.this, MainActivity.class);
        intent.putExtra("ID_USER", ID_USER);

        finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (ID_USER == 1) {

            getMenuInflater().inflate(R.menu.adddelete_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.AddButton:
                finish();
                intent = new Intent(this, addOiseau.class);
                intent.putExtra("ID_USER", ID_USER);

                this.startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}