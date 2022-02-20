package com.adiyusuf.testsandroidsalt.view.adapter.base;

import static com.adiyusuf.testsandroidsalt.view.adapter.base.NameKeyTypeViewRecyclerView.TYPE_ITEM;
import static com.adiyusuf.testsandroidsalt.view.adapter.base.NameKeyTypeViewRecyclerView.TYPE_LOAD_MORE;
import static com.adiyusuf.testsandroidsalt.view.adapter.base.NameKeyTypeViewRecyclerView.TYPE_NO_DATA;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiyusuf.testsandroidsalt.view.adapter.base.viewholder.DefaultViewHolder;
import com.adiyusuf.testsandroidsalt.view.listener.OnClickRecyclerDataListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnClickRecyclerDataObjectListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnLongClickRecyclerDataListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnLongClickRecyclerDataObjectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerView<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    public List<T> data;
    protected boolean isLoading = false;
    protected boolean isNoData = false;

    protected OnClickRecyclerDataListener onClick;
    protected OnClickRecyclerDataObjectListener onClickObject;
    protected OnLongClickRecyclerDataListener onLongClick;
    protected OnLongClickRecyclerDataObjectListener onLongClickObject;

    public BaseRecyclerView(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    @NotNull
    @Override
    public abstract VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType);

    protected abstract int getLoadMoreResourceLayout();

    protected abstract int getNoDataResourceLayout();

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    protected View getView(ViewGroup parent, int viewType) {
        Log.i("MainActivity", "getView: "+viewType);
        if (viewType == TYPE_LOAD_MORE) {
            return LayoutInflater.from(parent.getContext()).inflate(getLoadMoreResourceLayout(), parent, false);
        } else if (viewType == TYPE_NO_DATA) {
            return LayoutInflater.from(parent.getContext()).inflate(getNoDataResourceLayout(), parent, false);
        }
        return null;
    }

    protected int getDefaultItemViewType(int position) {
        if (isNoData) {
            return TYPE_NO_DATA;
        } else {
            if (isLoading) {
                return position == data.size() - 1 ? TYPE_LOAD_MORE : TYPE_ITEM;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    public void setOnClick(OnClickRecyclerDataListener onClick) {
        this.onClick = onClick;
    }

    public void setLongClick(OnLongClickRecyclerDataListener onLongClick) {
        this.onLongClick = onLongClick;
    }

    public void setLongClickObject(OnLongClickRecyclerDataObjectListener onLongClickObject) {
        this.onLongClickObject = onLongClickObject;
    }

    public void setOnClickObject(OnClickRecyclerDataObjectListener onClickObject) {
        this.onClickObject = onClickObject;
    }

    public void loadData(List<T> items) {
        data = items;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int position, T item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    public void updateItem(int position, T item) {
        if (data.contains(item)) {
            data.set(position, item);
        } else {
            data.add(position, item);
        }
        notifyDataSetChanged();
    }

    public void addUpdateItems(List<T> items) {
        int size = items.size();
        for (int i = 0; i < size; i++) {
            T item = items.get(i);
            int position = data.indexOf(item);
            if (position >= 0) {
                data.set(position, item);
            } else {
                addItem(item);
            }
        }

        notifyDataSetChanged();
    }

    public void addUpdateItemsToTop(List<T> items) {
        int size = items.size();
        for (int i = 0; i < size; i++) {
            T item = items.get(i);
            int position = data.indexOf(item);
            if (position >= 0) {
                data.set(position, item);
            } else {
                addItem(0, item);
            }
        }

        notifyDataSetChanged();
    }

    public boolean isNoData() {
        return isNoData;
    }

    public void addLoading(T item) {
        isLoading = true;
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void addNoData(T item) {
        isNoData = true;
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void removeLoading() {
        isLoading = false;
        if (!isNoData) {
            if (data.size() > 0) {
                int position = data.size() - 1;
                T item = getItem(position);
                if (item != null) {
                    data.remove(position);
                    notifyItemRemoved(position);
                }
            }
        }
    }

    public void removeNoData() {
        isNoData = false;
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void remove(T item) {
        int position = data.indexOf(item);
        remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public static class DefaultLoadingViewHolder extends DefaultViewHolder {
        public DefaultLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }

    public static class DefaultNoDataViewHolder extends DefaultViewHolder {
        public DefaultNoDataViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("DefaultNoDataViewHolder", "DefaultNoDataViewHolder: ");
        }

        @Override
        protected void clear() {

        }
    }
}
