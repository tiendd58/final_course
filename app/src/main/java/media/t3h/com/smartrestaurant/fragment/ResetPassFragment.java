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
import com.google.firebase.auth.FirebaseAuth;

import media.t3h.com.smartrestaurant.R;

/**
 * Created by duyti on 8/9/2016.
 */
public class ResetPassFragment extends Fragment implements View.OnClickListener {
    public static final int BACK = 101;
    public static final int RESET = 102;
    private EditText etEmail;
    private Button btnReset;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private OnClickButtonListener listener;
    private TextView tvBack;

    public void setOnClickButtonListener(OnClickButtonListener event){
        listener=event;
    }

    public ResetPassFragment(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_reset_password,container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        etEmail = (EditText) rootView.findViewById(R.id.et_email);
        btnReset = (Button) rootView.findViewById(R.id.btn_reset);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        tvBack = (TextView) rootView.findViewById(R.id.tv_back);
        //setEvent
        btnReset.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reset:
                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    listener.onClickListener(RESET);
                                } else {
                                    Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                break;
            case R.id.tv_back:
                listener.onClickListener(BACK);
            default:
                break;
        }
    }

    public interface OnClickButtonListener{
        void onClickListener(int ID);
    }
}
