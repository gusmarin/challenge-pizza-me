package com.gmarin.challenge.pizzame.data;

public interface IDataCallback<T> {
    void onSuccess(T places);
    void onFailure(String error);
}
