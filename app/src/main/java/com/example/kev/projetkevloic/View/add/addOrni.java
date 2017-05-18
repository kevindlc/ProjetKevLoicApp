package com.example.kev.projetkevloic.View.add;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.activity.Login;
import com.example.kev.projetkevloic.object.Ornithologue;

import java.util.ArrayList;

public class addOrni extends AppCompatActivity {

    Button bOK;
    OrnithoDB rDB = new OrnithoDB(this);

    private int ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orni);

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        ID_USER = intent.getIntExtra("ID_USER",0);

        ArrayList<String> cantons = new ArrayList<String>() ;
        for (String c : rDB.cantons){
            cantons.add(c);
        }
        Spinner spinnerCantons = (Spinner) findViewById(R.id.editText4);

        ArrayAdapter<String> adapterCantonName = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, cantons);
        spinnerCantons.setAdapter(adapterCantonName);

        bOK = (Button) findViewById(R.id.button5) ;

        // add a button to add a ornithologue into the database
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tusername , tpassword, tage ;
                finish();
                tusername =  (EditText)  findViewById(R.id.editText6);
                tpassword =  (EditText)  findViewById(R.id.editText);
                tage =  (EditText)  findViewById(R.id.editText2);

                Spinner spinnerCanton= (Spinner) findViewById(R.id.editText4);
                String canton = spinnerCanton.getSelectedItem().toString();

                Ornithologue or1 = new Ornithologue();
                or1.setAge(tage.getText().toString());
                or1.setUsername(tusername.getText().toString());
                or1.setPassword(tpassword.getText().toString());
                or1.setCanton(canton);
                or1.setId(rDB.getLastIdFree());


                rDB.cloudToSqlOrnithologueEdit(or1);
                rDB.createOrnitho(tusername.getText().toString(),
                        tpassword.getText().toString() ,
                        tage.getText().toString(),
                        canton);

                Intent intent = new Intent(addOrni.this , Login.class);
                intent.putExtra("ID_USER" , ID_USER);

                startActivity(intent);
            }
        });
    }
}
