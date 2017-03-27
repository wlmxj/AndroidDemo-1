package com.dingmouren.androiddemo.activity;

import android.app.ActivityManager;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dingmouren on 2017/2/19.
 */

public class RuningTasksActivity extends AppCompatActivity {
    @BindView(R.id.recycler)  RecyclerView mRecycler;
    private List<String> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ActivityManager  activityManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runing_tasks);
        ButterKnife.bind(this);
        mAdapter = new MyAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = activityManager.getRunningServices(Integer.MAX_VALUE);
        mList.clear();
        for (ActivityManager.RunningServiceInfo info : infos){
            mList.add(info.process+"-pid:" +info.pid+" uid:"+ info.uid);
        }
        mRecycler.setAdapter(mAdapter);

    }


    /**
     * 适配器
     */
    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private WeakReference<RuningTasksActivity> weakActivity;
        private RuningTasksActivity mActivity;
        public MyAdapter(RuningTasksActivity activity) {
            weakActivity = new WeakReference<RuningTasksActivity>(activity);
            if (weakActivity.get() != null){
                this.mActivity = weakActivity.get();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_running_tasks,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(mActivity.mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mActivity.mList == null ? 0 : mActivity.mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
            }
        }
    }
}
