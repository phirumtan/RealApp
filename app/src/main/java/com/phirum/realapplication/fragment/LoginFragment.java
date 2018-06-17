package com.phirum.realapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.phirum.realapplication.MainActivity;
import com.phirum.realapplication.R;
import com.phirum.realapplication.webservice.APIClient;
import com.phirum.realapplication.webservice.APIUserInterface;

import java.io.IOException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.edt_username) TextInputEditText mEdtUsername;
    @BindView(R.id.edt_password) TextInputEditText mEdtPassword;
    @BindView(R.id.btn_login) Button mBtnLogin;

    private APIUserInterface mApiUserInterface;


    public LoginFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mApiUserInterface = APIClient.getClient().create(APIUserInterface.class);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                methodDologin(v);
            }
        });

        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    methodDologin(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void methodDologin(final View v) {
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        Call<Object> call = mApiUserInterface.doLogin(username, password);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.errorBody() == null) {
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                    Toast.makeText(v.getContext(), "token =" + response.body(), Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    try {
                        Toast.makeText(v.getContext(), "error " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                if (t instanceof HttpException) {
                    Toast.makeText(v.getContext(), "Error hey HttpException No Internet", Toast.LENGTH_SHORT).show();
                } else if (t instanceof SocketTimeoutException) {
                    Toast.makeText(v.getContext(), "Error hey SocketTimeoutException No Internet", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IOException) {
                    Toast.makeText(v.getContext(), "Error hey IOException No Internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Error hey " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
