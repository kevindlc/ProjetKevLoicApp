package com.example.kev.projetkevloic.View.add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.home.HomeOiseaux;
import com.example.kev.projetkevloic.object.Oiseau;

import java.util.ArrayList;

public class addOiseau extends AppCompatActivity {

    Button bOK;
    OiseauDB oDB = new OiseauDB(this);

    private int ID_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oiseau);

        // create a array list, an adapater to create a spinner for the colors
        ArrayList<String> colors = new ArrayList<String>();
        Spinner spinnerColor = (Spinner) findViewById(R.id.editText7);

        for (String c : oDB.colorss){
            colors.add(c);
        }

        ArrayAdapter<String> adapterColors = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, colors);
        spinnerColor.setAdapter(adapterColors);


        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        ID_USER = intent.getIntExtra("ID_USER",0);

        // create a button from the layout
        bOK = (Button) findViewById(R.id.button5) ;

        // add the action to add a bird
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tnom , ttext, tpoids,ttaille;
                finish();
                Spinner mySpinner=(Spinner) findViewById(R.id.editText7);
                String color = mySpinner.getSelectedItem().toString();

                tnom =  (EditText)  findViewById(R.id.editText2);
                ttext = (EditText)  findViewById(R.id.editText4);
                tpoids = (EditText)   findViewById(R.id.editText5);
                ttaille = (EditText)   findViewById(R.id.editText3);

                oDB.createOiseau(tnom.getText().toString(), color,tpoids.getText().toString(), ttaille.getText().toString(), ttext.getText().toString() );


                Oiseau os = new Oiseau();

                os.setNom(tnom.getText().toString());
                os.setColor(color);
                os.setText(tpoids.getText().toString());
                os.setPoids(tpoids.getText().toString());
                os.setText(ttaille.getText().toString());
                os.setId(oDB.getLastIdFree());

                oDB.sqlToCloudOiseauEdit(os);

                Intent intent = new Intent(addOiseau.this , HomeOiseaux.class);
                intent.putExtra("ID_USER" , ID_USER);
                startActivity(intent);
            }
        });


    }
}







