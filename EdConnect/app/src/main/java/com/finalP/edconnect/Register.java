package com.finalP.edconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText Name,Age,user_email,password,repassword;
    Spinner spinner;
    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        Name = (EditText)findViewById((R.id.Name));
        Age = (EditText)findViewById((R.id.Age));
        user_email = (EditText)findViewById((R.id.user_email));
        password = (EditText)findViewById((R.id.password));
        repassword = (EditText)findViewById((R.id.repassword));

        spinner =(Spinner)findViewById(R.id.Spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.option_array));

    }

    public boolean validateForm() {

        String spinner_value = spinner.getSelectedItem().toString();

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

        String user_re_password = repassword.getText().toString();

        if (TextUtils.isEmpty(user_re_password)) {
            repassword.setError("Required.");
            valid = false;
        } else {
            repassword.setError(null);
        }

        return valid;
    }

    public void add_user (View view){
        createAccount(user_email.getText().toString(), password.getText().toString());
    }

    public void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String spinner_value = spinner.getSelectedItem().toString();

                            Map<String,String > myHashMap = new HashMap<String, String>();
                            myHashMap.clear();

                            myHashMap.put("Name",Name.getText().toString());
                            myHashMap.put("Age",Age.getText().toString());
                            myHashMap.put("Type",spinner_value);

                            myRef.child(spinner_value).child(user.getUid()).child(user.getUid()).setValue(myHashMap);

                            updateUI(user, spinner_value);

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Authentication failed.",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
    public void updateUI(FirebaseUser user, String type_of_user) {

     Intent myintent = new Intent(Register.this, Home.class);
     myintent.putExtra("type",type_of_user);
     startActivity(myintent);
     finish();
    }


}

