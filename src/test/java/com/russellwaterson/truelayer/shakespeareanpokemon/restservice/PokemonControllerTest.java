package com.russellwaterson.truelayer.shakespeareanpokemon.restservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class PokemonControllerTest {

    private PokemonController controller;

    @BeforeEach
    void setUp() {
        controller = new PokemonController();
    }

    @Test
    void pokemonRestCall_successful() throws Exception {

        final String pokemonName = "charizard";

        final String pokemonShakespeareanDescription = "Charizard flies 'round the sky in search of powerful " +
                "opponents. 't breathes fire of such most wondrous heat yond 't melts aught. However,  't nev'r turns " +
                "its fiery breath on any opponent weaker than itself.";

        Pokemon charizard = new Pokemon(pokemonName, pokemonShakespeareanDescription);

        assertEquals(charizard.getName(), controller.pokemon(pokemonName).getName());
        assertEquals(charizard.getDescription(), controller.pokemon(pokemonName).getDescription());

    }

    @Test
    void pokemonRestCall_badPokemon() throws Exception {

        try {
            controller.pokemon("russell");
            fail();
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
            assertEquals("Not Found", e.getReason());
        }

    }
}