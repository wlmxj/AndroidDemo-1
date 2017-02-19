package com.dingmouren.androiddemo.demos;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingmouren.androiddemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取手机通讯录中姓名和手机号码，添加权限 <uses-permission android:name="android.permission.READ_CONTACTS"/>
 */

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private Cursor mCursor;
    private MyAdapter mAdapter;
    private List<Person> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initView();


    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new MyAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        mCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        mList.clear();
        if (mCursor == null) return;
        while (mCursor.moveToNext()){
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Person person = new Person(name,number);
            mList.add(person);
        }
        mRecycler.setAdapter(mAdapter);
    }


    /**
     * 实体类
     */
    static class Person{
        public String persongName;
        public String phoneNum;

        public Person(String persongName, String phoneNum) {
            this.persongName = persongName;
            this.phoneNum = phoneNum;
        }

        public String getPersongName() {
            return persongName;
        }

        public void setPersongName(String persongName) {
            this.persongName = persongName;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }
    }
    /**
     * 适配器
     */
    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private WeakReference<ContactsActivity> weakActivity;
        private ContactsActivity mActivity;
        public MyAdapter(ContactsActivity activity) {
            weakActivity = new WeakReference<ContactsActivity>(activity);
            if (weakActivity.get() != null){
                this.mActivity = weakActivity.get();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(mActivity.mList.get(position).getPersongName());
            holder.num.setText(mActivity.mList.get(position).getPhoneNum());
        }

        @Override
        public int getItemCount() {
            return mActivity.mList == null ? 0 : mActivity.mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView num;
            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                num = (TextView) itemView.findViewById(R.id.number);
            }
        }
    }
}
