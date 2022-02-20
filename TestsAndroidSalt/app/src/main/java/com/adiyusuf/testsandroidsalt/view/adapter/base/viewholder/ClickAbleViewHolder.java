package com.adiyusuf.testsandroidsalt.view.adapter.base.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.adiyusuf.testsandroidsalt.view.listener.OnClickRecyclerDataListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnClickRecyclerDataObjectListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnLongClickRecyclerDataListener;
import com.adiyusuf.testsandroidsalt.view.listener.OnLongClickRecyclerDataObjectListener;

public class ClickAbleViewHolder extends DefaultViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected OnClickRecyclerDataListener onClick;
    protected OnClickRecyclerDataObjectListener onClickObject;
    protected OnLongClickRecyclerDataListener onLongClick;
    protected OnLongClickRecyclerDataObjectListener onLongClickObject;

    public ClickAbleViewHolder(@NonNull View itemView, OnClickRecyclerDataListener onClick) {
        super(itemView);
        this.onClick = onClick;
        itemView.setOnClickListener(this);
    }

    public ClickAbleViewHolder(@NonNull View itemView, OnClickRecyclerDataObjectListener onClickObject, OnLongClickRecyclerDataObjectListener onLongClickObject) {
        super(itemView);
        this.onClickObject = onClickObject;
        itemView.setOnClickListener(this);
        this.onLongClickObject = onLongClickObject;
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onClick != null) {
            onClick.onClick(v, position);
        }
    }

    @Override
    protected void clear() {

    }

    @Override
    public boolean onLongClick(View v) {
        if (onLongClick != null) {
            onLongClick.onLongClick(v, position);
        }
        return true;
    }
}
