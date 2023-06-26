package com.example.marketfinder.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marketfinder.R;
import com.example.marketfinder.model.MarketModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarketListAdapter extends AppCompatActivity {

    private ListView listViewMarkets;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list_adapter);

        listViewMarkets = findViewById(R.id.listView_markets);

        // Inicialize o FirebaseDatabase
        databaseReference = FirebaseDatabase.getInstance().getReference("markets");

        // Consulte o banco de dados Firebase para obter os mercados
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> marketList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MarketModel market = snapshot.getValue(MarketModel.class);
                    if (market != null) {
                        String marketName = market.getMarketName();
                        String marketAddress = market.getMarketAddress();
                        String marketInfo = marketName + " - " + marketAddress;
                        marketList.add(marketInfo);
                    }
                }

                // Crie um adaptador para exibir a lista de mercados no ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MarketListAdapter.this, android.R.layout.simple_list_item_1, marketList);
                listViewMarkets.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate o erro de consulta ao banco de dados Firebase
            }
        });
    }
}