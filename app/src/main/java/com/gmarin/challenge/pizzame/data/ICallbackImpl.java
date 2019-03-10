package com.gmarin.challenge.pizzame.data;

public interface ICallbackImpl<T> {
    void onSuccess(T places);
    void onFailure(String error);
}
