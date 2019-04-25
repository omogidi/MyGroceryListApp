package com.example.user.mygrocerylistapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.mygrocerylistapp.Data.DatabaseHandler;
import com.example.user.mygrocerylistapp.Model.Grocery;
import com.example.user.mygrocerylistapp.R;

public class MainActivity extends AppCompatActivity
{
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private EditText groceryItem, groceryQuantity;
    private Button saveButton;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        bypassAddActivity();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                createPopupDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createPopupDialog () {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);
        groceryItem = view.findViewById(R.id.groceryItem);
        groceryQuantity = view.findViewById(R.id.groceryQty);
        saveButton = view.findViewById(R.id.saveBtn);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Todo: SaveToDb
                //Todo: Go to next screen
                if (!groceryItem.getText().toString().isEmpty() && !groceryQuantity.getText().toString().isEmpty()) {
                    saveGroceryToDb(v);
                }

            }
        });

    }

    private void saveGroceryToDb(View v)
    {
            Grocery grocery = new Grocery();
            String newGrocery = groceryItem.getText().toString();
            String newGroceryQuantity = groceryQuantity.getText().toString();

            grocery.setName(newGrocery);
            grocery.setQuantity(newGroceryQuantity);

            //Save to Db
            db.addGrocery(grocery);
            Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();
            //Log.d("Item Added", String.valueOf(db.getGroceryCount()));
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    alertDialog.dismiss();
                    //start a new Activity
                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                }
            }, 1000);
    }

    //Bypass activity to goto list activity if there is item in the database
    public void bypassAddActivity() {
        if (db.getGroceryCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
