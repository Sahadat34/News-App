package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
NewsHeadlines newsHeadlines;
TextView title,author,time,detail,content;
ImageView img;
WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        newsHeadlines = (NewsHeadlines) getIntent().getSerializableExtra("data");

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsHeadlines.getUrl());
        webView.getSettings().setJavaScriptEnabled(true);

    }
    public void myLayout(){
        title = findViewById(R.id.details_title);
        author = findViewById(R.id.text_details_author);
        time = findViewById(R.id.text_details_time);
        detail = findViewById(R.id.tetx_details_details);
        content = findViewById(R.id.tetx_details_content);
        img = findViewById(R.id.image_details_news);

        title.setText(newsHeadlines.getTitle());
        author.setText(newsHeadlines.getAuthor());
        time.setText(newsHeadlines.getPublishedAt());
        detail.setText(newsHeadlines.getDescription());
        content.setText(newsHeadlines.getContent());
        if (newsHeadlines.getUrlToImage() != null){
            Picasso.get().load(newsHeadlines.getUrlToImage()).into(img);
        }
    }
}