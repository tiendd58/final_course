package media.t3h.com.smartrestaurant.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import media.t3h.com.smartrestaurant.R;

/**
 * Created by duyti on 8/5/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final int LOGIN = 1;
    public static final int TRANSFER_REGISTER = 2;
    public static final int TRANSFER_RESET_PASS = 3;
    private EditText etEmail, etPass;
    private Button btnLogin, btnTranferRegister;
    private ProgressBar progressBar;
    private TextView tvForgotPass;

    private FirebaseAuth auth;

    private OnClickButtonLoginListener listener;

    public void setOnClickButtonListener(OnClickButtonLoginListener event){
        listener=event;
    }


    public LoginFragment(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_login,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        etEmail = (EditText) rootView.findViewById(R.id.et_email);
        etPass = (EditText) rootView.findViewById(R.id.et_pass);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        btnTranferRegister = (Button) rootView.findViewById(R.id.btn_transfer_register);
        tvForgotPass = (TextView) rootView.findViewById(R.id.tv_forgot_password);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        //setEvent
        btnLogin.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        btnTranferRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String email = etEmail.getText().toString();
                final String password = etPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        etPass.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getContext(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    listener.onClickListener(LOGIN);
                                }
                            }
                        });
                break;
            case R.id.btn_transfer_register:
                listener.onClickListener(TRANSFER_REGISTER);
                break;
            case R.id.tv_forgot_password:
                listener.onClickListener(TRANSFER_RESET_PASS);
                break;
            default:
                break;
        }
    }

    public interface OnClickButtonLoginListener{
        void onClickListener(int ID);
    }

}
