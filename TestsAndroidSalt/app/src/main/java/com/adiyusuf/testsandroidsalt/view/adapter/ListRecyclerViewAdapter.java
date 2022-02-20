package com.adiyusuf.testsandroidsalt.view.adapter;

import static com.adiyusuf.testsandroidsalt.view.adapter.base.NameKeyTypeViewRecyclerView.TYPE_ITEM;
import static com.adiyusuf.testsandroidsalt.view.adapter.base.NameKeyTypeViewRecyclerView.TYPE_LOAD_MORE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.adiyusuf.testsandroidsalt.R;
import com.adiyusuf.testsandroidsalt.model.pojo.ArticlesItem;
import com.adiyusuf.testsandroidsalt.view.adapter.base.BaseRecyclerView;
import com.adiyusuf.testsandroidsalt.view.adapter.base.viewholder.ClickAbleViewHolder;
import com.adiyusuf.testsandroidsalt.view.adapter.base.viewholder.DefaultViewHolder;
import com.adiyusuf.testsandroidsalt.view.listener.OnClickRecyclerDataObjectListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnLongClickRecyclerDataObjectListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ListRecyclerViewAdapter extends BaseRecyclerView<ArticlesItem, DefaultViewHolder> {

    public ListRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getDefaultItemViewType(position);
    }

    @Override
    protected int getLoadMoreResourceLayout() {
        return R.layout.view_loadmore_loading;
    }

    @Override
    protected int getNoDataResourceLayout() {
        return R.layout.view_no_data;
    }

    @NotNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false), onClickObject, onLongClickObject);
        } else if (viewType == TYPE_LOAD_MORE) {
            return new DefaultLoadingViewHolder(getView(parent, viewType));
        } else {
            return new DefaultNoDataViewHolder(getView(parent, viewType));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        holder.onBind(position);
    }

    class ContentViewHolder extends ClickAbleViewHolder {
        private final TextView tvTitle, tvSource, tvDate;
        private final ImageView image;

        public ContentViewHolder(@NonNull View itemView, OnClickRecyclerDataObjectListener onClickObject, OnLongClickRecyclerDataObjectListener onLongClickObject) {
            super(itemView, onClickObject, onLongClickObject);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSource = itemView.findViewById(R.id.tv_source);
            tvDate = itemView.findViewById(R.id.tv_date);
            image = itemView.findViewById(R.id.iv_image);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            String title = "";
            String source = "";
            String date = "";
            String img = "";

            ArticlesItem currentData = data.get(position);

            if (currentData.getTitle() != null) {
                title = currentData.getTitle();
            }
            if (currentData.getSource() != null && currentData.getSource().getName() != null) {
                source = currentData.getSource().getName();
            }
            if (currentData.getPublishedAt() != null) {
                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat defFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                Date newDate;
                try {
                    newDate = apiFormat.parse(currentData.getPublishedAt());
                    date = defFormat.format(Objects.requireNonNull(newDate));
                } catch (Exception e) {
                    //error convert
                }
            }
            if (currentData.getUrlToImage() != null) {
                img = currentData.getUrlToImage();
            }

            tvTitle.setText(title);
            tvSource.setText(source);
            tvDate.setText(date);

            Glide.with(context)
                    .load(img)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(new RequestOptions().override(250, 250))
                    .placeholder(R.drawable.image_not_found)
                    .into(image);

        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            if (onClickObject != null) {
                onClickObject.onClick(v, position, data.get(position));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onLongClickObject != null) {
                onLongClickObject.onLongClick(v, position, data.get(position));
            }
            return true;
        }
    }

    public void showLoadMoreLoading() {
        if (!isNoData) {
            ArticlesItem emptyData = new ArticlesItem();
            addLoading(emptyData);
        }
    }

    public void showNoData() {
        if (getItemCount() == 0) {
            isNoData = true;
            ArticlesItem emptyData = new ArticlesItem();
            addNoData(emptyData);
        }
    }
}
