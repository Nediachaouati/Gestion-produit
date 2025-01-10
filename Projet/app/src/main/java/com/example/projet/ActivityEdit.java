package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityEdit extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.category);
        ed3 = findViewById(R.id.price);
        ed4 = findViewById(R.id.description);
        ed5 = findViewById(R.id.id);
        b1 = findViewById(R.id.bt1);
        b2 = findViewById(R.id.bt2);
        b3 = findViewById(R.id.bt3);
        b4 = findViewById(R.id.bt4);
        Intent i = getIntent();
        String t1 = i.getStringExtra("id").toString();
        String t2 = i.getStringExtra("name").toString();
        String t3 = i.getStringExtra("category").toString();
        String t4 = i.getStringExtra("price").toString();
        String t5 = i.getStringExtra("description").toString();
        ed5.setText(t1);
        ed1.setText(t2);
        ed2.setText(t3);
        ed3.setText(t4);
        ed4.setText(t5);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityView.class);
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit();
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
                
                Intent intent = new Intent(ActivityEdit.this, MainActivity.class);
                startActivity(intent);
                finish();  
            }
        });

    }
    public void Delete()
    {
        try
        {
            String id = ed5.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("SliteDb", Context.MODE_PRIVATE,null);
            String sql = "delete from records where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,id);
            statement.execute();
            Toast.makeText(this,"Product Deleted",Toast.LENGTH_LONG).show();
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


    public void Edit()
    {
        try
        {
            String name = ed1.getText().toString();
            String category = ed2.getText().toString();
            String price = ed3.getText().toString();
            String description = ed4.getText().toString();
            String id = ed5.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);
            String sql = "update records set name = ?,category=?,price=?,description=? where id= ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,name);
            statement.bindString(2,category);
            statement.bindString(3,price);
            statement.bindString(4,description);
            statement.bindString(5,id);
            statement.execute();
            Toast.makeText(this,"Product Updated",Toast.LENGTH_LONG).show();
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
