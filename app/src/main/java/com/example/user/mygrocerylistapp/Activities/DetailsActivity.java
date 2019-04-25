package com.example.user.mygrocerylistapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.mygrocerylistapp.R;

public class DetailsActivity extends AppCompatActivity
{
    private TextView itemName, quantity, dateAdded;
    private int groceryId;
    private Button detEditBtn, detDelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemName = findViewById(R.id.detailsName);
        quantity = findViewById(R.id.detailsQuantity);
        dateAdded = findViewById(R.id.detailsDateAdded);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            itemName.setText(bundle.getString("Name: "));
            quantity.setText(bundle.getString("Quantity: "));
            dateAdded.setText(bundle.getString("Date Added: "));
            groceryId = bundle.getInt("id");
        }
    }
}
