package com.example.authentication_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ImageView logoHead;
    private TextView logoTitle;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button logButton;
    private Button regButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoHead = findViewById(R.id.ivLogo);
        logoTitle = findViewById(R.id.logTitle);
        loginEmail = findViewById(R.id.et_email);
        loginPassword = findViewById(R.id.et_password);
        logButton = findViewById(R.id.log_button);
        regButton = findViewById(R.id.reg_button);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            updateUI(mAuth.getCurrentUser());
        }


        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                String password  = loginPassword.getText().toString();

                if(TextUtils.isEmpty(loginEmail.getText())){
                    loginEmail.setError("Qaitadan tolyqtyrynyz!");
                } if(TextUtils.isEmpty(loginPassword.getText())){
                    loginPassword.setError("Qaitadan toltyrynyz!");
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            private static final String TAG = "MainActivity";



                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }


        public void updateUI(FirebaseUser currentUser){
            Toast.makeText(getApplicationContext(), "You are register successfully!",
                    Toast.LENGTH_SHORT).show();
        }
}