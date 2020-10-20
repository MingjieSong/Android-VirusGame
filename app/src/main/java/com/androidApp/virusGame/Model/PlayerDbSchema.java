package com.androidApp.virusGame.Model;

//player schema
class PlayerDbSchema {
    static final class PlayerTable {
        static final String NAME = "players";

        static final class Cols {
            static final String NAME = "name";
            static final String PASSWORD = "password";
        }
    }

}
