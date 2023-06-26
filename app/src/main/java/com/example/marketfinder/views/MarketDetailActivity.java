package com.example.marketfinder.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marketfinder.R;
import com.example.marketfinder.model.MarketModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MarketDetailActivity extends AppCompatActivity {

    private EditText editTextMarketName;
    private EditText editTextMarketAddress;
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private Button buttonSalvar;
    private static final int LOCATION_REQUEST_CODE = 2;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);

        editTextMarketName = findViewById(R.id.editText_market_name);
        editTextMarketAddress = findViewById(R.id.editText_market_address);
        textViewLatitude = findViewById(R.id.textView_latitude);
        textViewLongitude = findViewById(R.id.textView_longitude);
        buttonSalvar = findViewById(R.id.button_salvar);

        // Inicialize o FirebaseDatabase
        databaseReference = FirebaseDatabase.getInstance().getReference("markets");

        Button buttonGetLocation = findViewById(R.id.button_get_location);
        buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketDetailActivity.this, MapsActivity.class);
                startActivityForResult(intent, LOCATION_REQUEST_CODE);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marketName = editTextMarketName.getText().toString().trim();
                String marketAddress = editTextMarketAddress.getText().toString().trim();
                String latitudeString = textViewLatitude.getText().toString().trim();
                String longitudeString = textViewLongitude.getText().toString().trim();

                if (marketName.isEmpty() || marketAddress.isEmpty() || latitudeString.isEmpty() || longitudeString.isEmpty()) {
                    Toast.makeText(MarketDetailActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double latitudeValue = Double.parseDouble(latitudeString);
                double longitudeValue = Double.parseDouble(longitudeString);

                MarketModel marketModel = new MarketModel();
                marketModel.setMarketAddress(marketAddress);
                marketModel.setMarketName(marketName);
                marketModel.setLatitude(latitudeValue);
                marketModel.setLongitude(longitudeValue);

                // Salve os dados no Firebase
                String marketId = databaseReference.push().getKey();
                databaseReference.child(marketId).setValue(marketModel);

                // Exiba a mensagem de sucesso
                Toast.makeText(MarketDetailActivity.this, "Dados gravados com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("latitude") && data.hasExtra("longitude")) {
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);

                // Exiba a latitude e longitude na tela
                textViewLatitude.setText(String.valueOf(latitude));
                textViewLongitude.setText(String.valueOf(longitude));
            }
        }
    }
}
