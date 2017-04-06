package com.dingmouren.androiddemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class RemoveItemViewHolder extends RecyclerView.ViewHolder {
    public TextView content,delete;
    public LinearLayout layout;
    public RemoveItemViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.item_content);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
