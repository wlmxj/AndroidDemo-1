package com.dingmouren.androiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.androiddemo.demos.BatteryActivity;
import com.dingmouren.androiddemo.demos.ContactsActivity;
import com.dingmouren.androiddemo.demos.RuningTasksActivity;
import com.dingmouren.androiddemo.demos.SIMActivity;
import com.dingmouren.androiddemo.demos.ScreenDirectionActivity;
import com.dingmouren.androiddemo.demos.VibratorActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private List<String> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();

    }



    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mAdapter = new MyAdapter(this);


    }


    private void initListener() {
        mAdapter.setOnClickItemListener(new MyAdapter.OnClickItemListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 0://手机振动
                        startActivity(new Intent(MainActivity.this, VibratorActivity.class));
                        break;
                    case 1://获取手机通讯录
                        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
                        break;
                    case 2://还原手机桌面
                        try {
                            clearWallpaper();
                            Toast.makeText(view.getContext(),"桌面还原完成",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, SIMActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, RuningTasksActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, ScreenDirectionActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainActivity.this, BatteryActivity.class));
                        break;
                }
            }
        });
    }



    private void initData() {
        mList.clear();
        mList.add("手机振动");
        mList.add("手机通讯录");
        mList.add("还原手机桌面");
        mList.add("获取SIM卡信息");
        mList.add("获取正在运行的服务信息");
        mList.add("重力感应切换屏幕方向");
        mList.add("显示手机电量");
        mRecycler.setAdapter(mAdapter);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private WeakReference<MainActivity> weakActivity;
        private OnClickItemListener mListener;
        public MyAdapter(MainActivity activity) {
            weakActivity = new WeakReference<MainActivity>(activity);
        }

        public void setOnClickItemListener(OnClickItemListener listener){
            this.mListener = listener;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            MainActivity activity = weakActivity.get();
            if (activity != null){
                holder.tv.setText(activity.mList.get(position));
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener){
                            mListener.onClick(holder.cardView,position);
                        }
                    }
                });


            }
        }

        @Override
        public int getItemCount() {
            MainActivity activity = weakActivity.get();
            return activity == null ? 0 : activity.mList.size();
        }

        public class ViewHolder  extends RecyclerView.ViewHolder{
            CardView cardView;
            TextView tv;
            public ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.cardview);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }

        static interface OnClickItemListener{
            void onClick(View view,int position);
        }
    }
}
