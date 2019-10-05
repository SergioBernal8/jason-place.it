package com.example.json.place.it.ui.post.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;
import com.example.json.place.it.databinding.CommentListItemBinding;
import com.example.json.place.it.domain.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> data;

    public CommentAdapter() {
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CommentListItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.setComment(data.get(position));
    }

    public void setData(List<Comment> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentListItemBinding binding;

        CommentViewHolder(CommentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
