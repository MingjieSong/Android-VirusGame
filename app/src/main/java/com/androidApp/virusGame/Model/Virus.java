package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.Vector;

public class Virus {
    private String NAME;
    private String HITPOINT;
    private String LOCATION;
    private byte[] IMAGE;

    public Virus(String name, String hitpt, String location, byte[] image) {
        NAME = name;
        HITPOINT = hitpt;
        LOCATION = location;
        IMAGE = image;
    }


    public String getName() {
        return NAME;
    }

    public String getHitpt() {
        return HITPOINT;
    }

    public String getLocation() {return LOCATION; }

    public byte[] getImage() {return IMAGE; }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setHITPOINT(String HITPOINT) {
        this.HITPOINT = HITPOINT;
    }

    public void setLOCATION(String LOCATION) {this.LOCATION = LOCATION; }

    public void setIMAGE(byte[] IMAGE){this.IMAGE = IMAGE;}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virus virus = (Virus) o;
        return Objects.equals(NAME, virus.NAME) &&
                Objects.equals(HITPOINT, virus.HITPOINT) &&
                Objects.equals(LOCATION, virus.LOCATION) &&
                Objects.equals(IMAGE, virus.IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(NAME, HITPOINT,LOCATION, IMAGE);
    }

    @Override
    public String toString() {
        return "Virus{" +
                "NAME='" + NAME + '\'' +
                ", HITPOINT='" + HITPOINT + '\'' +
                ", LOCATION='" + LOCATION + '\'' +
                ", IMAGEPATH='" + IMAGE + '\'' +
                '}';
    }
}