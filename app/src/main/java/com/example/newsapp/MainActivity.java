package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newsapp.models.NewsApiResponse;
import com.example.newsapp.models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener{
    RecyclerView recyclerView;
    CustomViewAdapter adapter;
    ProgressDialog progressDialog;
    Button sport,business,health,entertainment,general,science,technology;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching news articles...");
        progressDialog.show();

        searchView = findViewById(R.id.serach);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Searching for "+query+"...");
                progressDialog.show();
                
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener,"general",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        sport = findViewById(R.id.sport);
        sport.setOnClickListener(this);

        business = findViewById(R.id.business);
        business.setOnClickListener(this);

        health = findViewById(R.id.health);
        health.setOnClickListener(this);

        entertainment = findViewById(R.id.entertainment);
        entertainment.setOnClickListener(this);

        general = findViewById(R.id.general);
        general.setOnClickListener(this);

        science = findViewById(R.id.science);
        science.setOnClickListener(this);

        technology = findViewById(R.id.technology);
        technology.setOnClickListener(this);


        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getNewsHeadLines(listener,"general",null);
    }
    private final OnFetchDataListener<NewsApiResponse>  listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(MainActivity.this, "Result not found", Toast.LENGTH_SHORT).show();
            }else{
                show(list);
                progressDialog.dismiss();
            }
            
        }

        @Override
        public void onError(String error) {
            Toast.makeText(MainActivity.this, "An error occured!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void show(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_container);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomViewAdapter(this,list,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void OnNewsClicked(NewsHeadlines newsHeadlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
        .putExtra("data",newsHeadlines));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        progressDialog.setTitle("Fetching articles of "+category);
        progressDialog.show();
        RequestManager manager = new RequestManager(MainActivity.this);
        manager.getNewsHeadLines(listener,category,null);

    }
}