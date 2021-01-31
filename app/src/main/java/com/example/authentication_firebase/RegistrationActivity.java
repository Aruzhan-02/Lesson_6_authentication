package com.example.authentication_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {
    private ImageView logoHead;
    private TextView logoTitle, jataTitle;
    private RadioButton maleRadio, femaleRadio;
    private TextInputLayout logFullName, logEmail, logPhone, logPassword;
    private CheckBox noCheck, yesCheck;
    private Button regButton, logButton;
    private Spinner spinner;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private User user;

    private String gender = "";
    private String dormitory = "";
    private String[] faculties  = {"Mamandygynyzdy tandanyz", "IT", "Computer Science", "Software engineering", "Cybersecurity", "Data Science", "AI"};
    public static final String USER = "user";
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        logoHead = findViewById(R.id.iv_logo);
        logoTitle = findViewById(R.id.logoTitle);
        maleRadio = findViewById(R.id.maleRadio);
        femaleRadio = findViewById(R.id.femaleRadio);
        logFullName = findViewById(R.id.login_fullname);
        logEmail = findViewById(R.id.login_email);
        logPhone = findViewById(R.id.login_phone);
        logPassword = findViewById(R.id.login_password);
        jataTitle = findViewById(R.id.jataTitle);
        noCheck = findViewById(R.id.noCheck);
        yesCheck = findViewById(R.id.yesCheck);
        regButton = findViewById(R.id.reg_button);
        logButton = findViewById(R.id.log_button);
        spinner = findViewById(R.id.spinner);


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, faculties);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();



        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = logFullName.getEditText().getText().toString();
                String email = logEmail.getEditText().getText().toString();
                String phone = logPhone.getEditText().getText().toString();
                String password = logPassword.getEditText().getText().toString();
                String faculties = spinner.getSelectedItem().toString();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegistrationActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegistrationActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }if(TextUtils.isEmpty(fullName)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter full name", Toast.LENGTH_SHORT).show();
                }if(TextUtils.isEmpty(phone)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }


                if(maleRadio.isChecked()){
                    gender = "Male";
                }if(femaleRadio.isChecked()){
                    gender = "Female";
                }


                if(yesCheck.isChecked()){
                    dormitory = "Yes, dorm";
                }if(noCheck.isChecked()){
                    dormitory = "No, home";
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    User user = new User (
                                            gender,
                                            fullName,
                                            email,
                                            phone,
                                            password,
                                            dormitory,
                                            faculties

                                    );

                                    FirebaseDatabase.getInstance().getReference(USER)
                                            .child(fullName).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(RegistrationActivity.this, "Siz satti tirkeldiniz!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                } else {


                                }

                                // ...
                            }
                        });



            }
        });
    }

}