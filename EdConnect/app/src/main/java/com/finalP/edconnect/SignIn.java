package com.finalP.edconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText password,user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        password = (EditText)findViewById(R.id.password);
        user_email = (EditText)findViewById(R.id.user_email);
    }

    public boolean validateForm() {
        boolean valid = true;

        String email = user_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            user_email.setError("Required.");
            valid = false;
        } else {
            user_email.setError(null);
        }

        String user_password = password.getText().toString();
        if (TextUtils.isEmpty(user_password)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


    public void signIn(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    public void updateUI(FirebaseUser user){
        Intent myintent = new Intent(SignIn.this, Home.class);
        myintent.putExtra("type","Student");
        startActivity(myintent);
        finish();


    }

    public void user_signin(View view){
        signIn(user_email.getText().toString(), password.getText().toString());
    }
}


