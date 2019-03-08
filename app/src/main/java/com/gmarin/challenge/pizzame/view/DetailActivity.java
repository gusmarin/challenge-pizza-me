package com.gmarin.challenge.pizzame.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.gmarin.challenge.pizzame.PizzaMeApplication;
import com.gmarin.challenge.pizzame.data.model.Business;

import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.gmarin.challenge.pizzame.R;

public class DetailActivity extends AppCompatActivity {
    protected static final String EXTRA_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
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

    private void initViews(Business business) {
        setContentView(R.layout.business_detail_view);
        TextView nameView = findViewById(R.id.business_name_text_view);
        TextView distanceView = findViewById(R.id.business_distance_text_view);
        TextView addressView = findViewById(R.id.business_address_text_view);
        TextView phoneView = findViewById(R.id.business_phone_text_view);

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
