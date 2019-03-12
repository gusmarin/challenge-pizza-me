package com.gmarin.challenge.pizzame.viewmodel;

import android.app.Application;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class PlaceViewModelTest {

    @Rule
    public InstantTaskExecutorRule mRule = new InstantTaskExecutorRule();

    PlaceViewModel placeViewModel;

    @Mock
    LifecycleOwner lifecycleOwner;
    LifecycleRegistry lifecycleRegistry;

    @Before
    public void initSetup() {
        MockitoAnnotations.initMocks(this);
        placeViewModel = new PlaceViewModel((Application)ApplicationProvider.getApplicationContext());

        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
    }

    @Test
    public void testRemoveAddObserver() {
        Observer<PlaceViewModel.PlaceWrapper> observer = Mockito.mock(Observer.class);
        MutableLiveData<PlaceViewModel.PlaceWrapper> data = placeViewModel.getNearestBusinesses();
        data.observeForever(observer);

        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasObservers(), is(true));
        assertThat(data.hasActiveObservers(), is(true));

        data.removeObserver(observer);
        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasObservers(), is(false));
        assertThat(data.hasActiveObservers(), is(false));
    }

    @Test
    public void testLifecycleActiveObserver() {
        Observer<PlaceViewModel.PlaceWrapper> observer = Mockito.mock(Observer.class);
        MutableLiveData<PlaceViewModel.PlaceWrapper> data = placeViewModel.getNearestBusinesses();
        data.observe(lifecycleOwner, observer);

        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasActiveObservers(), is(false));

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasActiveObservers(), is(true));

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasActiveObservers(), is(false));
    }

    @Test
    public void testLiveDataChange() {
        Observer<PlaceViewModel.PlaceWrapper> observer = Mockito.mock(Observer.class);
        MutableLiveData<PlaceViewModel.PlaceWrapper> data = placeViewModel.getNearestBusinesses();
        data.observeForever(observer);

        verify(observer, never()).onChanged(any(PlaceViewModel.PlaceWrapper.class));
        assertThat(data.hasActiveObservers(), is(true));

        data.setValue(new PlaceViewModel.PlaceWrapper(null, "Failed to load places"));

        verify(observer).onChanged(any(PlaceViewModel.PlaceWrapper.class));
    }
}
