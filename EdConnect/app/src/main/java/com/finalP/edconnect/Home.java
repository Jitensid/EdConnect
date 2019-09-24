package com.finalP.edconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView Name,Age;
    EditText update_age,update_name;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Bundle bundle;

    String type_of_user;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Name = (TextView) findViewById(R.id.Name);
        Age = (TextView) findViewById(R.id.Age);

        bundle = getIntent().getExtras();
        type_of_user = bundle.getString("type");
        myRef = database.getReference().child(type_of_user).child(user.getUid());

        update_name = (EditText)findViewById(R.id.update_name);
        update_age = (EditText)findViewById(R.id.update_age);


        show_details();
    }

    public void SignOut_a_user(View view) {
        mAuth.signOut();
        updateUI();

    }

    public void updateUI(){
        Intent myintent = new Intent(Home.this,MainActivity.class);
        startActivity(myintent);
        finish();
    }

    public void show_details(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String name = (String)dataSnapshot1.child("Name").getValue();
                    String age = (String)dataSnapshot1.child("Age").getValue();

                    Name.setText(name);
                    Age.setText(age);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Unknown Error while fetching data",Toast.LENGTH_LONG).show();
            }
        });



    }


    public void update_data(View view){

        Map<String, Object> update_user = new HashMap<String, Object>();

        update_user.put("Name",update_name.getText().toString());
        update_user.put("Age",update_age.getText().toString());
        update_user.put("Type",type_of_user);


        DatabaseReference myRef2 = database.getReference().child(type_of_user).child(user.getUid()).child(user.getUid());

        myRef2.updateChildren(update_user);

        show_details();

        update_name.setText("");
        update_age.setText("");

        Toast.makeText(getApplicationContext(),"Data updated successfully!!!",Toast.LENGTH_SHORT).show();
    }

}
