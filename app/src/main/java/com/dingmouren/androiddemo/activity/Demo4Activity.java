package com.dingmouren.androiddemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.listener.OnItemClickListener;
import com.dingmouren.androiddemo.viewholder.RemoveItemViewHolder;
import com.dingmouren.androiddemo.widgets.RemoveItemRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class Demo4Activity extends AppCompatActivity {
    private RemoveItemRecyclerView mRecycler;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo4);
        try {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                data.add(i+"");
            }
            mRecycler = (RemoveItemRecyclerView) findViewById(R.id.recycler);
            mAdapter = new MyAdapter(data);
            mRecycler.setLayoutManager(new LinearLayoutManager(this));
            mRecycler.setAdapter(mAdapter);
            mRecycler.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(view.getContext(),"点击了"+ position,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDeleteClick(int position) {
                    mAdapter.removeItem(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error",e.getMessage());
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<RemoveItemViewHolder>{
        private List<String> mList;

        public MyAdapter(List<String> list) {
            this.mList = list;
        }

        public void removeItem(int position){
            if (mList.size() > 0 && mList != null) {
                mList.remove(position);
                notifyItemRemoved(position);
            }
        }
        @Override
        public RemoveItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Demo4Activity.this).inflate(R.layout.item_removeitem,parent,false);//没有这个false带来的bug,You must call removeView() on the child's parent first
            return new RemoveItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RemoveItemViewHolder holder, int position) {
            holder.content.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
