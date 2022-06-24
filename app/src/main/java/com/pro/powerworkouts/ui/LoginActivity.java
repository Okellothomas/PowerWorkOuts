package com.pro.powerworkouts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.powerworkouts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth.AuthStateListener mAuthenticationListener;

    @BindView(R.id.firebaseProgress) ProgressBar mLoginProgress;
    @BindView(R.id.loginProgressText)  TextView mLoadingText;
    @BindView(R.id.email) TextView mEmail;
    @BindView(R.id.passWord) EditText mPassWord;
    @BindView(R.id.login) Button mLogin;
    @BindView(R.id.createText) TextView mLoginText;

    private FirebaseAuth mAuthenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuthenticator = FirebaseAuth
                .getInstance();

        mAuthenticationListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user !=null){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mLogin.setOnClickListener(this);
        mLoginText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mLoginText){
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            startActivity(intent);
            finish();
        }

        if (v == mLogin){
            userLoggedIn();
            ProgressBar();
        }
    }

    private void userLoggedIn() {
        String email = mEmail.getText().toString().trim();
        String password = mPassWord.getText().toString().trim();
        if (email.equals("")){
            mEmail.setError("Please enter your email");
            return;
        }
        if (password.equals("")){
            mPassWord.setError("Enter your Password");
            return;
        }

        mAuthenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hidProgress();
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void ProgressBar(){
        mLoginProgress.setVisibility(View.VISIBLE);
    }
    private void hidProgress(){
        mLoginProgress.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuthenticator.addAuthStateListener(mAuthenticationListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        mAuthenticator.removeAuthStateListener(mAuthenticationListener);
    }
}