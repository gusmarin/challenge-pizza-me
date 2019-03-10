package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.gmarin.challenge.pizzame.data.DataRepository;
import com.gmarin.challenge.pizzame.data.IDataCallback;
import com.gmarin.challenge.pizzame.data.IDataRepository;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {

    private MutableLiveData<List<Place>> mObservableBusinesses = new MutableLiveData<>();

    private IDataRepository mDataRepository;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(new YelpRepository());
    }

    public void searchNearestBusinessesFrom(String latitude, String longitude, String term) {
        mDataRepository.getNearestPlaces(latitude, longitude, term, new IDataCallback<List<Place>>() {
            @Override
            public void onSuccess(List<Place> places) {
                mObservableBusinesses.setValue(places);
            }

            @Override
            public void onFailure(String error) {
                // TODO handle UI failures later
            }
        });
    }

    public MutableLiveData<List<Place>> getNearestBusinesses() {
        return mObservableBusinesses;
    }
}
