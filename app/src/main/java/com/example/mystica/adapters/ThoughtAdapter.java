package com.example.mystica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystica.R;
import com.example.mystica.models.Thought;

import java.util.List;

public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtAdapter.ThoughtViewHolder> {

    private final List<Thought> thoughtList;

    public ThoughtAdapter(Context context, List<Thought> thoughtList) {
        this.thoughtList = thoughtList;
    }

    @NonNull
    @Override
    public ThoughtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thought, parent, false);
        return new ThoughtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThoughtViewHolder holder, int position) {
        Thought thought = thoughtList.get(position);
        holder.tvThoughtMessage.setText(thought.getMessage());
        holder.tvThoughtDate.setText(thought.getDate());
    }

    @Override
    public int getItemCount() {
        return thoughtList.size();
    }

    public static class ThoughtViewHolder extends RecyclerView.ViewHolder {
        TextView tvThoughtMessage, tvThoughtDate;

        public ThoughtViewHolder(@NonNull View itemView) {
            super(itemView);
            tvThoughtMessage = itemView.findViewById(R.id.tvThoughtMessage);
            tvThoughtDate = itemView.findViewById(R.id.tvThoughtDate);
        }
    }
}
