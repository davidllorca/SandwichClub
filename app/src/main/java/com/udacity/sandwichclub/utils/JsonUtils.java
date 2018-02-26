package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            Sandwich sandwich = new Sandwich();
            JSONObject nameObject = object.getJSONObject("name");
            sandwich.setMainName(nameObject.getString("mainName"));
            JSONArray akaArray = nameObject.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(parseAsList(akaArray));

            sandwich.setPlaceOfOrigin(object.getString("placeOfOrigin"));
            sandwich.setDescription(object.getString("description"));
            sandwich.setImage(object.getString("image"));

            JSONArray ingredientArray = object.getJSONArray("ingredients");
            sandwich.setIngredients(parseAsList(ingredientArray));

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    private static List<String> parseAsList(JSONArray array) throws JSONException {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            out.add(array.getString(i));
        }
        return out;
    }
}
