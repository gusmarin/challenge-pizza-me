package com.gmarin.challenge.pizzame.data;

public interface IDataImpl {
    void getNearestPlaces(String latitude, String longitude, String term, ICallbackImpl callback);
}
