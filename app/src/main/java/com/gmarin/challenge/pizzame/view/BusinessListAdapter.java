package com.gmarin.challenge.pizzame.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmarin.challenge.pizzame.R;
import com.gmarin.challenge.pizzame.data.model.Business;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Business> mDataSet;
    private Context mContext;

    public BusinessListAdapter(Context context) {
        mContext = context;
    }

    public void setDataSet(List<Business> businesses) {
        mDataSet = businesses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_view_item, parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BusinessViewHolder viewHolder = (BusinessViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO start detailed activity
            }
        });
        Business business = mDataSet.get(position);
        viewHolder.getNameView().setText(position+1 + "." + business.getName());

        String distance = (new DecimalFormat("##.##").format(business.getDistance()));
        viewHolder.getDistanceView().setText(distance + " mi");
        // move to another location
        StringBuilder sb = new StringBuilder();
        for (String address : business.getLocation().getDisplay_address()) {
            sb.append(address);
            sb.append(" ");
        }
        viewHolder.getAddressView().setText(sb.toString().trim());
        viewHolder.getPhoneView().setText(business.getDisplay_phone());
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView distanceView;
        private final TextView addressView;

        private final TextView phoneView;

        public BusinessViewHolder(@NonNull View itemView) {
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
