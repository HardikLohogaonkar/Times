package com.heady.times;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.heady.times.adapter.NewsAdapter;
import com.heady.times.model.News;
import com.heady.times.utils.ServiceUrl;
import com.heady.times.utils.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<News.ResultsBean> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshNews);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        onSwipe();
        loadNews();

        mNewsAdapter = new NewsAdapter(this, mNewsList);
        mRecyclerView.setAdapter(mNewsAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void onSwipe() {

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadNews();
            }
        });
    }

    private void loadNews() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ServiceUrl.getNews, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                mSwipeRefreshLayout.setRefreshing(false);
                News news = new Gson().fromJson(response.toString(),News.class);
                mNewsList.clear();
                mNewsList.addAll(news.getResults());
                mNewsAdapter.notifyDataSetChanged();

                Log.d("hardik: ", "" + response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {

            onBack();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack() {

        finish();
    }

    @Override
    public void onRefresh() {

        loadNews();
    }
}
