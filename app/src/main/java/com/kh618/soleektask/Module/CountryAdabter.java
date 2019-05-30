package com.kh618.soleektask.Module;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh618.soleektask.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CountryAdabter extends RecyclerView.Adapter<CountryAdabter.ViewHolder> {

    private Context context;
    private ArrayList<CountryItem> items;

    public CountryAdabter(Context context, ArrayList<CountryItem> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CountryItem item = items.get(i);

        Picasso.get().load(Uri.parse(item.getCountryUrlFlag())).into(viewHolder.flagImage);
        viewHolder.countryCapital.setText("Capital :" + item.getCounterCapital());
        viewHolder.countryName.setText(item.getCounterName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView flagImage;
        TextView countryName, countryCapital;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImage = itemView.findViewById(R.id.flag_image);
            countryName = itemView.findViewById(R.id.country_name);
            countryCapital = itemView.findViewById(R.id.capital);

        }
    }
}
