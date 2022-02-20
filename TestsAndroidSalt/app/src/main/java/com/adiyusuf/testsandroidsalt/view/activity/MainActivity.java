package com.adiyusuf.testsandroidsalt.view.activity;

import static com.adiyusuf.testsandroidsalt.model.api.EndPoint.API_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adiyusuf.testsandroidsalt.R;
import com.adiyusuf.testsandroidsalt.model.pojo.ArticlesItem;
import com.adiyusuf.testsandroidsalt.view.adapter.ListRecyclerViewAdapter;
import com.adiyusuf.testsandroidsalt.view.adapter.base.PaginationRecyclerViewListener;
import com.adiyusuf.testsandroidsalt.view.utils.ConnectionUtils;
import com.adiyusuf.testsandroidsalt.viewmodel.ListViewModel;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;

    private LinearLayoutManager layoutManager;
    private ListRecyclerViewAdapter adapter;

    private int page = 1;

    private static boolean isCalled = false;
    private static boolean isLoading = false;
    private static boolean isLastData = false;

    private ListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListViewModel.class);
        initViewId();
        initRecyclerView();
        initApi();
        initListener();
    }

    private void initViewId() {
        Log.i(TAG, "init view");
        recyclerView = findViewById(R.id.recycler_view);
        refresh = findViewById(R.id.swipe_refresh);
    }

    private void initRecyclerView() {
        Log.i(TAG, "recycler view");
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(1000);
        recyclerView.setDrawingCacheEnabled(true);

        adapter = new ListRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void initApi() {
        Log.i(TAG, "init api");
        initCall();
        callApi(false);
        observableMvvm();
    }

    private void initCall() {
        //setup progressbar
    }

    private void callApi(boolean isLoadMore) {
        if (!isCalled) {
            Log.i(TAG, "callApi: " + isLoadMore);
            Map<String, String> param = new TreeMap<>();
            param.put("country", "id");
            param.put("apiKey", API_KEY);
            param.put("page", String.valueOf(page));

            viewModel.reqTopHeadLines(param, isLoadMore);
        }
    }

    private void observableMvvm() {
        viewModel.respTopHeadLines().observe(this, response -> {
            Log.i(TAG, "observable: ");
            refresh.setRefreshing(false);

            if (response != null && response.getArticles() != null) {
                Log.i(TAG, "have response");

                if (!response.isLoadMore()) {

                    isLoading = false;

                    hideNoData();
                    adapter.loadData(response.getArticles());

                    page = page + 1;

                } else {

                    if (isLoading && !isLastData) {

                        adapter.removeLoading();
                        isLoading = false;

                        adapter.addItems(response.getArticles());

                        page = page + 1;

                    }

                }

                isLastData = response.isLast();
                Log.i(TAG, "observableMvvm: isLast: " + response.isLast());
                Log.i(TAG, "observableMvvm: isLoadMore: " + response.isLoadMore());
            } else {

                Log.i(TAG, "no response");

                if (response != null) {
                    isLastData = response.isLast();

                    if (response.isLoadMore()) {
                        adapter.removeLoading();
                    }

                    if (!isLastData) {
                        showNoData();
                    }
                }

            }
            isCalled = true;
        });
    }

    public void showNoData() {
        Log.d(TAG, "show no data");
        if (adapter.getItemCount() > 0) {
            adapter.clear();
        }
        adapter.showNoData();
    }

    public void hideNoData() {
        Log.d(TAG, "hide no data");
        if (adapter.isNoData()) {
            adapter.removeNoData();
        }
    }

    private void initListener() {
        refreshListener();
        loadMoreListener();
    }

    private void refreshListener() {
        refresh.setOnRefreshListener(() -> {
            refresh.setRefreshing(true);
            if (adapter.isNoData()) {
                adapter.removeNoData();
            } else {
                adapter.clear();
            }

            page = 1;
            isCalled = false;
            isLastData = false;

            callApi(false);
        });
    }

    private void loadMoreListener() {
        recyclerView.addOnScrollListener(new PaginationRecyclerViewListener(layoutManager) {
            @Override
            public int dataSize() {
                return adapter.getItemCount();
            }

            @Override
            protected void onLoadMoreItems() {
                if (ConnectionUtils.isConnect(getBaseContext())) {
                    if (!isLastData) {
                        isLoading = true;
                        isCalled = false;
                        ArticlesItem emptyData = new ArticlesItem();
                        adapter.addLoading(emptyData);
                        callApi(true);
                    }
                }
            }

            @Override
            public boolean isLastData() {
                return isLastData;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isBottomToTop() {
                return false;
            }

            @Override
            public boolean useLoadMore() {
                return true;
            }
        });
        adapter.setOnClickObject((view, position, data) -> {
            Intent detailActivity = new Intent(this, DetailActivity.class);
            detailActivity.putExtra(DetailActivity.EXTRA_DETAIL_NEWS, (ArticlesItem) data);
            startActivity(detailActivity);
        });
    }
}