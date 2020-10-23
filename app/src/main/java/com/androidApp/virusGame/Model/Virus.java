package com.androidApp.virusGame.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Virus {
    private String NAME;
    private String HITPOINT;


    public Virus(String name, String hitpt) {
        NAME = name;
        HITPOINT = hitpt;
    }

    public String getName() {
        return NAME;
    }

    public String getHitpt() {
        return HITPOINT;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setHITPOINT(String HITPOINT) {
        this.HITPOINT = HITPOINT;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Virus virus = (Virus) o;
        return Objects.equals(NAME, virus.NAME) &&
                Objects.equals(HITPOINT, virus.HITPOINT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(NAME, HITPOINT);
    }

    @Override
    public String toString() {
        return "Virus{" +
                "NAME='" + NAME + '\'' +
                ", HITPOINT='" + HITPOINT + '\'' +
                '}';
    }
}