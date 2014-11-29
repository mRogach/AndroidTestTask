package com.example.user.androidtesttask;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonCaseDeserializer implements JsonDeserializer<Country> {

    @Override
    public Country deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Country country = null;
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray countryArray = jsonObject.get("result").getAsJsonArray();
        for (int j = 0; j < countryArray.size(); j++) {
            country = new Country();
            JsonObject countryJson = (JsonObject) countryArray.get(j);
            String code = "";
            if (countryJson.has("code")) {
                code = countryJson.get("code").getAsString();
            }

            String name = "";
            if (countryJson.has("name")) {
                name = countryJson.get("name").getAsString();
            }


            ArrayList<State> states = new ArrayList<State>();
            if (countryJson.has(String.valueOf(true))) {
                JsonObject stateObject = countryJson.getAsJsonObject();
                JsonArray jsonArray = stateObject.get("states").getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    State state = new State();
                    JsonObject stateJson = (JsonObject) jsonArray.get(i);
                    String stateCode = stateJson.get("code").getAsString();
                    String stateName = stateJson.get("name").getAsString();
                    state.setmCode(stateCode);
                    state.setmName(stateName);
                    states.add(state);
                }
            }
            country.setCode(code);
            country.setName(name);
            country.setmStates(states);
        }
        return country;

    }
}
