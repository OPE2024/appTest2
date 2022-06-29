package com.onaopemipodimowo.apptest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.inspector.protocol.module.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import fragment.HomeFragment;

public class RegistrationActivity extends AppCompatActivity {
//    private EditText emailTextView, passwordTextView;
//    private Button Btn;
//    private ProgressBar progressbar;
//    private FirebaseAuth mAuth;
    private EditText editTextRegisterFullName,editTextRegisterEmail,editTextRegisterDoB,editTextRegisterCollege,editTextRegisterPwd,editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog picker;
    private static final String TAG= "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Register");
        Toast.makeText(RegistrationActivity.this, "You can Register now", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterCollege = findViewById(R.id.editText_register_college);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_Confirm_password);

        //RadioButton for Gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        //Setting up datePicker on edittext
        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date picker dialog
                picker = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editTextRegisterDoB.setText(dayOfMonth + "/" +(month + 1) + "/" + year );
                    }
                }, year, month, day);
                picker.show();
            }
        });

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                //obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textCollege = editTextRegisterCollege.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
                String textGender; // cannot obtain the value before verifying if any button was selected or not

                if (TextUtils.isEmpty(textFullName)){
                    Toast.makeText(RegistrationActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full Name is required");
                    editTextRegisterFullName.requestFocus();
                }else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegistrationActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegistrationActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textDoB)){
                    Toast.makeText(RegistrationActivity.this, "Please your date of birth", Toast.LENGTH_LONG).show();
                    editTextRegisterDoB.setError("Date of Birth is required");
                    editTextRegisterDoB.requestFocus();
                }else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(RegistrationActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }else if (TextUtils.isEmpty(textCollege)){
                    Toast.makeText(RegistrationActivity.this, "Please enter the name of your College", Toast.LENGTH_LONG).show();
                    editTextRegisterCollege.setError("College name is required");
                    editTextRegisterCollege.requestFocus();
                }else if (TextUtils.isEmpty(textPwd)){
                    Toast.makeText(RegistrationActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                }else if (textPwd.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Password should be atleast 6 digits", Toast.LENGTH_LONG).show();
                    editTextRegisterPwd.setError("Password too weak");
                    editTextRegisterPwd.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmPwd)){
                    Toast.makeText(RegistrationActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPwd.setError("Password Confrimation is required");
                    editTextRegisterConfirmPwd.requestFocus();
                }else if (!textPwd.equals(textConfirmPwd)){
                    Toast.makeText(RegistrationActivity.this, "Please same password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPwd.setError("Password Confirmation is required");
                    editTextRegisterConfirmPwd.requestFocus();
                    // clears entered password
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConfirmPwd.clearComposingText();
                }else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textDoB, textGender, textCollege, textPwd);

                }

            }
        });


//        // taking FirebaseAuth instance
//        mAuth = FirebaseAuth.getInstance();
//
//        // initialising all views through id defined above
//        emailTextView = findViewById(R.id.email);
//        passwordTextView = findViewById(R.id.passwd);
//        Btn = findViewById(R.id.btnregister);
//        progressbar = findViewById(R.id.progressbar);

//        // Set on Click Listener on Registration button
//        Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                registerNewUser();
//            }
//        });
    }
    // Register user using the credentials given
    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String textCollege, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Create User Profile
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update Display name of user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter User Data into the firebase Realtime Database;
                    ReadWriteUserDetails writeUsersDetails = new ReadWriteUserDetails(textDoB,textGender,textCollege);

                    // Enter User reference from database for Registered Users
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUsersDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                // send verification email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegistrationActivity.this, "Successful Registration", Toast.LENGTH_LONG).show();

                    /// Open user Profile successful registration
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    //To prevent User from returning back to register Activity on pressing back button after registration
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); //to close registration activity
                            }else {
                                Toast.makeText(RegistrationActivity.this, "User registration failed.Please try again", Toast.LENGTH_LONG).show();
                            }
                            //Hide progressBar
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak. Kindly add numbers and special characters");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Your email is invalid and already in-use. kindly re-enter");
                        editTextRegisterPwd.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("User is already registered with this email. use another email");
                        editTextRegisterPwd.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegistrationActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
//    private void registerNewUser()
//    {
//
//        // show the visibility of progress bar to show loading
//        progressbar.setVisibility(View.VISIBLE);
//
//        // Take the value of two edit texts in Strings
//        String email, password;
//        email = emailTextView.getText().toString();
//        password = passwordTextView.getText().toString();
//
//        // Validations for input email and password
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(getApplicationContext(),
//                            "Please enter email!!",
//                            Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(getApplicationContext(),
//                            "Please enter password!!",
//                            Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//
//        // create new user or register new user
//        mAuth
//                .createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task)
//                    {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(),
//                                            "Registration successful!",
//                                            Toast.LENGTH_LONG)
//                                    .show();
//
//                            // hide the progress bar
//                            progressbar.setVisibility(View.GONE);
//
//                            // if the user created intent to login activity
//                            Intent intent
//                                    = new Intent(RegistrationActivity.this,
//                                    MainActivity.class);
//                            startActivity(intent);
//                        }
//                        else {
//
//                            // Registration failed
//                            Toast.makeText(
//                                            getApplicationContext(),
//                                            "Registration failed!!"
//                                                    + " Please try again later",
//                                            Toast.LENGTH_LONG)
//                                    .show();
//
//                            // hide the progress bar
//                            progressbar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }
}