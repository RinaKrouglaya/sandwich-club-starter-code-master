package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static android.provider.Settings.Global.getString;

/**
 * Utility class with methods to help
 * parse JSON.
 */

public final class JsonUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * Return an {@link Sandwich} object by parsing out information
     * about the sandwich from JSON string.
     */
    public static Sandwich extractFeatureFromJson(String sandwichJSON) {
        String mainName = "";
        List<String> listAlternativeNames = Arrays.asList((""));
        String placeOfOrigin = "";
        String description = "";
        String linkToImage = "";
        List<String> listOfIngredients = Arrays.asList((""));

        try {
            JSONObject baseJson = new JSONObject(sandwichJSON);
            JSONObject nameObject = baseJson.getJSONObject("name");

            // If there are results in the name object
            if (nameObject.length() > 0) {

                // Extract out the first element - String mainName
                mainName = nameObject.getString("mainName");

                // Extract out the second element - List<String>  listAlternativeNames
                JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");
                String[] arrAlternativeNames = new String[alsoKnownAsArray.length()];
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    arrAlternativeNames[i] = alsoKnownAsArray.getString(i);
                }
                listAlternativeNames = Arrays.asList(arrAlternativeNames);
            }

                // Extract out the third element - String placeOfOrigin
               placeOfOrigin = baseJson.getString("placeOfOrigin");

                // Extract out the forth element - String description
                description = baseJson.getString("description");
                description = description.replaceAll("\\s+", " ");

                // Extract out the fifth element - String image - the link to the
                // sandwich image on the web
                linkToImage = baseJson.getString("image");


                // Extract out the sixth element - List<String>  ingredients

                JSONArray ingredientsArray =  baseJson.getJSONArray("ingredients");
                    String[] arrIngredients = new String[ingredientsArray.length()];
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        arrIngredients[i] = ingredientsArray.getString(i);
                    }
                    listOfIngredients = Arrays.asList(arrIngredients);



        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        // Create a new {@link Sandwich} object
        return new Sandwich ( mainName, listAlternativeNames,  placeOfOrigin, description, linkToImage, listOfIngredients);
    }

  //  public static Sandwich parseSandwichJson(String json) {
    //    return null;
    //}
}
