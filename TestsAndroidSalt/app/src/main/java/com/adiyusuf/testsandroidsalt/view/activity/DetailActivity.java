package com.adiyusuf.testsandroidsalt.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adiyusuf.testsandroidsalt.R;
import com.adiyusuf.testsandroidsalt.model.pojo.ArticlesItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_DETAIL_NEWS = "extra_detail_news";

    private ArticlesItem item;

    private TextView tvSource, tvTitle, tvDate, tvAuthor, tvBody;
    private MaterialButton btnNext;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }

    private void init() {

        item = getIntent().getParcelableExtra(EXTRA_DETAIL_NEWS);
        initViewId();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initDate();
    }

    private void initViewId() {
        tvSource = findViewById(R.id.tv_source);
        tvTitle = findViewById(R.id.tv_title);
        tvDate = findViewById(R.id.tv_date);
        tvAuthor = findViewById(R.id.tv_author);
        tvBody = findViewById(R.id.tv_body);
        ivImage = findViewById(R.id.iv_image);
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
    }

    private void initDate() {
        if (item != null) {
            if (item.getSource() != null && item.getSource().getName() != null) {
                tvSource.setText(item.getSource().getName());
            }
            if (item.getTitle() != null) {
                tvTitle.setText(item.getTitle());
            }
            if (item.getPublishedAt() != null) {
                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat defFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                Date newDate;
                String date = "";
                try {
                    newDate = apiFormat.parse(item.getPublishedAt());
                    date = defFormat.format(Objects.requireNonNull(newDate));
                } catch (Exception e) {
                    //error convert
                }
                tvDate.setText(date);
            }
            if (item.getAuthor() != null) {
                String author = getString(R.string.author) + " " + item.getAuthor();
                tvAuthor.setText(author);
            }
            if (item.getContent() != null) {
                String[] body = item.getContent().split("… \\[");
                String content = body[0] + "...";
                tvBody.setText(content);
            } else {
                tvBody.setVisibility(View.GONE);
            }
            if (item.getUrlToImage() != null) {
                Glide.with(this)
                        .load(item.getUrlToImage())
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .apply(new RequestOptions().override(250, 250))
                        .placeholder(R.drawable.image_not_found)
                        .into(ivImage);
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            if (item != null && item.getUrl() != null) {
                Uri webpage = Uri.parse(item.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
            }
        }
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}