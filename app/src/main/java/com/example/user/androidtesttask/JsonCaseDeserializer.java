package com.example.user.androidtesttask;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by RICHI on 2014.10.19..
 */
public class JsonCaseDeserializer implements JsonDeserializer<CountryDetail> {

    @Override
    public CountryDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CountryDetail cs = null;
        JsonObject jsonObject = json.getAsJsonObject();
        String capital = "";
        if (jsonObject.has("capital")) {
            capital = jsonObject.get("capital").getAsString();
        }

        String region = "";
        if (jsonObject.has("region")) {
            region = jsonObject.get("region").getAsString();
        }

        double area = 0;
        if (jsonObject.has("area")) {
            area = jsonObject.get("area").getAsDouble();
        }
        int callingCode = 0;
        if (jsonObject.has("callingCodes")) {
            callingCode = jsonObject.get("callingCodes").getAsInt();
        }

        if (jsonObject.has("latlng")) {
            JsonArray array = (JsonArray) jsonObject.get("latlng");
            ArrayList<Float> geoPoints = new ArrayList<Float>();

            float lititude = array.get(0).getAsFloat();
            float longitude = array.get(1).getAsFloat();
            geoPoints.add(lititude);
            geoPoints.add(longitude);
            cs = new CountryDetail();
                 cs.setmCapital(capital);
                 cs.setmRegion(region);
                 cs.setmArea(area);
                 cs.setmCallingCode(callingCode);
                 cs.setGeoPoints(geoPoints);
        }
        return cs;
    }
}
