package com.adiyusuf.testsandroidsalt.view.adapter.base;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationRecyclerViewListener extends RecyclerView.OnScrollListener {
    private static final String TAG = PaginationRecyclerViewListener.class.getSimpleName();

    public static final int PAGE_START = 1;

    @NonNull
    private final RecyclerView.LayoutManager layoutManager;

    public PaginationRecyclerViewListener(@NonNull RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int totalItemCount = layoutManager.getItemCount();
        Log.d(TAG, "totalItemCount: " + totalItemCount);
        if (useLoadMore()) {
            Log.d(TAG, "use loadmore");
            if (!isLoading() && !isLastData()) {
                Log.d(TAG, "loading false");
                if (isBottomToTop()) {
                    Log.d(TAG, "bottom to top");
                    if (!recyclerView.canScrollVertically(-1)
                            && dy < 0
                            && totalItemCount >= dataSize()) {
                        //scrolled to TOP
                        onLoadMoreItems();
                        Log.d(TAG, "load");
                    }
                } else {
                    Log.d(TAG, "top to bottom");
                    if (!recyclerView.canScrollVertically(1)
                            && dy > 0
                            && totalItemCount >= dataSize()) {
                        //scrolled to BOTTOM
                        onLoadMoreItems();
                        Log.d(TAG, "load");
                    }
                }
            }
        }

    }

    public abstract int dataSize();

    protected abstract void onLoadMoreItems();

    public abstract boolean isLastData();

    public abstract boolean isLoading();

    public abstract boolean isBottomToTop();

    public abstract boolean useLoadMore();
}
