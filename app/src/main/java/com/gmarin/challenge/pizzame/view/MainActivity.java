package com.gmarin.challenge.pizzame.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import com.gmarin.challenge.pizzame.PizzaMeApplication;
import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.viewmodel.BusinessViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BusinessViewModel mModel;
    private Observer<List<Business>> mBusinessesObserver;
    private BusinessListAdapter mDataAdapter;
    private FusedLocationProviderClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = ViewModelProviders.of(this).get(BusinessViewModel.class);

        initViews();

        mBusinessesObserver = new Observer<List<Business>>() {
            @Override
            public void onChanged(@Nullable final List<Business> businesses) {
                if (businesses != null) {
                    mDataAdapter.setDataSet(businesses);
                    mDataAdapter.notifyDataSetChanged();
                    ((PizzaMeApplication)getApplication()).setBusinessCache(businesses);
                }
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
        listView.setHasFixedSize(true);

        Button search = findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                // TODO handle no connectivity
                mLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                String latitude = String.valueOf(location.getLatitude());
                                String longitude = String.valueOf(location.getLongitude());
                                mModel.searchNearestBusinessesFrom(latitude, longitude);
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
