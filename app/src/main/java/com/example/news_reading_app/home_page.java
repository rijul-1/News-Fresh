package com.example.news_reading_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home_page extends AppCompatActivity implements NewsItemClicked {

    News_List_Adapter adapter;
    ProgressBar progressBar;
    TextView progress_bar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        progressBar = findViewById(R.id.progressBar2);
        progress_bar_text = findViewById(R.id.progress_bar_text);
        progressBar.setVisibility(View.VISIBLE);
        progress_bar_text.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<news> news_array_list = new ArrayList<news>();
        news_array_list = fetchdata();
        adapter = new News_List_Adapter(this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<news> fetchdata() {
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        final ArrayList<news> newsArray = new ArrayList<news>();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    JSONArray newsjsonarray;
                    JSONObject newsjsonobject;
                    String title, author, url, urltoimage;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            newsjsonarray = response.getJSONArray("articles");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < newsjsonarray.length(); i++) {
                            try {
                                newsjsonobject = newsjsonarray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                title = newsjsonobject.getString("title");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                author = newsjsonobject.getString("author");
                                if(author.equals("null") || author.equals("")){
                                    author = "Unknown";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                url = newsjsonobject.getString("url");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                urltoimage = newsjsonobject.getString("urlToImage");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            news single_news = new news();
                            single_news.setNews_author(author);
                            single_news.setNews_imageurl(urltoimage);
                            single_news.setNews_url(url);
                            single_news.setNews_title(title);
                            newsArray.add(single_news);
                        }
                        adapter.update(newsArray);
                    }
                },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                        });
        requestQueue.add(jsonObjectRequest);
        progressBar.setVisibility(View.GONE);
        progress_bar_text.setVisibility(View.GONE);
        return newsArray;
    }
    public void onItemClicked(news item) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.getNews_url()));
    }
}