package com.example.internetnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    String API_KEY = "c751b470209e47b89f7a5d5eba820df8";

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);

        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, this, this::onCategoryClick);

        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("All", "https://images.unsplash.com/photo-1529243856184-fd5465488984?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzV8fG5ld3N8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://images.unsplash.com/photo-1518152006812-edab29b069ac?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fHNjaWVuY2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports", "https://images.unsplash.com/photo-1517649763962-0c623066013b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c3BvcnRzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General", "https://images.unsplash.com/reserve/uZYSV4nuQeyq64azfVIn_15130980706_64134efc6e_o.jpg?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTd8fG5ld3N8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://images.unsplash.com/photo-1476480862126-209bfaa8edc8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String Category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + Category + "&apiKey=c751b470209e47b89f7a5d5eba820df8";
        System.out.println(categoryURL);
        String URL = "https://newsapi.org/v2/top-headlines?country=in&sortBy=publishedAt&language=en&apiKey=c751b470209e47b89f7a5d5eba820df8";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;

        if (Category.equals("All")) {
            call = retrofitAPI.getAllNews(URL);
        } else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();

                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),
                            articles.get(i).getContent())
                    );
                    newsRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail To Get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
       String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}