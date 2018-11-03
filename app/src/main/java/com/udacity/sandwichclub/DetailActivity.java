package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.apache.commons.lang3.StringUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.extractFeatureFromJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

            Picasso.with(this)
                    .load(sandwich.getImage())
                    .error(R.drawable.nophoto)
                    .into(ingredientsIv);


        if (sandwich.getMainName().equals("")){
            setTitle(R.string.activity2_name);
        }
        else {
            setTitle(sandwich.getMainName());
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich currentSandwich) {

        //Main Name
        TextView nameOfTheSandwich = findViewById(R.id.sandwich_name);
        if (currentSandwich.getMainName().equals("")){
            nameOfTheSandwich.setText(R.string.missing_data_message);
        }
        else {
            nameOfTheSandwich.setText(currentSandwich.getMainName());
        }

        //Also Known As
        TextView alternativeNameOfTheSandwich = findViewById(R.id.also_known_as);
        if (currentSandwich.getAlsoKnownAs().isEmpty()){
            alternativeNameOfTheSandwich.setText(R.string.missing_data_message );
        }
        else {
            StringBuilder listStringBuilder = new StringBuilder();
            for (String s : currentSandwich.getAlsoKnownAs())
                listStringBuilder.append(s + ", ");
            String listString = String.valueOf(listStringBuilder);
            listString = StringUtils.removeEnd(listString, ", ");
            alternativeNameOfTheSandwich.setText(listString);
        }

        //Ingredients
        TextView ingredientsList = findViewById(R.id.ingredients_list);
        if(currentSandwich.getIngredients().isEmpty() ){
            ingredientsList.setText(R.string.missing_data_message);
        }
        else {
            StringBuilder ingredientsListStringBuilder = new StringBuilder();
            for (String str : currentSandwich.getIngredients())
                ingredientsListStringBuilder.append(str + ", ");
            String ingredientsListString = String.valueOf(ingredientsListStringBuilder);
          ingredientsListString = StringUtils.removeEnd(ingredientsListString, ", ");
            ingredientsList.setText(ingredientsListString);
        }

        //Place of Origin
        TextView placeOfOrigin = findViewById(R.id.place_of_origin);
        if (currentSandwich.getPlaceOfOrigin().equals("")){
            placeOfOrigin.setText( R.string.missing_data_message);
        }
        else {
            placeOfOrigin.setText(currentSandwich.getPlaceOfOrigin());
        }

        //Description
        TextView description = findViewById(R.id.sandwich_description);
        if (currentSandwich.getDescription().equals("")){
            description.setText( String.valueOf(R.string.missing_data_message));
        }
        else {
            description.setText(currentSandwich.getDescription());
        }
    }
}
