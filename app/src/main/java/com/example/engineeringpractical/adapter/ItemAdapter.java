package com.example.engineeringpractical.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.engineeringpractical.mdoel.UserListResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    List<UserListResponse.UserHits> hitsList;
    Context ctx;

    public ItemAdapter(List<UserListResponse.UserHits> hitsList, Context ctx) {
        this.hitsList = hitsList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case VIEW_TYPE_LOADING:

        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
