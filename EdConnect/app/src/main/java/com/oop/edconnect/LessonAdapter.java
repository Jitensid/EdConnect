package com.oop.edconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    private List<Lesson> files;

    public LessonAdapter(List<Lesson> files) {
        this.files = files;
    }

    @NonNull
    @Override
    public LessonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final LessonAdapter.ViewHolder holder, final int position) {


                holder.title.setText(files.get(position).getTitle());

                holder.content.setText(files.get(position).getContent());

                holder.lesson_cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent myintent = new Intent(holder.context,File_display.class);

                        Bundle extras = new Bundle();

                        extras.putString("Name",files.get(position).getFilename());
                        extras.putString("DownloadUrl",files.get(position).getFileurl());

                        myintent.putExtras(extras);

                        holder.context.startActivity(myintent);

                    }
                });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       private Context context;

       private TextView title, content;

       private CardView lesson_cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.display_title);

            content = itemView.findViewById(R.id.display_content);

            lesson_cardview = itemView.findViewById(R.id.lesson_cardview);

            context = itemView.getContext();

        }
    }
}
