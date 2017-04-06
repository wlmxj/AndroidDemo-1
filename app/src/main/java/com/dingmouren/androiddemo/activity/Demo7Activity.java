package com.dingmouren.androiddemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;
import com.dingmouren.androiddemo.helper.Demo7ItemTouchHelperCallback;
import com.dingmouren.androiddemo.listener.Demo7MoveAndSwipedListener;
import com.dingmouren.androiddemo.listener.Demo7StartDragListener;
import com.dingmouren.androiddemo.listener.Demo7StateChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dingmouren on 2017/4/6.
 * 资料链接：http://blog.csdn.net/nugongahou110/article/details/50505210
 */

public class Demo7Activity extends AppCompatActivity implements Demo7StartDragListener{
    private RecyclerView mRecycler;
    private MyAdapter mAdapter;
    private Demo7ItemTouchHelperCallback mCallBack;
    private ItemTouchHelper mItemTouchHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo7);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new MyAdapter(this);
        mCallBack = new Demo7ItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mCallBack);
        mItemTouchHelper.attachToRecyclerView(mRecycler);//绑定到RecyclerView
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);//固定recyclerview的大小
        mRecycler.setAdapter(mAdapter);
    }

    @Override//点击item布局右边图片就可可以实现拖拽，默认是长按才能实现拖拽
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);//开始拖拽操作
    }

    /**
     * 适配器
     */
    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Demo7MoveAndSwipedListener{
        private List<String> mItems = new ArrayList<>();
        private Demo7StartDragListener mStartDragListener;
        public MyAdapter(Demo7StartDragListener listener) {
            mStartDragListener = listener;
            for (int i = 0; i < 10; i++) {
                mItems.add(i+"");
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo7,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.tv.setText(mItems.get(position));
            holder.img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //按下就执行拖拽
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN && mStartDragListener != null){
                        mStartDragListener.startDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override//拖拽
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mItems,fromPosition,toPosition);//交换item的位置
            notifyItemMoved(fromPosition,toPosition);
            return true;
        }

        @Override//侧滑删除
        public void onItemDismiss(int position) {
            mItems.remove(position);//在集合中删除数据
            notifyItemRemoved(position);//删除Recyclerview中对应的item，有动画的
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements Demo7StateChangeListener{
            TextView tv;
            ImageView img;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
                img = (ImageView) itemView.findViewById(R.id.img);
            }

            @Override
            public void onItemSelected() {//选中时改变item背景颜色
                itemView.setBackgroundColor(Color.RED);
            }

            @Override//恢复item的颜色
            public void onItemClear() {
                itemView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
    }
}
