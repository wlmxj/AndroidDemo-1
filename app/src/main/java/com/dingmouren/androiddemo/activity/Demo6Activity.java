package com.dingmouren.androiddemo.activity;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingmouren on 2017/4/6.
 */

public class Demo6Activity extends AppCompatActivity {
    private RecyclerView mRecycler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo6);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        MyAdapter myAdapter = new MyAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(myAdapter);
    }

    /**
     * 适配器
     */
    public static class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> mList = new ArrayList<>();
        public enum ITEM_TYPE {
            ITEM_TYPE_IMG,
            ITEM_TYPE_TEXT
        }

        public MyAdapter() {
            for (int i = 0; i < 10; i++) {
                mList.add(i+"");
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.ITEM_TYPE_IMG.ordinal()){
                return new ImgViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo6_img,parent,false));
            }else if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()){
                return  new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo6_text,parent,false));
            }else {
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ImgViewHolder){
                ((ImgViewHolder) holder).tv.setText(mList.get(position));
            }else if (holder instanceof TextViewHolder){
                ((TextViewHolder) holder).tv.setText(mList.get(position));
            }
        }

        @Override
        public int getItemViewType(int position) {//决定返回的类型
            if (position%2 == 0){
                return ITEM_TYPE.ITEM_TYPE_IMG.ordinal();
            }else {
                return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        /**
         * 两种ViewHolder
         */
        public static class ImgViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;
            public ImgViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }

        public static class TextViewHolder extends RecyclerView.ViewHolder{
            public TextView tv;
            public TextViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }
}
