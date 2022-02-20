package com.adiyusuf.testsandroidsalt.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.adiyusuf.testsandroidsalt.R;
import com.adiyusuf.testsandroidsalt.model.pojo.ArticlesItem;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_DETAIL_NEWS = "extra_detail_news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }
    private void init(){
        ArticlesItem item = getIntent().getParcelableExtra(EXTRA_DETAIL_NEWS);
        Log.i(TAG, "init: "+item.getTitle());

    }
}