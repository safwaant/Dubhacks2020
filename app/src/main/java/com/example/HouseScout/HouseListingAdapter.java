package com.example.HouseScout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class HouseListingAdapter extends RecyclerView.Adapter<HouseListingAdapter.ViewHolder>{

    private static final String LOG_TAG = HouseListingAdapter.class.getSimpleName();
    private List<House> houses;
    private Context context;

    public HouseListingAdapter(List<House> houseList,Context context){
        this.houses = houseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.house_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       House house = houses.get(position);
       holder.price.setText(house.price);
       Picasso.get().load(house.getUrl()).into(holder.houseImage);
    }


    @Override
    public int getItemCount() {
        return houses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView houseImage;
        TextView price, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            houseImage = itemView.findViewById(R.id.houseImage);
            price = itemView.findViewById(R.id.Price);
            location = itemView.findViewById(R.id.location);

            houseImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
