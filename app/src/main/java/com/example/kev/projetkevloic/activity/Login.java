package com.example.kev.projetkevloic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kev.projetkevloic.Database.DatabaseHelper;
import com.example.kev.projetkevloic.Database.ObserverDB;
import com.example.kev.projetkevloic.Database.OiseauDB;
import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.add.addOrni;
import com.example.kev.projetkevloic.View.home.MainActivity;
import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskObserv;
import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskOiseau;
import com.example.kev.projetkevloic.cloud.EndpointsAsyncTaskOrnitho;



public class Login extends AppCompatActivity {

    public static boolean[] loadingDone;
    Button buttonRegister;
    Button buttonLogin;
    public static OrnithoDB rDB;
    public static ObserverDB bDB;
    public static OiseauDB oDB;
    public static DatabaseHelper db;




    // SALUT C EST UN TEST
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button) findViewById(R.id.butLog);
        buttonRegister = (Button) findViewById(R.id.butAdd);
        rDB = new OrnithoDB(this);
        bDB = new ObserverDB(this);
        oDB = new OiseauDB(this);
        db = new DatabaseHelper(this);
        cloudToSQL();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, MainActivity.class);

                EditText tusername, tpassword, tage, tcanton;

                tusername = (EditText) findViewById(R.id.editText);
                tpassword = (EditText) findViewById(R.id.editText2);

                int id = rDB.checkOrnitho(tusername.getText().toString(),
                        tpassword.getText().toString());

                if (id != -1) {
                    intent.putExtra("ID_USER", id);
                    startActivity(intent);
                }

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, addOrni.class);
                startActivity(intent);

            }
        });


    }

    public void goLogin(View view) {
        finish();
        Intent intent = new Intent(Login.this, MainActivity.class);
    }


    public void Register(View view) {
        finish();
        Intent intent = new Intent(Login.this, addOrni.class);

    }


    public static void sqliteToCloud() {

        Log.d("sqlitcoulod","fasdf");
        oDB.sqlToCloudOiseau();
        rDB.sqlToCloudOrnithologue();
        bDB.sqlToCloudObservation();

    }


    public static void cloudToSQL( ) {
        db.resetDatabase();
        Log.d("ON VERA", "INTOC CLOUDTOSQL");

        new EndpointsAsyncTaskObserv().execute();
        new EndpointsAsyncTaskOiseau().execute();
        new EndpointsAsyncTaskOrnitho().execute();
    }






}