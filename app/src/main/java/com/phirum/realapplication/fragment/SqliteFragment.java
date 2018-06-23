package com.phirum.realapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phirum.realapplication.R;
import com.phirum.realapplication.adapter.MyListAdapter;
import com.phirum.realapplication.db.UserDbRepo;
import com.phirum.realapplication.pojo.Datum;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SqliteFragment extends Fragment {
    public static final String TAG = SqliteFragment.class.getSimpleName();
    @BindView(R.id.rcv)
    RecyclerView mRecyclerView;
    private MyListAdapter mAdapter;
    private UserDbRepo mUserDbRepo;

    public SqliteFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sqlite_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        mUserDbRepo = new UserDbRepo(view.getContext());
        mAdapter = new MyListAdapter(view.getContext());
        mRecyclerView.setAdapter(mAdapter);
        new StaticUserTask(mUserDbRepo).execute();
    }

    private class StaticUserTask extends AsyncTask<String, Void, LinkedList<Datum>> {
        private WeakReference<UserDbRepo> mWeakReference;

        public StaticUserTask(UserDbRepo userDbRepo) {
            mWeakReference = new WeakReference<>(userDbRepo);
        }

        @Override
        protected LinkedList<Datum> doInBackground(String... strings) {
            if (mWeakReference.get() != null)
                return mWeakReference.get().getAllData();
            return new LinkedList<>();
        }

        @Override
        protected void onPostExecute(LinkedList<Datum> data) {
            for (Datum ob : data) {
                mAdapter.insertItem(ob);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}