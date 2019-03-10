package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.gmarin.challenge.pizzame.data.DataRepository;
import com.gmarin.challenge.pizzame.data.IDataCallback;
import com.gmarin.challenge.pizzame.data.IDataRepository;
import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;

import java.util.List;

public class PlaceViewModel extends AndroidViewModel {

    private MutableLiveData<PlaceWrapper> mObservableBusinesses = new MutableLiveData<>();

    private IDataRepository mDataRepository;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(new YelpRepository());
    }

    public void searchNearestBusinessesFrom(String latitude, String longitude, String term) {
        mDataRepository.getNearestPlaces(latitude, longitude, term, new IDataCallback<List<Place>>() {
            @Override
            public void onSuccess(List<Place> places) {
                mObservableBusinesses.setValue(new PlaceWrapper(places, null));
            }

            @Override
            public void onFailure(String error) {
                mObservableBusinesses.setValue(new PlaceWrapper(null, error));
            }
        });
    }

    public MutableLiveData<PlaceWrapper> getNearestBusinesses() {
        return mObservableBusinesses;
    }

    public static class PlaceWrapper {
        private final List<Place> places;

        private final String error;

        public PlaceWrapper(List<Place> places, String error) {
            this.places = places;
            this.error = error;
        }

        public List<Place> getPlaces() {
            return places;
        }

        public String getError() {
            return error;
        }
    }
}
