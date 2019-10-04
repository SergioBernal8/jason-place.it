package com.example.json.place.it.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;
import com.example.json.place.it.databinding.PostListItemBinding;
import com.example.json.place.it.domain.model.realm.LocalPost;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<LocalPost> data;

    PostAdapter() {
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PostListItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.post_list_item, parent, false);
        return new PostViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.binding.setPost(data.get(position));
    }


    public void setData(List<LocalPost> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        PostListItemBinding binding;

        PostViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
