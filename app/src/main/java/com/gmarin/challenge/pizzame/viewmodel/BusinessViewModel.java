package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.gmarin.challenge.pizzame.data.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;

import java.util.List;

public class BusinessViewModel extends AndroidViewModel {

    private MutableLiveData<List<Business>> mObservableBusinesses;

    private YelpRepository mYelpRepository;

    public BusinessViewModel (@NonNull Application application) {
        super(application);
        mYelpRepository = new YelpRepository();
    }

    public void searchNearestBusinessesFrom(String latitude, String longitude) {
        mObservableBusinesses = mYelpRepository.searchNearestBusinesses(latitude, longitude);
    }

    public MutableLiveData<List<Business>> getNearestBusinesses() {
        if (mObservableBusinesses == null) {
            return new MutableLiveData<>();
        }
        return mObservableBusinesses;
    }
}
