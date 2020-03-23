package com.russellwaterson.truelayer.shakespeareanpokemon.restservice;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpeciesFlavorText;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.russellwaterson.truelayer.shakespeareanpokemon.utils.Constants.*;

public class HttpService {

    private final Logger logger = LoggerFactory.getLogger(HttpService.class);

    private final CloseableHttpClient httpClient;

    public HttpService() {
        httpClient = HttpClients.createDefault();
    }

    public void close() throws IOException {
        httpClient.close();
    }

    public int getPokemonInfo(String name) throws IOException {

        logger.info("Getting pokemon info from its name: " + name.toLowerCase());

        HttpGet request = new HttpGet(POKE_API_URL + name.toLowerCase());

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String result = EntityUtils.toString(entity);

                try {
                    JSONObject jsonResult = new JSONObject(result);
                    return jsonResult.getInt("id");
                } catch (JSONException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
                }

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
            }

        }

    }

    public String getPokemonDescription(int id) {

        logger.info("Getting pokemon description in English from ID: " + id);

        PokeApi pokeApi = new PokeApiClient();
        PokemonSpecies species = pokeApi.getPokemonSpecies(id);

        List<PokemonSpeciesFlavorText> speciesFlavorTexts = species.getFlavorTextEntries();

        for (PokemonSpeciesFlavorText speciesFlavorText : speciesFlavorTexts) {
            if (speciesFlavorText.getLanguage().getName().equals("en")) {
                return speciesFlavorText.getFlavorText()
                        .replace("\r\n", " ")
                        .replace("\n", " ")
                        .replace("é", "e");
            }
        }

        return NO_DESCRIPTION_FOUND;

    }

    public String translateToShakespearean(String text) throws IOException {

        logger.info("Translating the following description to Shakespearean: " + text);

        HttpPost post = new HttpPost(SHAKESPEARE_API_URL);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("text", text));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpResponse response = httpClient.execute(post)) {

            HttpEntity entity = response.getEntity();

            if (entity != null) {

                String result = EntityUtils.toString(entity);

                try {
                    JSONObject jsonResult = new JSONObject(result);
                    if (!jsonResult.has("error")) {
                        return ((JSONObject)jsonResult.get("contents")).getString("translated");
                    } else {
                        JSONObject error = (JSONObject) jsonResult.get("error");
                        throw new ResponseStatusException(
                                Objects.requireNonNull(HttpStatus.resolve(error.getInt("code"))),
                                error.getString("message")
                        );
                    }
                } catch (JSONException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
                }

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
            }

        }

    }

}
