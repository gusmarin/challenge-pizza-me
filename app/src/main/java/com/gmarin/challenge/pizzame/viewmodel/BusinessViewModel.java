package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;

import java.util.List;

public class BusinessViewModel extends AndroidViewModel {

    private MutableLiveData<List<Business>> mObservableBusinesses = new MutableLiveData<>();

    private YelpRepository mYelpRepository;

    public BusinessViewModel (@NonNull Application application) {
        super(application);
        mYelpRepository = new YelpRepository();
    }

    public void searchNearestBusinessesFrom(String latitude, String longitude) {
        mYelpRepository.searchNearestBusinesses(mObservableBusinesses, latitude, longitude);
    }

    public MutableLiveData<List<Business>> getNearestBusinesses() {
        return mObservableBusinesses;
    }
}
