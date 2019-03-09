package com.gmarin.challenge.pizzame.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmarin.challenge.pizzame.PizzaMeApplication;
import com.gmarin.challenge.pizzame.data.model.Business;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.gmarin.challenge.pizzame.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    protected static final String EXTRA_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent != null && intent.getExtras() == null) {
            finish();
            return;
        }
        // TODO create another api interface to get a business through ID in case its not in cache
        Business business = ((PizzaMeApplication)getApplication()).getBusiness(intent.getStringExtra(EXTRA_ID));
        getSupportActionBar().setTitle(business.getName());
        initViews(business);
    }

    private void initViews(final Business business) {
        setContentView(R.layout.business_detail_view);
        TextView nameView = findViewById(R.id.business_name_text_view);
        TextView distanceView = findViewById(R.id.business_distance_text_view);
        final TextView addressView = findViewById(R.id.business_address_text_view);
        TextView phoneView = findViewById(R.id.business_phone_text_view);
        ImageView imageView = findViewById(R.id.business_image_view);

        String distance = (new DecimalFormat("##.##").format(business.getDistance()));
        distanceView.setText(distance + " mi");
        // move to another location
        StringBuilder sb = new StringBuilder();
        for (String address : business.getLocation().getDisplay_address()) {
            sb.append(address);
            sb.append(" ");
        }
        addressView.setText(sb.toString().trim());
        phoneView.setText(business.getDisplay_phone());
        Picasso.get().load(business.getImage_url()).fit().into(imageView);

        Linkify.addLinks(phoneView, Linkify.PHONE_NUMBERS);
        phoneView.setLinkTextColor(getResources().getColor(R.color.colorPrimary, getTheme()));

        addressView.setOnClickListener(new View.OnClickListener() {
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
