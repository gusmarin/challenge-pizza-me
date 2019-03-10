package com.gmarin.challenge.pizzame.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.viewmodel.Place;
import com.gmarin.challenge.pizzame.viewmodel.PlaceViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlaceViewModel mModel;
    private Observer<PlaceViewModel.PlaceWrapper> mBusinessesObserver;
    private BusinessListAdapter mDataAdapter;
    private FusedLocationProviderClient mLocationClient;

    private static final String PIZZA_TERM = "pizza";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = ViewModelProviders.of(this).get(PlaceViewModel.class);

        initViews();

        mBusinessesObserver = new Observer<PlaceViewModel.PlaceWrapper>() {
            @Override
            public void onChanged(@Nullable final PlaceViewModel.PlaceWrapper places) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);

                if (places.getError() != null) {
                    Toast.makeText(getBaseContext(), "Error: " + places.getError(), Toast.LENGTH_SHORT).show();
                    return;
                }

                findViewById(R.id.locations_list).setVisibility(View.VISIBLE);
                findViewById(R.id.empty_list).setVisibility(View.GONE);
                mDataAdapter.setDataSet(places.getPlaces());
                mDataAdapter.notifyDataSetChanged();
            }
        };

        mModel.getNearestBusinesses().observe(this, mBusinessesObserver);
        mLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initViews() {
        RecyclerView listView = findViewById(R.id.locations_list);
        mDataAdapter = new BusinessListAdapter(this);
        listView.setAdapter(mDataAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listView.setVisibility(View.INVISIBLE);

        ProgressBar spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        Button search = findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                ProgressBar spinner = findViewById(R.id.progressBar);
                spinner.setVisibility(View.VISIBLE);
                // TODO handle no connectivity
                mLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                String latitude = String.valueOf(location.getLatitude());
                                String longitude = String.valueOf(location.getLongitude());
                                mModel.searchNearestBusinessesFrom(latitude, longitude, PIZZA_TERM);
                            }
                        });
            }
        });

    }

    @Override
    protected void onResume() {
        // only in this activity we need the gps permission. check if user has not
        // revoked while we were on the stack.
        if (!isGpsPermissionGranted()) {
            requestGpsPermission();
        }
        super.onResume();
    }

    private boolean isGpsPermissionGranted() {
        String gpsPermission = Manifest.permission.ACCESS_COARSE_LOCATION;
        return checkSelfPermission(gpsPermission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGpsPermission() {
        requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // only one request code
        for(int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Needs GPS permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
