package media.t3h.com.smartrestaurant.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import media.t3h.com.smartrestaurant.R;
import media.t3h.com.smartrestaurant.fragment.LoginFragment;
import media.t3h.com.smartrestaurant.fragment.RegisterFragment;
import media.t3h.com.smartrestaurant.fragment.ResetPassFragment;

public class MainActivity extends AppCompatActivity implements RegisterFragment.OnClickButtonRegisterListener,
        LoginFragment.OnClickButtonLoginListener, ResetPassFragment.OnClickButtonListener {

    private FragmentManager manager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private FirebaseAuth auth;
    private ResetPassFragment resetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        manager = getSupportFragmentManager();
        loginFragment = new LoginFragment(auth);
        loginFragment.setOnClickButtonListener(this);
        registerFragment = new RegisterFragment(auth);
        registerFragment.setOnClickButtonListener(this);
        resetFragment = new ResetPassFragment(auth);
        resetFragment.setOnClickButtonListener(this);
        manager.beginTransaction().add(R.id.container_login, loginFragment)
                .add(R.id.container_login, registerFragment).add(R.id.container_login, resetFragment)
                .show(loginFragment)
                .hide(registerFragment)
                .hide(resetFragment)
                .commit();
    }

    @Override
    public void onClickListener() {
        //if success
        manager.beginTransaction().show(loginFragment)
                .hide(registerFragment).hide(resetFragment).commit();
    }

    @Override
    public void onClickListener(int ID) {
        switch (ID){
            case LoginFragment.LOGIN:
                Toast.makeText(this, " Dang nhap thanh cong ", Toast.LENGTH_LONG).show();
                break;
            case LoginFragment.TRANSFER_REGISTER:
                manager.beginTransaction().show(registerFragment)
                        .hide(loginFragment).hide(resetFragment).commit();
                break;
            case ResetPassFragment.BACK:
                manager.beginTransaction().show(loginFragment)
                        .hide(registerFragment).hide(resetFragment).commit();
                break;
            case ResetPassFragment.RESET:
                manager.beginTransaction().show(loginFragment)
                        .hide(registerFragment).hide(resetFragment).commit();
                break;
            case LoginFragment.TRANSFER_RESET_PASS:
                manager.beginTransaction().show(resetFragment)
                        .hide(registerFragment).hide(loginFragment).commit();
                break;
            default:
                break;
        }
    }
}
