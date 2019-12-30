package com.example.bitgaram.main.bitgaram.presenter.main;

public class Environment {
    public static Environment singleton;

    public Environment newInstance() {
        if(singleton == null) {
            singleton = new Environment();
        }

        return singleton;
    }

    public static void saveEnvironment() {

    }

    public static void
}
