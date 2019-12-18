package com.example.engineeringpractical.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.engineeringpractical.R;
import com.example.engineeringpractical.Util.EndlessRecyclerOnScrollListener;
import com.example.engineeringpractical.adapter.ItemAdapter;
import com.example.engineeringpractical.api.APIClient;
import com.example.engineeringpractical.api.ApiInterface;
import com.example.engineeringpractical.mdoel.UserListResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.lstUsers)
    RecyclerView lstUsers;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.progressBottom)
    ProgressBar progressBottom;

    @BindView(R.id.progress)
    ProgressBar progress;

    Context ctx = this;

    ItemAdapter adapter;
    List<UserListResponse.UserHits> hitsList;

    private int currentPage = 1;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    static int selectCount = 0;
    TextView notifyCount;
    LinearLayoutManager layoutManager;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeRefresh.setOnRefreshListener(this);
        lstUsers.setHasFixedSize(true);
        lstUsers.setLayoutManager(getLinearLayoutManager());

        adapter = new ItemAdapter(new ArrayList<>(), ctx);
        lstUsers.setAdapter(adapter);
        apiCall(1);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(getLinearLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                progressBottom.setVisibility(View.VISIBLE);
                currentPage++;
                apiCall(current_page);
            }
        };
        lstUsers.addOnScrollListener(endlessRecyclerOnScrollListener);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(lstUsers.getContext(),
                layoutManager.getOrientation());
        lstUsers.addItemDecoration(dividerItemDecoration);

    }

    public LinearLayoutManager getLinearLayoutManager() {
        if (layoutManager == null) {
            // use a linear layout manager
            layoutManager = new LinearLayoutManager(this);
        }
        return layoutManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.mainmenu, menu);
        notifyCount = (TextView) menu.findItem(R.id.notifyCount).getActionView();

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        notifyCount.setLayoutParams(layoutParams);
        notifyCount.setTextSize(20);
        notifyCount.setText("Total : " + selectCount + "  ");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        setNotifCount(0);
        endlessRecyclerOnScrollListener.reset();
        itemCount = 0;
        currentPage = 1;
        isLastPage = false;
        adapter.clear();
        apiCall(1);
    }

    private void apiCall(int page) {
        ApiInterface service = APIClient.getRetrofitInstance().create(ApiInterface.class);

        Call<UserListResponse> call = service.getUsersList("story", page);

        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                //adapter.removeLoading();
                progress.setVisibility(View.GONE);
                progressBottom.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                if (response.body() != null) {

                    totalPage = response.body().getNbPages();
                    adapter.addItems(response.body().getHits());
                    setNotifCount(adapter.getItemCount());
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                adapter.removeLoading();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNotifCount(int count) {
        selectCount = count;
        invalidateOptionsMenu();
    }


}
