package com.molor.monggolapor.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.molor.monggolapor.NewDetailActivity;
import com.molor.monggolapor.R;
import com.molor.monggolapor.Utils;
import com.molor.monggolapor.adapter.NewsAdapter;
import com.molor.monggolapor.api.ApiClient;
import com.molor.monggolapor.api.ApiInterface;
import com.molor.monggolapor.model.Article;
import com.molor.monggolapor.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BeritaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "b12fdd1d48e9481a9708a8037f0df6a5";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsAdapter adapter;
    private Context mContext;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btnRetry;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_berita, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        recyclerView = v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorNav);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        errorLayout = v.findViewById(R.id.errorLayout);
        errorImage = v.findViewById(R.id.errorImage);
        errorMessage = v.findViewById(R.id.errorMessage);
        errorTitle = v.findViewById(R.id.errorTitle);
        btnRetry = v.findViewById(R.id.btnRetry);

        LoadJson("");
        setHasOptionsMenu(true);
        return v;
    }

    public void initListener() {
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(mContext, NewDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img", article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());

//                Pair<View, String> pair= Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        pair
//                );
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
//                    startActivity(intent, optionsCompat.toBundle());
//                }else{
                startActivity(intent);
                //}
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflaterr) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(mContext.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItem aboutMenuItem = menu.findItem(R.id.action_about);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Search news..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    onLoadingSwipeRefresh(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);
        super.onCreateOptionsMenu(menu, inflaterr);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about){
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadJson(final String keyword) {
        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiclient().create(ApiInterface.class);

        String country = Utils.getCountry();

        Call<News> call;

        if (keyword.length() > 0) {
            call = apiInterface.getNewsSearch(keyword, "id", "publishedAt", API_KEY);
        } else {
//            call = apiInterface.getNews("id", "health", API_KEY);
            call = apiInterface.getNews("kemendikbud", "id", "publishedAt", API_KEY);

        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    adapter = new NewsAdapter(articles, getActivity());
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    initListener();
                } else {
                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;

                    }
                    swipeRefreshLayout.setRefreshing(false);
                    showErrorMessage(R.drawable.no_result, "No Result", "Mohon coba lagi\n" + errorCode);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(R.drawable.no_result, "Oops..", "Mohon coba lagi\n" + t.toString());
            }
        });
    }

    private void showErrorMessage(int imageView, String title, String message) {
        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh("");
            }
        });
    }


    @Override
    public void onRefresh() {
        LoadJson("");
    }

    private void onLoadingSwipeRefresh(final String keyword){
        swipeRefreshLayout.post(
                new Runnable(){
                    @Override
                    public void run(){
                        LoadJson(keyword);
                    }
                }
        );
    }
}