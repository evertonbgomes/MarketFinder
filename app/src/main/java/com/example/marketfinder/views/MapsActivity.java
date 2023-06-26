package com.example.marketfinder.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.marketfinder.R;
import com.example.marketfinder.databinding.ActivityMapsBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Marker marker;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private Button buttonConfirm;
    private LatLng selectedLatLng;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        buttonConfirm = findViewById(R.id.button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLatLng != null) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude", selectedLatLng.latitude);
                    intent.putExtra("longitude", selectedLatLng.longitude);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(MapsActivity.this, "Selecione uma localização no mapa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnLimpar.setOnClickListener(v -> {
            if (marker != null) {
                marker.remove();
                selectedLatLng = null;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_app_id));
        }

        // Configurar o AutocompleteSupportFragment
        autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteSupportFragment.setCountry("BR"); // Defina o país desejado

        // Lidar com a seleção de um lugar pelo usuário
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Atualizar o marcador no mapa com o local selecionado
                LatLng location = place.getLatLng();
                updateMarker(location);
            }

            @Override
            public void onError(Status status) {
                // Lidar com erros na seleção do lugar
                Log.e("AutocompleteError", status.getStatusMessage());
            }
        });
    }

    private void updateMarker(LatLng location) {
        // Remover o marcador existente (se houver)
        if (marker != null) {
            marker.remove();
        }

        // Adicionar um novo marcador com a localização selecionada
        marker = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Selected Location"));

        selectedLatLng = location; // Salvar a localização selecionada
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }

                selectedLatLng = latLng;
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                marker = mMap.addMarker(markerOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtém o provedor de serviços de localização
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            // Obtém a última localização conhecida do dispositivo
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Obtém as coordenadas da localização atual
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Move a câmera para a localização atual
                        LatLng currentLocation = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

                        // Adiciona um marcador na localização atual
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(currentLocation)
                                .title("Current Location"));
                    }
                }
            });
        } else {
            // Caso não tenha permissão, solicite-a ao usuário
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng));

        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                String fullAddress = address.getAddressLine(0);
                Toast.makeText(this, fullAddress, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}