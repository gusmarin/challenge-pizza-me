package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;

import java.util.List;

public class BusinessViewModel extends AndroidViewModel {

    private MutableLiveData<List<Business>> mObservableBusinesses;

    private YelpRepository mYelpRepository;

    public BusinessViewModel (@NonNull Application application, YelpRepository repository) {
        super(application);
        mYelpRepository = repository;
    }

    public void searchNearestBusinessesFromCoordinates(String latitude, String longitude) {
        mObservableBusinesses = mYelpRepository.searchNearestBusinesses(latitude, longitude);
    }

    public MutableLiveData<List<Business>> getNearestBusinesses() {
        return mObservableBusinesses;
    }
}
