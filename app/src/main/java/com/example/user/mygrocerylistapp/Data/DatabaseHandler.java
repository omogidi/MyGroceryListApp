package com.example.user.mygrocerylistapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.mygrocerylistapp.Model.Grocery;
import com.example.user.mygrocerylistapp.Util.Constant;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 10/31/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{
    private Context ctx;
    public DatabaseHandler(Context context)
    {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constant.TABLE_NAME + "("
                +Constant.KEY_ID + " INTEGER PRIMARY KEY," + Constant.KEY_GROCERY_ITEM + " TEXT," +
                Constant.KEY_QUANTITY_NO + " TEXT," + Constant.KEY_DATE_NAME + " LONG);";
        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        onCreate(db);
    }

    /**
     *  CRUD OPERATIIONS
     */

    //Add grocery
    public void addGrocery (Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constant.KEY_QUANTITY_NO, grocery.getQuantity());
        values.put(Constant.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert the row
        db.insert(Constant.TABLE_NAME, null, values);
    }

    //Get grocery item
    public Grocery getGrocery (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constant.TABLE_NAME, new String[] {Constant.KEY_ID, Constant.KEY_GROCERY_ITEM, Constant.KEY_QUANTITY_NO,
        Constant.KEY_DATE_NAME}, Constant.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null,
                null, null);

        if (cursor != null)
            cursor.moveToFirst();

            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constant.KEY_QUANTITY_NO)));

            //convert timestamp to real date format

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATE_NAME))).getTime());
            grocery.setDateItemAdded(formattedDate);
            return grocery;
    }

    //Get all grocery
    public List<Grocery> getAllGrocery () {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Grocery> listAllGrocery = new ArrayList<>();
        Cursor cursor = db.query(Constant.TABLE_NAME, new String[] {Constant.KEY_ID, Constant.KEY_GROCERY_ITEM, Constant.KEY_QUANTITY_NO,
        Constant.KEY_DATE_NAME}, null, null, null, null, Constant.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constant.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constant.KEY_QUANTITY_NO)));

                //convert timestamp to readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constant.KEY_DATE_NAME))).getTime());
                grocery.setDateItemAdded(formattedDate);

                //Add to the grocery list
                listAllGrocery.add(grocery);
            } while (cursor.moveToNext());
        }
         return listAllGrocery;
    }

    //Update grocery
    public int updateGrocery (Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.KEY_GROCERY_ITEM, grocery.getName());
        values.put(Constant.KEY_QUANTITY_NO, grocery.getQuantity());
        values.put(Constant.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //update row
       return db.update(Constant.TABLE_NAME, values, Constant.KEY_ID + "=?", new String[] {String.valueOf(grocery.getId())} );
    }

    //Delete grocery
    public void deleteGrocery (int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constant.TABLE_NAME, Constant.KEY_ID + "=?", new String[] {String.valueOf(id)});
        db.close();

    }

    //Get count
    public int getGroceryCount() {
        String COUNT_QUERY = " SELECT * FROM " + Constant.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(COUNT_QUERY, null);
        return cursor.getCount();
    }
}
