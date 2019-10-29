package com.oop.edconnect;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassDetails extends Fragment {

    private String classID, className;
    private String temp;

    private TextView name_of_teacher;
    private CircleImageView teacher_image;
    private RecyclerView mrecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    private List<String> students, images;
    private List<String> classesClassUids;

    Classroom classroom;
    EnrolledClasses enrolledClasses;


    public ClassDetails(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.classmates,container, false);

        //Attached classmates layout file
        students = new LinkedList<>();
        images = new LinkedList<>();

        name_of_teacher = view.findViewById(R.id.name_of_teacher);
        teacher_image = view.findViewById(R.id.teacher_image);
        mrecyclerView = view.findViewById(R.id.classmates);

        get_class_details();

        load_students();

        mLayoutManager = new LinearLayoutManager(getContext());
        mrecyclerView.setHasFixedSize(true);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ClassmateAdapter(students, images);
        mrecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void get_class_details() {

        myRef.child("Classroom").child(classID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                classroom = dataSnapshot.getValue(Classroom.class);

                if(classroom != null){

                    DatabaseReference teacherRef = database.getReference().child("Users").child(classroom.getCreater());

                    teacherRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User teacher = dataSnapshot.getValue(User.class);
                            name_of_teacher.setText(teacher.getUserName());
                            Picasso.get().load(teacher.getImageUrl()).fit().into(teacher_image);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void load_students(){

        final DatabaseReference studentsRef = database.getReference().child("EnrolledClasses");

        final DatabaseReference classRef = database.getReference().child("EnrolledClasses");

        final DatabaseReference teacherRef = database.getReference().child(classID);

        final DatabaseReference teacherproRef = database.getReference().child("Users");

        final DatabaseReference imageRef = database.getReference().child("Users");

        students = new LinkedList<>();
        images = new LinkedList<>();

        classesClassUids = new LinkedList<>();

        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot KeyNode : dataSnapshot.getChildren()) {
                    temp = KeyNode.getKey();

                    if (temp != null) {

                        enrolledClasses = dataSnapshot.child(temp).getValue(EnrolledClasses.class);

                        classesClassUids = enrolledClasses.getClassUids();

                        if(classesClassUids == null){
                            Log.i("LOl","NULL");
                        }

                        else{

                            Log.i(enrolledClasses.getUserName(),enrolledClasses.getClassUids().toString());

                            if(enrolledClasses.getClassUids().indexOf(classID) != -1){
                                Log.i(enrolledClasses.getUserName(),"True");

                                students.add(enrolledClasses.getUserName());

                                imageRef.child(temp).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        User profile = dataSnapshot.getValue(User.class);

                                        if(profile != null && profile.getProfileType().equals("Student")){
                                            images.add(profile.getImageUrl());}

                                        if(profile!= null && profile.getProfileType().equals("Teacher")){
                                            students.remove(enrolledClasses.getUserName());
                                        }

                                        mAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                            else{
                                Log.i(enrolledClasses.getUserName(),"False");
                            }

                        }

                    }

                }


                mAdapter.notifyDataSetChanged();
                mrecyclerView.scrollToPosition(students.size()-1);
                Log.i("size",(students.get(0)));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}