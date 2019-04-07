package com.example.mobilodev1;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Course> models;
    public  MyAdapter(final List<Course> l){
        models = new ArrayList<>(l);
        HashMap<String, Float> not = new HashMap<>();
        not.put("AA", 4.0f);
        not.put("BA", 3.5f);
        not.put("BB", 4.0f);
        not.put("CB", 2.5f);
        not.put("CC", 2.0f);
        not.put("DC", 1.5f);
        not.put("DD", 1.0f);
        not.put("FD", 0.5f);
        not.put("FF", 0.0f);
        not.put("F0", 0.0f);

        models.add(0, new Course("1. grade courses and ", "notes in letter form."));
        int j = 1;
        for(int i = 1; i < 5; i++){
            float sum = 0.0f;
            int count = 0;
            while(j < models.size() && models.get(j).getName().charAt(3) == i+48){
                sum += not.get(models.get(j).getNote());
                j++;
                count++;
            }
            models.add(j++,new Course("",String.format("GPA: %.2f",sum/count)));
            models.add(j++, new Course((i+1)+". grade courses and ", "notes in letter form."));
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = ((RecyclerView)parent.findViewById(R.id.simple_recyclerview)).getChildLayoutPosition(view);
                Course item = models.get(itemPosition);
                if(item.getName().contains("BLM")){
                    Intent i = new Intent(parent.getContext(), ShowCourseInfoActivity.class);
                    i.putExtra("data", item.getName());
                    parent.getContext().startActivity(i);
                }
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).bindData(models.get(position));
    }
    @Override
    public int getItemCount() {
        return models.size();
    }
    @Override
    public int getItemViewType(final int position) {
        return R.layout.show_notes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView note;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.course_name);
            note = itemView.findViewById(R.id.course_note);
        }

        public void bindData(final Course viewModel) {
            name.setText(viewModel.getName());
            note.setText(viewModel.getNote());
        }
    }
}
