package com.kh618.soleektask.Module;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh618.soleektask.Home.HomeActivity;
import com.kh618.soleektask.R;

public class CharAdapter extends RecyclerView.Adapter<CharAdapter.CharHolder> {
    Context context;
    char[] items;

    public CharAdapter(Context context, char[] items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CharHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.char_view, viewGroup, false);
        return new CharHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CharHolder charHolder, int i) {
        final char item = items[i];
        charHolder.charTv.setText(String.valueOf(item));
        charHolder.charTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HomeActivity.class);
                i.putExtra("char", item);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class CharHolder extends RecyclerView.ViewHolder {
        TextView charTv;
        public CharHolder(@NonNull View itemView) {
            super(itemView);
            charTv = itemView.findViewById(R.id.tv_char);

        }
    }
}
