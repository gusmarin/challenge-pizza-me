package com.gmarin.challenge.pizzame.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gmarin.challenge.pizzame.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
