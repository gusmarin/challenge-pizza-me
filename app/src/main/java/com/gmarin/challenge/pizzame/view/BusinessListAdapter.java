package com.gmarin.challenge.pizzame.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.viewmodel.Place;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.gmarin.challenge.pizzame.view.DetailActivity.*;

public class BusinessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Place> mDataSet;
    private Context mContext;

    public BusinessListAdapter(Context context) {
        mContext = context;
    }

    public void setDataSet(List<Place> places) {
        mDataSet = places;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_view_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceViewHolder viewHolder = (PlaceViewHolder) holder;
        final Place place = mDataSet.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_NAME, place.getName());
                bundle.putString(EXTRA_ADDRESS, place.getAddress());
                bundle.putString(EXTRA_IMAGE_URL, place.getImageUrl());
                bundle.putFloat(EXTRA_RATING, place.getRating());
                bundle.putInt(EXTRA_REVIEW_COUNT, place.getReviewCount());
                bundle.putString(EXTRA_PHONE_NUMBER, place.getPhoneNumber());
                bundle.putDouble(EXTRA_DISTANCE, place.getDistance());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        viewHolder.getNameView().setText(position+1 + "." + place.getName());

        String distance = (new DecimalFormat("##.##").format(place.getDistance()));
        viewHolder.getDistanceView().setText(distance + " mi");

        viewHolder.getAddressView().setText(place.getAddress());
        viewHolder.getPhoneView().setText(place.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView distanceView;
        private final TextView addressView;

        private final TextView phoneView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.business_name_text_view);
            distanceView = itemView.findViewById(R.id.business_distance_text_view);
            addressView = itemView.findViewById(R.id.business_address_text_view);
            phoneView = itemView.findViewById(R.id.business_phone_text_view);
        }

        public TextView getDistanceView() {
            return distanceView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getAddressView() {
            return addressView;
        }

        public TextView getPhoneView() {
            return phoneView;
        }
    }
}
