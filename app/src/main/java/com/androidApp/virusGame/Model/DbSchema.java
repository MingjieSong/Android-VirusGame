package com.androidApp.virusGame.Model;


import com.google.android.gms.maps.model.LatLng;

class DbSchema {
    //player schema
    static final class PlayerTable {
        static final String NAME = "players";

        static final class Cols {
            static final String ID = "id";
            static final String NAME = "name";
            static final String PASSWORD = "password";
        }
    }
    //virus schema
    static final class VirusTable{
        static final String NAME = "virus";

        static final class Cols {
            static final String ID = "id";
            static final String NAME = "name";
            static final String HITPOINT = "hitpoint";
            static final String LOCATION= "location";

            static final String IMAGE = "image";
        }
    }

    //relational schema
    static final class CaughtVirus{
        static final String NAME = "caught_virus_table";

        static final class Cols{
            static final String ID = "id";
            static final String PLAYER_ID = "player";
            static final String VIRUS_ID = "virus";
        }
    }

}
