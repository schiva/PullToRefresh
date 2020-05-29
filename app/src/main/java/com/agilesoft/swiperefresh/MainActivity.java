package com.agilesoft.swiperefresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<MainData> arrayList;
    private ReccyclerViewAdapter reccyclerViewAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private int seqno =0 ;
    private String TAG="TEST";
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView  = findViewById(R.id.recyclerView);
        progressBar   = findViewById(R.id.progress);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        reccyclerViewAdapter = new ReccyclerViewAdapter(arrayList);
        recyclerView.setAdapter(reccyclerViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                Log.i(TAG, "onScrollStateChanged: .....1");
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                    Log.i(TAG, "onScrollStateChanged: isScrolling=true");
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems   = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                Log.i(TAG, String.format("Current:%d scrollOut:%d total:%d", currentItems, scrollOutItems, totalItems));
                if(isScrolling && ((currentItems + scrollOutItems == totalItems))){
                    isScrolling = false;
                    fetchData();
                }
            }
        });


        addData(12);
        reccyclerViewAdapter.notifyDataSetChanged();

    }

    private void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        addData(12);
        reccyclerViewAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    protected void addData(int count){

        for (int i=1; i <=count; i++) {
            String name = String.format("%d%s", seqno, " 홍길동");
            String contents = "내용 샘플";

            MainData mainData = new MainData(R.mipmap.ic_launcher, name, contents);
            arrayList.add(mainData);
            seqno++;
        }
    }



}
