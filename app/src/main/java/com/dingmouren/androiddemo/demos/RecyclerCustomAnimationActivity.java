package com.dingmouren.androiddemo.demos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.widgets.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingmouren on 2017/3/6.
 */

public class RecyclerCustomAnimationActivity extends AppCompatActivity {
    private CustomRecyclerView mRecycler;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_custom_animation);
        mRecycler = (CustomRecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

    }

    @Override//activity进入动画完成时，调用设置recyclerview的进入动画
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        mAdapter = new MyAdapter();
        mRecycler.setAdapter(mAdapter);
        mRecycler.scheduleLayoutAnimation();//安排动画
    }

    private static class  MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private List<String> mList = new ArrayList<>();

        public MyAdapter() {
            for (int i = 1; i < 100; i++) {
                mList.add("第"+i+"条数据");
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_custom_animation,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }
}
