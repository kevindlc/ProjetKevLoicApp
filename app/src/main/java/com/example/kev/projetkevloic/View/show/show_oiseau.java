package com.example.kev.projetkevloic.View.show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.edit.edit_oiseau;
import com.example.kev.projetkevloic.View.home.HomeOiseaux;

import java.util.ArrayList;

public class show_oiseau extends AppCompatActivity {

    OiseauDB oDB ;
    private String idf; // permet de resortir l'ID de l'oiseau
    private int ID_USER ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_oiseau);

        //Get the intent
        Intent intent = getIntent();

        //Extract the strings
        String nom = intent.getStringExtra("NOM");
        String taille = intent.getStringExtra("TAILLE");
        String poids =  intent.getStringExtra("POIDS");
        String color = intent.getStringExtra("COLOR");
        String text = intent.getStringExtra("TEXT");
        int id_ = intent.getIntExtra("ID",0);
        ID_USER  = intent.getIntExtra("ID_USER", 0);
        Log.d("ID_USERR : " , ID_USER+"");

        // check if the user is the admin, if yes we can edit
        if(ID_USER != 1){
            Button butEdit;
            butEdit = (Button) findViewById(R.id.button6);
            butEdit.setVisibility(View.INVISIBLE);
        }

        setIdf(String.valueOf(id_));

        //Set the title into the textview
        TextView txtLblNom = (TextView) findViewById(R.id.textView1);
        txtLblNom.setText(R.string.nom);

        TextView txtLblTaille = (TextView) findViewById(R.id.textView2);
        txtLblTaille.setText(R.string.taille);

        TextView txtLblPoids = (TextView) findViewById(R.id.textView3);
        txtLblPoids.setText(R.string.poids);

        TextView txtLblColor = (TextView) findViewById(R.id.textView4);
        txtLblColor.setText(R.string.couleur);

        TextView txtLblText = (TextView) findViewById(R.id.textView5);
        txtLblText.setText(R.string.descritpion);


        // set tue values from the intent into the textview
        TextView txtNom = (TextView) findViewById(R.id.textView6);
        txtNom.setText(nom);

        TextView txtTaille = (TextView) findViewById(R.id.textView7);
        txtTaille.setText(taille);

        TextView txtPoids = (TextView) findViewById(R.id.textView8);
        txtPoids.setText(poids);

        TextView txtColor = (TextView) findViewById(R.id.textView11);
        txtColor.setText(color);

        TextView txtText = (TextView) findViewById(R.id.textView10);
        txtText.setText(text);

    }

    public void Retour(View view) {
        Intent intent = new Intent(show_oiseau.this, HomeOiseaux.class);
        intent.putExtra("ID_USER" , ID_USER);

        finish();
        startActivity(intent);
    }

    public void Modify(View view) {

        // create intent to go in the editOiseau_class

        Intent intent = new Intent(show_oiseau.this, edit_oiseau.class);
        finish();

        // take the informations and add to the intent
        TextView txtNom = (TextView) findViewById(R.id.textView6);
        TextView txttaille = (TextView) findViewById(R.id.textView7);
        TextView txtPoids = (TextView) findViewById(R.id.textView8);
        TextView txtColor = (TextView) findViewById(R.id.textView11);
        TextView txtText = (TextView) findViewById(R.id.textView10);

        intent.putExtra("nom", txtNom.getText().toString());
        intent.putExtra("text", txtText.getText().toString());
        intent.putExtra("color", txtPoids.getText().toString());
        intent.putExtra("poids", txtPoids.getText().toString());
        intent.putExtra("taille", txttaille.getText().toString());
        intent.putExtra("id", getIdf() );
        intent.putExtra("ID_USER" , ID_USER);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(ID_USER == 1) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);

    }

    public String getIdf() {
        return idf;
    }

    public void setIdf(String idf) {
        this.idf = idf;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

            switch (item.getItemId()) {
                case R.id.butDelete:

                    // create a intent to go to home with and deletation of  a bird
                    intent = new Intent(this, HomeOiseaux.class);
                    finish();

                    int ID = Integer.parseInt(idf);
                    oDB = new OiseauDB(this);
                    oDB.deleteOiseau(ID);
                    intent.putExtra("ID_USER", ID_USER);
                    this.startActivity(intent);

                    break;

                default:
                    return super.onOptionsItemSelected(item);

        }

        return true;
    }
}





