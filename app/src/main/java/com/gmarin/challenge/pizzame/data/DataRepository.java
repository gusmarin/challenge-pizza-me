package com.gmarin.challenge.pizzame.data;

import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Businesses;
import com.gmarin.challenge.pizzame.viewmodel.Place;

import java.util.ArrayList;
import java.util.List;

public class DataRepository implements IDataRepository {

    private final IDataImpl mDataRepo;
    private static DataRepository mRepository;

    private DataRepository(IDataImpl dataImpl) {
        mDataRepo = dataImpl;
    }

    @Override
    public void getNearestPlaces(String latitude, String longitude, String term, final IDataCallback callback) {
        mDataRepo.getNearestPlaces(latitude, longitude, term, new IDataCallback<Businesses>() {
            @Override
            public void onSuccess(Businesses businesses) {
                List<Place> placeList = new ArrayList<>();
                for (Business business: businesses.getBusinesses()) {
                    placeList.add(fromBusiness(business));
                }

            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }

    // TODO this needs to be done in a generic translator
    private Place fromBusiness(Business business) {
        Place.PlaceBuilder builder = new Place.PlaceBuilder();

        builder.setName(business.getName());
        String address =  "";
        for (String a : business.getLocation().getDisplay_address()) {
            address += a;
        }
        builder.setAddress(address);
        builder.setDistance(business.getDistance());
        builder.setImageUrl(business.getImage_url());
        builder.setPhoneNumber(business.getDisplay_phone());
        builder.setRating((float) business.getRating());
        builder.setReviewCount(business.getReview_count());

        return builder.build();
    }

    public static synchronized IDataRepository getInstance(IDataImpl dataImpl) {
        if  (mRepository == null) {
            mRepository = new DataRepository(dataImpl);
        }
        return mRepository;
    }
}
