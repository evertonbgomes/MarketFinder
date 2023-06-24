package com.example.marketfinder.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.marketfinder.entities.Market;

import java.util.List;

@Dao
public interface MarketDao {
    @Query("SELECT * FROM markets")
    List<Market> getAllMarkets();

    @Query("SELECT * FROM markets WHERE id = :marketId")
    Market getMarketById(int marketId);

    @Insert
    void insertMarket(Market market);

    @Update
    void updateMarket(Market market);

    @Delete
    void deleteMarket(Market market);
}

