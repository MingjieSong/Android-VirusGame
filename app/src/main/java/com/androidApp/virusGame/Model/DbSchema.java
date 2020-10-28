package com.androidApp.virusGame.Model;


class DbSchema {
    //player schema
    static final class PlayerTable {
        static final String NAME = "players";

        static final class Cols {
            static final String NAME = "name";
            static final String PASSWORD = "password";
        }
    }
    //virus schema
    static final class VirusTable{
        static final String NAME = "virus";

        static final class Cols {
            static final String NAME = "name";
            static final String HITPOINT = "hitpoint";
            //static final String LOCATION = "location";

        }
    }

}
