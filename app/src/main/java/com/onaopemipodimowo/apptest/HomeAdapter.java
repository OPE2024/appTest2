package com.onaopemipodimowo.apptest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    Context context;
    List<Home> homes;

    public HomeAdapter(Context context, List<Home>homes){
        this.context = context;
        this.homes = homes;
    }

    // inflates a layout from xml and returns the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Log.d("HomeAdapter","onCreativeViewHolder");
        View homeView = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        return new ViewHolder(homeView);
    }

    //populates data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        Log.d("HomeAdapter","onBindViewHolder" + position);
        // get home at the passed in position
        Home home = homes.get(position);
        // bind the home data into the VH
        holder.bind(home);
    }

    @Override
    public int getItemCount(){return homes.size();}
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle, tvCity, tvStateCode, tvPropertyType, tvLocation, tvBathsMin, tvBathsMax, tvBedsMin, tvBedsMax;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvStateCode = itemView.findViewById(R.id.tvStateCode);
            tvPropertyType = itemView.findViewById(R.id.tvPropertyType);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvBathsMin = itemView.findViewById(R.id.tvBathsMin);
            tvBathsMax = itemView.findViewById(R.id.tvBathsMax);
            tvBedsMin = itemView.findViewById(R.id.tvBedsMin);
            tvBedsMax = itemView.findViewById(R.id.tvBedsMax);

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        public void bind(Home home){
            tvTitle.setText(home.getName());
            tvCity.setText(home.getCity());
            tvStateCode.setText(home.getState_code());
            tvPropertyType.setText(home.getProperty_type());
            tvLocation.setText(home.getLine());
            tvBathsMin.setText(String.valueOf(home.getBaths_min()));
            tvBathsMax.setText(String.valueOf(home.getBaths_max()));
            tvBedsMin.setText(String.valueOf(home.getBeds_min()));
            tvBedsMax.setText(String.valueOf(home.getBeds_max()));
        }

        @Override
        public void onClick(View v){
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Home home = homes.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, HomeDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Home.class.getSimpleName(), Parcels.wrap(home));
                // show the activity
                context.startActivity(intent);

            }
        }


    }

}
