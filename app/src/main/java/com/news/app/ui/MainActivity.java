package com.news.app.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news.app.R;
import com.news.app.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();

        RecyclerView recyclerView = binding.mainContent.newsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter adapter = new NewsAdapter(this);
        recyclerView.setAdapter(adapter);
        binding.mainContent.progressBar.setVisibility(View.VISIBLE);

        viewModel.loading.observe(this, loading -> {
            if (loading) {
                binding.mainContent.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.mainContent.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.newsData.observe(this, articles -> {
            Log.d(TAG, "setNews: "+articles.articles.toString());
            if (articles.articles != null) {
                binding.mainContent.progressBar.setVisibility(View.GONE);
                adapter.setNewsList(articles.articles);
            }
        });
        viewModel.getNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.search(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("NEWS_URL", url);
        startActivity(intent);
    }
}