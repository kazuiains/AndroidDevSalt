package com.adiyusuf.testsandroidsalt.view.adapter.base.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class DefaultViewHolder extends RecyclerView.ViewHolder {
    public int position;

    public DefaultViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        this.position = position;
        clear();
    }

    public int getCurrentPosition() {
        return position;
    }
}
