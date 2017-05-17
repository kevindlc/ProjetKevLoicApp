package com.example.kev.projetkevloic.View.edit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.home.HomeOrnithologue;
import com.example.kev.projetkevloic.View.show.show_ornitho;
import com.example.kev.projetkevloic.object.Ornithologue;

import java.util.ArrayList;

public class edit_ornitho extends AppCompatActivity {

    OrnithoDB rDB = new OrnithoDB(this);
    Button butOK;
    String id = null;
    int ID_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ornitho);

        // initalize a button from the layout
        butOK = (Button) findViewById(R.id.button5);

        // add an action in the button ( edit a ornithologue)
        butOK.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                EditText tusername , tpassword, tage;

                // take the values in the editexts from the layout
                tusername =  (EditText)  findViewById(R.id.editText1);
                tpassword =  (EditText)  findViewById(R.id.editText4);
                tage =  (EditText)  findViewById(R.id.editText3);

                Spinner spinnerCanton= (Spinner) findViewById(R.id.editText2);
                String tcanton = spinnerCanton.getSelectedItem().toString();

                // create a ornitho and add values
                Ornithologue o = new Ornithologue();
                int idOk = Integer.parseInt(id);
                o.setId(idOk);
                o.setUsername(tusername.getText().toString());
                o.setPassword(tpassword.getText().toString());
                o.setAge(tage.getText().toString());
                o.setCanton(tcanton);

                // update the ornitho into the db
                rDB.updateOrnitho(o);
                rDB.cloudToSqlOrnithologueEdit(o);

                // move to the homeOrnitho view
                Intent intent = new Intent(edit_ornitho.this , HomeOrnithologue.class);
                intent.putExtra("ID_USER", ID_USER);
                finish();
                startActivity(intent);
            }
        });

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        String username = intent.getStringExtra("username");
        String canton = intent.getStringExtra("canton");
        String age = intent.getStringExtra("age");
        String password = intent.getStringExtra("password");
        id = intent.getStringExtra("id");
        ID_USER = intent.getIntExtra("ID_USER",0);




        //Set the labels
        TextView txtLblNom = (TextView) findViewById(R.id.textView1);
        txtLblNom.setText(R.string.username);

        TextView txtLblprenom = (TextView) findViewById(R.id.textView2);
        txtLblprenom.setText(R.string.canton);

        TextView txtLblAge = (TextView) findViewById(R.id.textView3);
        txtLblAge.setText(R.string.age);

        TextView txtLblPassword = (TextView) findViewById(R.id.textView4);
        txtLblPassword.setText(R.string.password);

        // set the values into the edittext
        EditText txtUsername = (EditText) findViewById(R.id.editText1);
        txtUsername.setText(username);

        ArrayList<String> cantons = new ArrayList<String>() ;
        for (String c : rDB.cantons){
            cantons.add(c);
        }
        Spinner spinnerCantons = (Spinner) findViewById(R.id.editText2);

        ArrayAdapter<String> adapterCantonName = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, cantons);
        spinnerCantons.setAdapter(adapterCantonName);

        EditText txtAge = (EditText) findViewById(R.id.editText3);
        txtAge.setText(age);

        EditText txtPassword = (EditText) findViewById(R.id.editText4);
        txtPassword.setText(password);
    }


    public void Retour (View view) {
        Intent intent = new Intent(edit_ornitho.this, HomeOrnithologue.class);
        intent.putExtra("ID_USER" , ID_USER);

        finish();
        startActivity(intent);
    }
}
