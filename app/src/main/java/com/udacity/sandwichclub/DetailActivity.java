package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // VIEWS
    private ImageView ingredientsIv;
    private TextView originTv;
    private TextView descriptionTv;
    private TextView placeOfOrigin;
    private TextView ingredientsTv;
    private TextView alsoKnownTv;

    /**
     * Target Sandwich
     */
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        placeOfOrigin = findViewById(R.id.place_of_origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);
        originTv.setText(mSandwich.getMainName());
        descriptionTv.setText(mSandwich.getDescription());
        placeOfOrigin.setText(mSandwich.getPlaceOfOrigin());
        alsoKnownTv.setText(TextUtils.join(", ", mSandwich.getAlsoKnownAs()));
        ingredientsTv.setText(TextUtils.join(", ", mSandwich.getIngredients()));
    }
}
