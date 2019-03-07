package com.gmarin.challenge.pizzame.view;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.widget.Toast;

import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.viewmodel.BusinessViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BusinessViewModel mModel;
    private Observer<List<Business>> mBusinessesObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = ViewModelProviders.of(this).get(BusinessViewModel.class);

        mBusinessesObserver = new Observer<List<Business>>() {
            @Override
            public void onChanged(@Nullable final List<Business> businesses) {
                for (Business b : businesses) {
                    Log.d("Business", b.getName());
                }
            }
        };

        mModel.getNearestBusinesses().observe(this, mBusinessesObserver);

    }

    @Override
    protected void onResume() {
        // only in this activity we need the gps permission. check if user has not
        // revoked while we were on the stack.
        if (!isGpsPermissionGranted()) {
            requestGpsPermission();
        } else {
            // TODO hook up google fuse location and UI triggers
            // TODO handle no connectivity
            mModel.searchNearestBusinessesFrom("40.54", "-74.58");
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
