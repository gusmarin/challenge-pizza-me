package com.gmarin.challenge.pizzame.data;

import com.gmarin.challenge.pizzame.data.network.yelp.YelpRepository;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;
import com.gmarin.challenge.pizzame.data.network.yelp.model.Businesses;
import com.gmarin.challenge.pizzame.viewmodel.Place;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class DataRepositoryTest {

    @Mock
    YelpRepository yelpRepository;

    @Captor
    ArgumentCaptor<IDataCallback> iCallbackArgumentCaptor;

    @Mock
    IDataCallback callback;

    IDataRepository dataRepository;
    Business testBusiness;

    @Before
    public void initSetup() {
        MockitoAnnotations.initMocks(this);
        dataRepository = DataRepository.getInstance(yelpRepository);
        testBusiness = new Business();
        Business.Location mockLocation = new Business.Location();
        mockLocation.setDisplay_address(new ArrayList<String>());
        testBusiness.setLocation(mockLocation);
    }

    @Test
    public void testOnFailureError() {
        dataRepository.getNearestPlaces("", "", "", callback);
        verify(yelpRepository).getNearestPlaces(anyString(), anyString(), anyString(), iCallbackArgumentCaptor.capture());
        iCallbackArgumentCaptor.getValue().onFailure("ERROR!");
        verify(callback).onFailure("ERROR!");
    }

    @Test
    public void testNullList() {
        dataRepository.getNearestPlaces("", "", "", callback);
        verify(yelpRepository).getNearestPlaces(anyString(), anyString(), anyString(), iCallbackArgumentCaptor.capture());
        iCallbackArgumentCaptor.getValue().onSuccess(null);
        verify(callback).onFailure("No places found");
    }

    @Test
    public void testEmptyList() {
        dataRepository.getNearestPlaces("", "", "", callback);
        verify(yelpRepository).getNearestPlaces(anyString(), anyString(), anyString(), iCallbackArgumentCaptor.capture());
        Businesses businesses = new Businesses();
        businesses.setBusinesses(new ArrayList<Business>());
        iCallbackArgumentCaptor.getValue().onSuccess(businesses);
        verify(callback).onFailure("No places found");
    }

    @Test
    public void testValidList() {
        dataRepository.getNearestPlaces("", "", "", callback);
        verify(yelpRepository).getNearestPlaces(anyString(), anyString(), anyString(), iCallbackArgumentCaptor.capture());

        Business business = new Business();
        business.setName("Test");

        List<Business> testList = new ArrayList<>();
        testList.add(business);
        Businesses businesses = new Businesses();
        businesses.setBusinesses(testList);
        iCallbackArgumentCaptor.getValue().onSuccess(businesses);

        verify(callback).onSuccess(any());
    }
}
