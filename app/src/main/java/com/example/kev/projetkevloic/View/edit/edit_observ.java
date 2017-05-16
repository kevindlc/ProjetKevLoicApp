package com.example.kev.projetkevloic.View.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kev.projetkevloic.Database.ObserverDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.home.HomeObservations;
import com.example.kev.projetkevloic.View.home.HomeOiseaux;
import com.example.kev.projetkevloic.View.home.HomeOrnithologue;
import com.example.kev.projetkevloic.object.Observation;

/**
 * Created by Kev on 26.04.2017.
 */

public class edit_observ extends AppCompatActivity {

    ObserverDB bDB = new ObserverDB(this);
    Button butOK;

    private int id , orni, oiseau;
    private String text, orniN, oiseauN;
    EditText txtText;

    private int ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observ);

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        orni = intent.getIntExtra("orni",0);
        oiseau = intent.getIntExtra("oiseau",0);
        text = intent.getStringExtra("text");
        orniN = intent.getStringExtra("orniName");
        oiseauN = intent.getStringExtra("oiseauName");
        id = intent.getIntExtra("id",0);
        ID_USER = intent.getIntExtra("ID_USER",0);


        butOK = (Button) findViewById(R.id.butOK);
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtText =  (EditText)  findViewById(R.id.editText3);

                Observation o = new Observation();
                o.setId(id);
                o.setOiseau(oiseau);
                o.setOrni(orni);
                o.setText(txtText.getText().toString());

                bDB.updateObservation(o);
                bDB.sqlToCloudObservationEdit(id);


                Intent intent = new Intent(edit_observ.this , HomeObservations.class);
                finish();
                intent.putExtra("ID_USER" , ID_USER);

                startActivity(intent);

            }
        });


        //Set the values
        TextView txtLblOrni = (TextView) findViewById(R.id.textView1);
        txtLblOrni.setText("orni : ");

        TextView txtLblOiseau = (TextView) findViewById(R.id.textView2);
        txtLblOiseau.setText("oiseau : ");

        TextView txtLblText = (TextView) findViewById(R.id.textView3);
        txtLblText.setText("text : ");


        txtText = (EditText) findViewById(R.id.editText3);
        txtText.setText(text);
    }


    public void Retour (View view) {
        Intent intent = new Intent(edit_observ.this, HomeObservations.class);
        intent.putExtra("ID_USER" , ID_USER);

        finish();
        startActivity(intent);
    }
}
