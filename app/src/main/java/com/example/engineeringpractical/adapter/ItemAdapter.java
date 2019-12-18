package com.example.engineeringpractical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.engineeringpractical.R;
import com.example.engineeringpractical.mdoel.UserListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;


    List<UserListResponse.UserHits> hitsList;
    Context ctx;

    public ItemAdapter(List<UserListResponse.UserHits> hitsList, Context ctx) {
        this.hitsList = hitsList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(ctx).inflate(R.layout.item_loading, parent, false));
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_main, parent, false));
            default:
                return null;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == hitsList.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return hitsList == null ? 0 : hitsList.size();

    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtCreatedAt)
        TextView txtCreatedAt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
        }

        public void onBind(int position) {
            super.onBind(position);
            UserListResponse.UserHits item = hitsList.get(position);
            txtTitle.setText(item.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:MM a");
            try {
                if (item.getCreated_at() != null)
                    txtCreatedAt.setText(output.format(Objects.requireNonNull(dateFormat.parse(item.getCreated_at()))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UserListResponse.UserHits> getItems() {
        return hitsList;
    }

    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {
        }
    }

    public void addItems(List<UserListResponse.UserHits> postItems) {
        hitsList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        hitsList.add(new UserListResponse.UserHits());
        if (hitsList.size() != 0)
            notifyItemInserted(hitsList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = hitsList.size() - 1;
        UserListResponse.UserHits item = getItem(position);
        if (item != null) {
            if (hitsList.size() != 0) {
                hitsList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    public void clear() {
        hitsList.clear();
        notifyDataSetChanged();
    }

    UserListResponse.UserHits getItem(int position) {
        return hitsList.get(position);
    }

}
