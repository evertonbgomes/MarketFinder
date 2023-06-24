package com.example.marketfinder.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.webkit.WebView;

import com.example.marketfinder.Manifest;
import com.example.marketfinder.R;

public class MarketDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
    }
    public void buscarInformacoesGPS(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION)   != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MarketDetailActivity.this, new String[] {Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, 1);
            ActivityCompat.requestPermissions(MarketDetailActivity.this, new String[] {Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, 1);
            ActivityCompat.requestPermissions(MarketDetailActivity.this, new String[] {Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, 1);
            return;
        }

        LocationManager mLocManager  = (LocationManager) getSystemService(MarketDetailActivity.this.LOCATION_SERVICE);
        LocationListener mLocListener = new Local();

        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            String texto = "Latitude.: " + Local.latitude + "\n" +
                    "Longitude: " + Local.longitude + "\n";
            Toast.makeText(MarketDetailActivity.this, texto, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MarketDetailActivity.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
        }

        this.mostrarGoogleMaps(Local.latitude, Local.longitude);
    }

    public void mostrarGoogleMaps(double latitude, double longitude) {
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);
    }
}