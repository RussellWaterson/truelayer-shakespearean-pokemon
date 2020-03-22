package com.russellwaterson.truelayer.shakespeareanpokemon.restservice;

public class Pokemon {

    private final String name;
    private final String description;

    public Pokemon(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
