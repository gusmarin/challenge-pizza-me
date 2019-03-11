package com.gmarin.challenge.pizzame.view;

import android.content.Intent;
import android.os.Bundle;

import com.gmarin.challenge.pizzame.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;

import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_ADDRESS;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_DISTANCE;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_IMAGE_URL;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_NAME;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_PHONE_NUMBER;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_RATING;
import static com.gmarin.challenge.pizzame.view.DetailActivity.EXTRA_REVIEW_COUNT;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mRule  = new  ActivityTestRule<>(DetailActivity.class);

    @Before
    public void setupPlaceIntent() {

        Intent intent = new Intent(mRule.getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NAME, "Test Place");
        bundle.putString(EXTRA_ADDRESS, "Somewhere");
        bundle.putString(EXTRA_IMAGE_URL, "");
        bundle.putFloat(EXTRA_RATING, 3.0f);
        bundle.putInt(EXTRA_REVIEW_COUNT, 1);
        bundle.putString(EXTRA_PHONE_NUMBER, "(000) 000-0000");
        bundle.putDouble(EXTRA_DISTANCE, 1.0);
        intent.putExtras(bundle);

        mRule.launchActivity(intent);
        Intents.init();
    }

    @After
    public void clean() {
        Intents.release();
    }

    @Test
    public void phoneViewOpensDialerTest() {
        onView(withId(R.id.business_phone_text_view)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW)));
    }

    @Test
    public void mapViewOpensMapsTest() {
        onView(withId(R.id.floating_map_button)).perform(click());
        intended(allOf(hasAction(Intent.ACTION_VIEW), toPackage("com.google.android.apps.maps")));
    }
}
