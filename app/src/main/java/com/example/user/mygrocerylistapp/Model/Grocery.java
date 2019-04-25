package com.example.user.mygrocerylistapp.Model;

/**
 * Created by User on 10/31/2018.
 */

public class Grocery
{
    public String name;
    public String quantity;
    public String dateItemAdded;
    private int id;

    public Grocery()
    {
    }

    public Grocery(String name, String quantity, String dateItemAdded, int id)
    {
        this.name = name;
        this.quantity = quantity;
        this.dateItemAdded = dateItemAdded;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getDateItemAdded()
    {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded)
    {
        this.dateItemAdded = dateItemAdded;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
