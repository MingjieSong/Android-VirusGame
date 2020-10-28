package com.androidApp.virusGame.Model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Player {
    private String mName;
    private String mPassword;

    public Player(String name, String password) {
        mName = name;
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public String getPassword() {
        return mPassword;
    }


    public void setName(String mName) {
        this.mName = mName;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }


    //override equal, hashcode and toString
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(mName, player.mName) &&
                Objects.equals(mPassword, player.mPassword);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mName, mPassword);
    }


    @Override
    public String toString() {
        return "Player{" +
                "mName='" + mName + '\'' +
                ", mPassword='" + mPassword + '\'' +

        '}';
    }
}
