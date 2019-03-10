package com.gmarin.challenge.pizzame.viewmodel;

import com.gmarin.challenge.pizzame.data.DataRepository;
import com.gmarin.challenge.pizzame.data.IDataCallback;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlaceViewModelTest {
    @Mock
    DataRepository repository;

    @Captor
    ArgumentCaptor<IDataCallback> callbackArgumentCaptor;

    public void setupViewModel() {
        MockitoAnnotations.initMocks(this);


    }


}
