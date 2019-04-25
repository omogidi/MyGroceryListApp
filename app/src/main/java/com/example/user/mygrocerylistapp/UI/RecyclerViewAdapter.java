package com.example.user.mygrocerylistapp.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.mygrocerylistapp.Activities.DetailsActivity;
import com.example.user.mygrocerylistapp.Data.DatabaseHandler;
import com.example.user.mygrocerylistapp.Model.Grocery;
import com.example.user.mygrocerylistapp.R;

import java.util.List;

/**
 * Created by User on 10/31/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<Grocery> groceryItems;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryItems)
    {
        this.context = context;
        this.groceryItems = groceryItems;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position)
    {
        Grocery grocery = groceryItems.get(position);
        holder.groceryItemName.setText(grocery.getName());
        holder.quantity.setText(grocery.getQuantity());
        holder.dateAdded.setText(grocery.getDateItemAdded());
    }

    @Override
    public int getItemCount()
    {
        return groceryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView groceryItemName, quantity, dateAdded;
        public Button editButton, deleteButton;
        public int id;
        public ViewHolder(View view, Context ctx)
        {
            super(view);
            context = ctx;
            groceryItemName = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.qty);
            dateAdded = view.findViewById(R.id.dateAdded);

            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);


                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Todo: go to next
                        int position = getAdapterPosition();
                        Grocery grocery = groceryItems.get(position);
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("Name: ", grocery.getName());
                        intent.putExtra("Quantity: ", grocery.getQuantity());
                        intent.putExtra("id: ", grocery.getId());
                        intent.putExtra("Date Added: ", grocery.getDateItemAdded());
                        context.startActivity(intent);
                    }
                });
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId()) {
                case R.id.editButton:
                    int updatePosition = getAdapterPosition();
                    Grocery updateGrocery = groceryItems.get(updatePosition);
                    editItem(updateGrocery);
                    break;
                case R.id.deleteButton:
                    int position = getAdapterPosition();
                    Grocery grocery = groceryItems.get(position);
                    deleteItem(grocery.getId());
                    break;
            }
        }

        public void editItem (final Grocery grocery) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.pop_up, null);

            final EditText groceryItem = view.findViewById(R.id.groceryItem);
            final EditText groceryQty = view.findViewById(R.id.groceryQty);
            final TextView title = view.findViewById(R.id.tileID);
            title.setText("EDit Grocery");
            Button saveButton = view.findViewById(R.id.saveBtn);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            saveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                        DatabaseHandler db = new DatabaseHandler(context);
                        grocery.setName(groceryItem.getText().toString());
                        grocery.setQuantity(groceryQty.getText().toString());

                        if (!groceryItem.getText().toString().isEmpty() && !groceryQty.getText().toString().isEmpty()) {
                            db.updateGrocery(grocery);
                            notifyItemChanged(getAdapterPosition(), grocery);
                            dialog.dismiss();
                        } else {
                            Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                        }
                }
            });
        }

        public void deleteItem (final int id) {
            //create an alert dialog
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noBtn = view.findViewById(R.id.noBtn);
            Button yesBtn = view.findViewById(R.id.yesBtn);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            noBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();
                }
            });

            yesBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    DatabaseHandler db = new DatabaseHandler(context);
                    //delete item
                    db.deleteGrocery(id);
                    groceryItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

        }
    }
}
