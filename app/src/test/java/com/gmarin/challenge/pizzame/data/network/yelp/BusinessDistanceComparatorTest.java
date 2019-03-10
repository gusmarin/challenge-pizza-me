package com.gmarin.challenge.pizzame.data.network.yelp;

import com.gmarin.challenge.pizzame.data.network.yelp.model.Business;

import com.gmarin.challenge.pizzame.data.network.yelp.model.BusinessDistanceComparator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BusinessDistanceComparatorTest {

    @Test
    public void testAIsGreaterThanB() {
        Business a = new Business();
        Business b = new Business();

        a.setDistance(1);
        b.setDistance(0.1);

        Business[] array = {a, b};
        Arrays.sort(array, new BusinessDistanceComparator());

        Assert.assertEquals(b.getDistance(), array[0].getDistance(), 0.0f);
    }

    @Test
    public void testBIsEqualToA() {
        Business a = new Business();
        Business b = new Business();

        a.setDistance(1);
        b.setDistance(1);

        Business[] array = {a, b};
        Arrays.sort(array, new BusinessDistanceComparator());

        Assert.assertEquals(a.getDistance(), array[0].getDistance(), 0.0f);
    }
}
