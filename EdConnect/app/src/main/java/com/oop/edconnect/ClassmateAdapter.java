package com.oop.edconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassmateAdapter extends RecyclerView.Adapter<ClassmateAdapter.ViewHolder> {

    private List<String> students, images;


    public ClassmateAdapter( List<String> students_data, List<String> images_data) {
        this.students = students_data;
        this.images = images_data;
    }

    @NonNull
    @Override
    public ClassmateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(students.get(position));


        try {
            Picasso.get().load(images.get(position)).fit().into(holder.image);
        }
        catch(Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private CircleImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);

        }
    }
}
