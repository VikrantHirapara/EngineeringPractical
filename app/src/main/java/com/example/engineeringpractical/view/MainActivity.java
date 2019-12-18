package com.example.engineeringpractical.view;

import android.content.Context;
import android.os.Bundle;

import com.example.engineeringpractical.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lstUsers)
    RecyclerView lstUsers;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


}
