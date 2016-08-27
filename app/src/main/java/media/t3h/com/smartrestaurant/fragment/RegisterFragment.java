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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import media.t3h.com.smartrestaurant.R;

/**
 * Created by duyti on 8/5/2016.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener  {

    private EditText etEmail, etPass;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private OnClickButtonRegisterListener listener;

    public void setOnClickButtonListener(OnClickButtonRegisterListener event){
        listener=event;
    }

    public RegisterFragment(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_register,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        etEmail = (EditText) rootView.findViewById(R.id.et_email);
        etPass = (EditText) rootView.findViewById(R.id.et_pass);
        btnRegister = (Button) rootView.findViewById(R.id.btn_register);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        //setEvent
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getContext(), "Create User With Email:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    listener.onClickListener();
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

    public interface OnClickButtonRegisterListener{
        void onClickListener();
    }
}
