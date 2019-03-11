package com.gmarin.challenge.pizzame.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.app.NavUtils;

import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.data.entity.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    protected static final String EXTRA_NAME = "name";
    protected static final String EXTRA_IMAGE_URL = "imgUrl";
    protected static final String EXTRA_ADDRESS = "address";
    protected static final String EXTRA_PHONE_NUMBER = "phoneNumber";
    protected static final String EXTRA_REVIEW_COUNT = "reviewCount";
    protected static final String EXTRA_RATING = "rating";
    protected static final String EXTRA_DISTANCE = "distance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras() == null) {
            finish();
            return;
        }
        // TODO create another api interface to get a business through ID in case its not in cache
        Place place = fromBundle(getIntent().getExtras());
        getSupportActionBar().setTitle(place.getName());
        initViews(place);
    }

    private Place fromBundle(Bundle bundle) {
        Place.PlaceBuilder builder = new Place.PlaceBuilder();
        builder.setName(bundle.getString(EXTRA_NAME));
        builder.setAddress(bundle.getString(EXTRA_ADDRESS));
        builder.setReviewCount(bundle.getInt(EXTRA_REVIEW_COUNT));
        builder.setPhoneNumber(bundle.getString(EXTRA_PHONE_NUMBER));
        builder.setRating(bundle.getFloat(EXTRA_RATING));
        builder.setImageUrl(bundle.getString(EXTRA_IMAGE_URL));
        builder.setDistance(bundle.getDouble(EXTRA_DISTANCE));
        return builder.build();
    }

    private void initViews(final Place place) {
        setContentView(R.layout.business_detail_view);
        TextView distanceView = findViewById(R.id.business_distance_text_view);
        final TextView addressView = findViewById(R.id.business_address_text_view);
        TextView phoneView = findViewById(R.id.business_phone_text_view);
        ImageView imageView = findViewById(R.id.business_image_view);
        FloatingActionButton mapButton = findViewById(R.id.floating_map_button);
        AppCompatRatingBar ratingBar = findViewById(R.id.business_rating_bar);
        TextView ratingView = findViewById(R.id.business_rating_count);

        String distance = (new DecimalFormat("##.##").format(place.getDistance()));
        distanceView.setText(distance + " mi");

        addressView.setText(place.getAddress());
        phoneView.setText(place.getPhoneNumber());
        if (TextUtils.isEmpty(place.getImageUrl())) {
            Picasso.get().load(android.R.drawable.ic_menu_gallery).into(imageView);
        } else {
            Picasso.get().load(place.getImageUrl())
                    .fit()
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageView);
        }

        ratingBar.setRating(place.getRating());
        ratingBar.setIsIndicator(true);
        ratingView.setText(place.getReviewCount() + " Reviews");

        Linkify.addLinks(phoneView, Linkify.PHONE_NUMBERS);
        phoneView.setLinkTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder uriBuilder = new StringBuilder();
                uriBuilder.append("geo:0,0?q=");
                uriBuilder.append(addressView.getText());
                Uri gmmIntentUri = Uri.parse(uriBuilder.toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
