package com.news.app.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news.app.R;
import com.news.app.domain.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> newsList = new ArrayList<>();
    private OnClickListener onClickListener;

    NewsAdapter(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public void setNewsList(List<Article> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article news = newsList.get(position);
        holder.bind(news, onClickListener);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView publishedAt;
        private final ImageView image;
        private final Button btnNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_tile);
            publishedAt = itemView.findViewById(R.id.news_date);
            image = itemView.findViewById(R.id.news_image);
            btnNews = itemView.findViewById(R.id.read_news);
        }

        public void bind(Article news, OnClickListener onClickListener) {
            title.setText(news.getTitle());
            publishedAt.setText(news.getPublishedAt());
            Picasso.get().load(news.urlToImage).fit().into(image);
            btnNews.setOnClickListener(v -> {
                if (news.getUrl() != null) {
                    onClickListener.onClick(news.getUrl());
                }
            });
        }
    }
}
