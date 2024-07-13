package com.example.tailormanagementsystem;

import java.io.Serializable;

class Address implements Serializable
{

    private String city;
    private String town;
    private String block;

    Address(String city,String town,String block)
    {
        this.setCity(city);
        this.setTown(town);
        this.setBlock(block);

    }
    Address(Address a)
    {
        this.city=a.city;
        this.town=a.town;
        this.block=a.block;

    }
    public void setCity(String city)
    {
        this.city=city;

    }
    public void setTown(String town)
    {
        this.town=town;

    }
    public void setBlock(String block)
    {
        this.block=block;

    }
    public String getCity()
    {
        return this.city;
    }

    public String getTown()
    {
        return this.town;

    }

    public String getBlock()
    {
        return this.block;
    }
    @Override
    public boolean equals(Object o)
    {
        Address add=(Address)o;
        return this.city.equals(add.city)&&this.town.equals(add.town)&&this.block.equals(add.block);
    }
    @Override
    public String toString()
    {
        return String.format("City: %s \tTown:%s \tBlock: %s",this.city,this.town,this.block);

    }
    @Override
    public Object clone()
    {
        return new Address(this.city,this.town,this.block);
    }

}