package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActivityAdd extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4;
    Button b1,b2,b4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.category);
        ed3 = findViewById(R.id.price);
        ed4 = findViewById(R.id.description);
        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);
        b4 = findViewById(R.id.bt4);
        //page view
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), ActivityView.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        // Bouton Logout
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Effacer toutes les données stockées
                editor.apply();

                Intent intent = new Intent(ActivityAdd.this, MainActivity.class);
                startActivity(intent);
                finish();  // Fermer l'Activity actuelle pour empêcher de revenir en arrière
            }
        });
    }
    public void insert()
    {
        try
        {
            String name = ed1.getText().toString();
            String category = ed2.getText().toString();
            String price = ed3.getText().toString();
            String description = ed4.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,category VARCHAR,price VARCHAR,description VARCHAR)");
            String sql = "insert into records(name,category,price,description)values(?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,name);
            statement.bindString(2,category);
            statement.bindString(3,price);
            statement.bindString(4,description);
            statement.execute();
            Toast.makeText(this,"Product added",Toast.LENGTH_LONG).show();
            ed1.setText("");
            ed2.setText("");
            ed3.setText("");
            ed4.setText("");
            ed1.requestFocus();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Product Fail",Toast.LENGTH_LONG).show();
        }
    }


}
