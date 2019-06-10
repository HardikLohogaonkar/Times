package com.heady.times.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heady.times.R;
import com.heady.times.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context mContext;
    List<News.ResultsBean> mNewsList;

    public NewsAdapter(Context context, List<News.ResultsBean> mNewsList) {
        this.mContext = context;
        this.mNewsList = mNewsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_activity, viewGroup, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        final News.ResultsBean news = mNewsList.get(position);

        holder.mTxtTitle.setText(news.getTitle());
        holder.mTxtBy.setText(news.getByline());
        holder.mTxtDesc.setText(news.getAbstractX());
        Picasso.with(mContext).load(news.getMultimedia().get(4).getUrl()).into(holder.mImgArticle);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgArticle;
        TextView mTxtTitle, mTxtBy, mTxtDesc;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImgArticle = itemView.findViewById(R.id.imgNews);
            mTxtTitle = itemView.findViewById(R.id.txtTitle);
            mTxtBy = itemView.findViewById(R.id.txtBy);
            mTxtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}
