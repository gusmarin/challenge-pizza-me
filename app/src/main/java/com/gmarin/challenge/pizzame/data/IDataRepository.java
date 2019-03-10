package com.gmarin.challenge.pizzame.data;

public interface IDataRepository {
    void getNearestPlaces(String latitude, String longitude, String term, IDataCallback callback);
}
