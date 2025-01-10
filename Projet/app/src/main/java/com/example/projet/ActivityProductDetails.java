package com.example.projet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityProductDetails extends AppCompatActivity {


    private TextView ed1, ed2, ed3, ed4;
    private Button btnAddToCart,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.category);
        ed3 = findViewById(R.id.price);
        ed4 = findViewById(R.id.description);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        b4 = findViewById(R.id.bt4);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");


        ed1.setText("Name : " + name);
        ed2.setText("Catégorie : " + category);
        ed3.setText("Prix : " + price);
        ed4.setText("Description : " + description);


        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(this,"Product add to cart",Toast.LENGTH_LONG).show();
        });

        // Bouton Logout
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              
                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();  // Effacer toutes les données stockées
                editor.apply();
                Intent intent = new Intent(ActivityProductDetails.this, MainActivity.class);
                startActivity(intent);
                finish(); 
            }
        });
    }
}
