package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.Vector;

public class Virus {
    private String NAME;
    private int HITPOINT;
    private String LOCATION;


    public Virus(String name, int hitpt /*, String location*/) {
        NAME = name;
        HITPOINT = hitpt;
        //LOCATION = location;
    }

    public String getName() {
        return NAME;
    }

    public int getHitpt() {
        return HITPOINT;
    }

    public String getLocation() {return LOCATION; }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setHITPOINT(int HITPOINT) {
        this.HITPOINT = HITPOINT;
    }

    public void setLOCATION(String LOCATION) {this.LOCATION = LOCATION; }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virus virus = (Virus) o;
        return Objects.equals(NAME, virus.NAME) &&
                Objects.equals(HITPOINT, virus.HITPOINT);
                //&& Objects.equals(LOCATION, virus.LOCATION);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(NAME, HITPOINT,LOCATION);
    }

    @Override
    public String toString() {
        return "Virus{" +
                "NAME='" + NAME + '\'' +
                ", HITPOINT='" + HITPOINT + '\'' +
                //", LOCATION='" + LOCATION + '\'' +
                '}';
    }
}