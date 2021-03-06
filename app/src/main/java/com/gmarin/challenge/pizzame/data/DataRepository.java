package com.gmarin.challenge.pizzame.data;

import com.gmarin.challenge.pizzame.data.client.ICallbackImpl;
import com.gmarin.challenge.pizzame.data.client.IDataImpl;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Businesses;
import com.gmarin.challenge.pizzame.data.entity.Place;

import java.util.ArrayList;
import java.util.List;

public class DataRepository implements IDataRepository {

    private IDataImpl mDataRepo;

    public DataRepository(IDataImpl dataImpl) {
        mDataRepo = dataImpl;
    }

    @Override
    public void getNearestPlaces(String latitude, String longitude, String term, final IDataCallback callback) {
        mDataRepo.getNearestPlaces(latitude, longitude, term, new ICallbackImpl<Businesses>() {
            @Override
            public void onSuccess(Businesses businesses) {
                List<Place> placeList = new ArrayList<>();
                if (businesses == null || businesses.getBusinesses().isEmpty()) {
                    callback.onFailure("No places found");
                    return;
                }
                for (Business business: businesses.getBusinesses()) {
                    placeList.add(fromBusiness(business));
                }
                callback.onSuccess(placeList);
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
}
