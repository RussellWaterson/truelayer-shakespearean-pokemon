package com.russellwaterson.truelayer.shakespeareanpokemon.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.russellwaterson.truelayer.shakespeareanpokemon.utils.Constants.NO_DESCRIPTION_FOUND;

@RestController
public class PokemonController {

    @GetMapping("/pokemon/{name}")
    public Pokemon pokemon(@PathVariable String name) throws IOException {

        HttpService httpService = new HttpService();
        String translation;

        try {

            int id = httpService.getPokemonInfo(name);
            String description = httpService.getPokemonDescription(id);
            translation = description.equals(NO_DESCRIPTION_FOUND) || description.isEmpty()
                    ? NO_DESCRIPTION_FOUND
                    : httpService.translateToShakespearean(description);

        } finally {
            httpService.close();
        }

        return new Pokemon(name, translation);
    }

}
