package com.russellwaterson.truelayer.shakespeareanpokemon.restservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServiceTest {

    HttpService httpService;

    @BeforeEach
    void setUp() {
        httpService = new HttpService();
    }

    @AfterEach
    void tearDown() throws IOException {
        httpService.close();
    }

    @Test
    void getPokemonInfo_successfulCall() throws Exception {

        assertEquals(1, httpService.getPokemonInfo("bulbasaur"));
        assertEquals(6, httpService.getPokemonInfo("charizard"));
        assertEquals(6, httpService.getPokemonInfo("Charizard"));
        assertEquals(6, httpService.getPokemonInfo("CHARIZARD"));

    }

    @Test
    void getPokemonInfo_notFound() {

        try {
            httpService.getPokemonInfo("russell");
            fail();
        } catch (IOException e) {
            fail();
        } catch (ResponseStatusException e) {
            assertEquals("404 NOT_FOUND \"Not Found\"", e.getMessage());
        }

    }

    @Test
    void getPokemonDescription_successfulCall() {

        String bulbasaurDescription = "Bulbasaur can be seen napping in bright sunlight. " +
                "There is a seed on its back. By soaking up the sun’s rays, " +
                "the seed grows progressively larger.";

        assertEquals(bulbasaurDescription, httpService.getPokemonDescription(1));

    }

    @Test
    void getPokemonDescription_sanitisePokemonE() {

        String porygonDescription = "This Pokemon was created using the cutting-edge science of 20 years ago, so " +
                "many parts of it have since become obsolete.";

        assertEquals(porygonDescription, httpService.getPokemonDescription(137));

    }

    @Test
    void translateToShakespearean() throws IOException {

        String inputDescription = "Charizard flies around the sky in search of powerful opponents.\n" +
                "It breathes fire of such great heat that it melts anything.\n" +
                "However, it never turns its fiery breath on any opponent\n" +
                "weaker than itself.";

        String expectedTranslation = "Charizard flies 'round the sky in search of powerful opponents. 't breathes " +
                "fire of such most wondrous heat yond 't melts aught. However,  't nev'r turns its fiery breath on " +
                "any opponent weaker than itself.";

        assertEquals(expectedTranslation, httpService.translateToShakespearean(inputDescription));
    }
}