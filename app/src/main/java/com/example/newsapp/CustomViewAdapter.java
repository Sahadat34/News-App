package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomViewAdapter extends RecyclerView.Adapter<CustomViewAdapter.Myholder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    SelectListener onclick;

    public CustomViewAdapter(Context context, List<NewsHeadlines> headlines,SelectListener onclick) {
        this.context = context;
        this.headlines = headlines;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.headline_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        holder.headlines_title.setText(headlines.get(position).getTitle());
        holder.newsSource.setText(headlines.get(position).getSource().getName());
        String imageUrl = headlines.get(position).getUrlToImage();
        if (imageUrl != null){
            Picasso.get().load(imageUrl).into(holder.headlinesImage);
        }
        holder.headlineContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.OnNewsClicked(headlines.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        TextView headlines_title,newsSource;
        ImageView headlinesImage;
        CardView headlineContainer;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            headlines_title = itemView.findViewById(R.id.headlines);
            newsSource = itemView.findViewById(R.id.news_source);
            headlinesImage = itemView.findViewById(R.id.headline_image);
            headlineContainer = itemView.findViewById(R.id.headline_container);
        }
    }
}
