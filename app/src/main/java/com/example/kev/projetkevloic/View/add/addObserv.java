package com.example.kev.projetkevloic.View.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kev.projetkevloic.Database.ObserverDB;
import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.home.HomeObservations;
import com.example.kev.projetkevloic.View.home.HomeOiseaux;
import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskObserv;

import java.util.ArrayList;

/**
 * Created by Kev on 26.04.2017.
 */

public class addObserv extends AppCompatActivity {

    Button butOk;
    ObserverDB bDB = new ObserverDB(this);
    OiseauDB oDB = new OiseauDB(this);
    OrnithoDB rDB = new OrnithoDB(this);
    int ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observ);

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        ID_USER = intent.getIntExtra("ID_USER",0);


        //création du spinner pour oiseau
        ArrayList<String> oiseauName = oDB.getAllOiseauxName();
        Spinner spinnerOiseau = (Spinner) findViewById(R.id.editText);

        ArrayAdapter<String> adapteroiseauName = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, oiseauName);
        spinnerOiseau.setAdapter(adapteroiseauName);


        TextView tOrnitho = (TextView)   findViewById(R.id.editText6);
        String orn = bDB.getNameOrnitho(ID_USER);
        tOrnitho.setText(orn);


        // create a button from the layout
        butOk = (Button) findViewById(R.id.button5) ;

        // add the action to add an observation
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tOiseau , Ttext;

                Spinner mySpinner= (Spinner) findViewById(R.id.editText);
                String oiseau = mySpinner.getSelectedItem().toString();

                Ttext = (EditText)   findViewById(R.id.editText2);

                bDB.createObservation(bDB.getLastID(),oDB.getIdByName(oiseau),ID_USER,Ttext.getText().toString());

                Intent intent = new Intent(addObserv.this , HomeObservations.class);
                intent.putExtra("ID_USER" , ID_USER);
                finish();
                startActivity(intent);


            }
        });
    }

}
