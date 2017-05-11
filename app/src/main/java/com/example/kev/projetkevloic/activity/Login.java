package com.example.kev.projetkevloic.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kev.projetkevloic.Database.OrnithoDB;
import com.example.kev.projetkevloic.R;
import com.example.kev.projetkevloic.View.add.addOrni;
import com.example.kev.projetkevloic.View.home.MainActivity;

public class Login extends AppCompatActivity {

    Button buttonRegister;
    Button buttonLogin;
    OrnithoDB rDB ;

// SALUT C EST UN TEST
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button) findViewById(R.id.butLog);
        buttonRegister = (Button) findViewById(R.id.butAdd);
        rDB = new OrnithoDB(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, MainActivity.class);

                EditText tusername , tpassword, tage ,tcanton;

                tusername =  (EditText)  findViewById(R.id.editText);
                tpassword =  (EditText)  findViewById(R.id.editText2);

                int id = rDB.checkOrnitho(tusername.getText().toString(),
                        tpassword.getText().toString());

                if(id != -1){
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

    public void goLogin (View view) {
        finish();
        Intent intent = new Intent(Login.this, MainActivity.class);
    }



    public void Register (View view) {
        finish();
        Intent intent = new Intent(Login.this, addOrni.class);

    }
}