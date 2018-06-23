package com.phirum.realapplication.holder;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.phirum.realapplication.R;
import com.phirum.realapplication.adapter.MyListAdapter;
import com.phirum.realapplication.db.UserDbRepo;
import com.phirum.realapplication.pojo.Datum;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateUserHolder {

    private WeakReference<UserDbRepo> mRepoWeakReference;
    private Datum datum;
    public View v;
    private AlertDialog dialog;
    private MyListAdapter adapter;
    private int pos;
    @BindView(R.id.edt_username)
    TextInputEditText mEdtUsername;
    @BindView(R.id.edt_pass)
    TextInputEditText mEdtPassword;

    public UpdateUserHolder(Context context, UserDbRepo dbRepo, Datum datum) {
        this.datum = datum;
        this.mRepoWeakReference = new WeakReference<>(dbRepo);
        v = LayoutInflater.from(context).inflate(R.layout.partial_user_update, null, false);
        ButterKnife.bind(this, v);
        mEdtUsername.setText(datum.firstName);
        mEdtPassword.setText(datum.password);

    }

    public void setAdapter(MyListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @OnClick({R.id.btn_ok, R.id.btn_cancel})
    public void doUpdateUser(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                datum.withFirstName(mEdtUsername.getText().toString());
                datum.withPassword(mEdtPassword.getText().toString());
                if (mRepoWeakReference.get().updateDataToTable(datum) > 0) {
                    Toast.makeText(v.getContext(), "update success", Toast.LENGTH_SHORT).show();
                    adapter.mListItem.set(pos, datum);

                    adapter.notifyItemChanged(pos);
                } else
                    Toast.makeText(v.getContext(), "update success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                Toast.makeText(v.getContext(), "cancel call", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
        }
    }

}
