package com.oop.edconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

public class LessonView extends Fragment {

    private String classID, className;

    private List<Lesson> Lessondataset;

    private RecyclerView lesson_recycler_view;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private FloatingActionButton upload;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public LessonView(String classID, String className) {
        this.classID = classID;
        this.className = className;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lesson_display, container, false);

        lesson_recycler_view = view.findViewById(R.id.lesson_recycler_view);

        upload = view.findViewById(R.id.upload);

        Lessondataset = new LinkedList<>();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(getContext(),ClassLessonActivity.class);
                Bundle extras = new Bundle();
                extras.putString("classId",classID);
                extras.putString("classname",className);

                myintent.putExtras(extras);
                startActivity(myintent);
            }
        });

        fetch_all_Lessons();

        mLayoutManager = new LinearLayoutManager(getContext());
        lesson_recycler_view.setHasFixedSize(true);
        lesson_recycler_view.setLayoutManager(mLayoutManager);

        mAdapter = new LessonAdapter(Lessondataset);

        lesson_recycler_view.setAdapter(mAdapter);

        return view;
    }


        public void fetch_all_Lessons(){
        
            myRef.child("Lessons").child(classID).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Lesson tempLesson = dataSnapshot.getValue(Lesson.class);

                    Lessondataset.add(0,tempLesson);

                    mAdapter.notifyDataSetChanged();

                    lesson_recycler_view.scrollToPosition(0);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

}
