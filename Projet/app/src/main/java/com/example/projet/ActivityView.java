package com.example.projet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ActivityView extends AppCompatActivity {

    ListView lst1;
    Button b4;
    ArrayList<String> titles=new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase db = openOrCreateDatabase("SliteDb",Context.MODE_PRIVATE,null);

        lst1 = findViewById(R.id.lst1);
        b4 = findViewById(R.id.bt4);

        final Cursor c = db.rawQuery("select * from records",null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("name");
        int category = c.getColumnIndex("category");
        int price = c.getColumnIndex("price");
        int description = c.getColumnIndex("description");
        titles.clear();


        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,titles);
        lst1.setAdapter(arrayAdapter);

        final  ArrayList<product> stud = new ArrayList<product>();


        if(c.moveToFirst())
        {
            do{
                product stu = new product();
                stu.id = c.getString(id);
                stu.name = c.getString(name);
                stu.category = c.getString(category);
                stu.price = c.getString(price);
                stu.description = c.getString(description);
                stud.add(stu);

                titles.add(c.getString(id) + " \t " + c.getString(name) + " \t "  + c.getString(category) + " \t "  + c.getString(price)+ " \t "  + c.getString(description) );

            } while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            lst1.invalidateViews();

        }
            lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    product stu = stud.get(position);
                    Intent i = new Intent(getApplicationContext(), ActivityEdit.class);
                    i.putExtra("id", stu.id);
                    i.putExtra("name", stu.name);
                    i.putExtra("category", stu.category);
                    i.putExtra("price", stu.price);
                    i.putExtra("description", stu.description);
                    startActivity(i);
                }
            });

        // Bouton "Logout" (Déconnexion)
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effacer les informations de session
                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Effacer toutes les données stockées
                editor.apply();

                // Rediriger l'utilisateur vers l'écran de connexion (mainActivity)
                Intent intent = new Intent(ActivityView.this, MainActivity.class);
                startActivity(intent);
                finish();  // Fermer l'Activity actuelle pour empêcher de revenir en arrière
            }
        });


}}