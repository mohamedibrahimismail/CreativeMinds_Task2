package com.example.creativeminds_second_task.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.creativeminds_second_task.R;
import com.example.creativeminds_second_task.adapters.EndlessRecyclerViewScrollListener;
import com.example.creativeminds_second_task.adapters.RecyclerViewAdapter;
import com.example.creativeminds_second_task.data.remote.models.RepoModel;

import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Communicator {

    ProgressBar progressbar;
    RecyclerView recyclerView;
    androidx.swiperefreshlayout.widget.SwipeRefreshLayout SwipeRefreshLayout;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    RecyclerViewAdapter recyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    RepoViewModel viewModel;
    View nodatatoshow;
    androidx.appcompat.widget.SearchView searchView;
    boolean flag = false;


    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener refreshListener = new androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            viewModel.setSwipRefresfer(true);
            // viewModel.setPage_list(1);
            viewModel.resetList();
            endlessRecyclerViewScrollListener.resetState();
            viewModel.getRepos(MainActivity.this);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        //getRepos();
        setupViewModel();
    }

    public void setupUI() {
        progressbar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.home_recyclerView);
        SwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        nodatatoshow = findViewById(R.id.nodatatoshow);
        searchView = findViewById(R.id.SearchView);
        setupSwipeRefresher();
        setup_RecyclerView();
        setup_searchView();
    }

    public void setup_searchView(){

        searchView.setQueryHint(getString(R.string.searchhere));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                recyclerViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                recyclerViewAdapter.getFilter().filter(query);
                if(!query.isEmpty()){
                    flag = true;
                }else {
                    flag =false;
                    endlessRecyclerViewScrollListener.resetState();
                }
                Log.e(TAG, "onQueryTextSubmit: "+query );
                return false;
            }
        });

    }

    public void setupSwipeRefresher() {
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        SwipeRefreshLayout.setOnRefreshListener(refreshListener);
    }

    public void setup_RecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setupEndOfList();

    }

    public void setupEndOfList() {
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.e("onLoadMore", "endlessRecyclerViewScrollListener: " + page);
                //          progressbar.setVisibility(View.VISIBLE);
//                viewModel.setPage_list(viewModel.getPage_list().getValue()+1);
                //  page_list++;
                //  getRepos();
                if(!flag) {
                    viewModel.reachEnd();
                    viewModel.getRepos(MainActivity.this);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(recyclerViewAdapter.getItemCount() - 1);
                }
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

//    private void getRepos() {
//        if (checkNetworkConnection()) {
//            //    loginbtn.setVisibility(View.GONE);
//            //    progressbar.setVisibility(View.VISIBLE);
//            GetDataService service = RetrofitClientInstance.getRetrofitInstance(this).create(GetDataService.class);
//            Call<List<RepoModel>> call = service.getRepos(TOKEN, page_list + "", "10");
//            call.enqueue(new Callback<List<RepoModel>>() {
//                @Override
//                public void onResponse(Call<List<RepoModel>> call, Response<List<RepoModel>> response) {
//                    if (response.code() == 200) {
//                        if (!response.body().isEmpty()) {
//                            if (page_list == 1) {
//                                setData(response.body());
//                            } else {
//                                addDataToList(response.body());
//                            }
//                        } else {
//                            SwipeRefreshLayout.setRefreshing(false);
//                            progressbar.setVisibility(View.GONE);
//                        }
//                    } else {
//                        SwipeRefreshLayout.setRefreshing(false);
//                        progressbar.setVisibility(View.GONE);
//                        //   Utilities.ShowToast(context, "----------------------");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<RepoModel>> call, Throwable t) {
////                    SwipeRefreshLayout.setRefreshing(false);
////                    Utilities.ShowToast(context, "----------------------");
////                    loginbtn.setVisibility(View.VISIBLE);
////                    progressbar.setVisibility(View.GONE);
//                }
//            });
//        } else {
//
//        }
//    }

    public void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RepoViewModel.class);
        viewModel.initalizeRepository(getApplication());

        viewModel.getSwipRefresfer().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                SwipeRefreshLayout.setRefreshing(aBoolean);
            }
        });
        viewModel.getProgressbar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressbar.setVisibility(View.VISIBLE);
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }
        });


        final LiveData<List<RepoModel>> observer = viewModel.getlocalRepo();
        observer.observe(this, new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(List<RepoModel> repoModels) {
                //Log.e(TAG, "testRoom: "+repoModels.size());
           //     setData(repoModels);
                viewModel.setRepo_list(repoModels);
//                if(repoModels.size()==0) {
//                    viewModel.setPage_list(repoModels.size() / 10);
//
//                }
                handelRemoteData();
                observer.removeObserver(this);
            }
        });

    }

    public void handelRemoteData(){
        viewModel.getRepos(this);
        viewModel.getrepo_list().observe(this, new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(List<RepoModel> repoModels) {
                if (viewModel.getPage_list().getValue() != null && !repoModels.isEmpty()) {
                    if (viewModel.getPage_list().getValue() == 1) {
                        setData(repoModels);
                        //   testRoom(repoModels);
                    } else {
                        addDataToList(repoModels);
                    }
                }else {

                }
            }
        });
    }



    public void setData(List<RepoModel> data) {

        recyclerViewAdapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(recyclerViewAdapter);
        //   viewModel.setSwipRefresfer(false);
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        nodatatoshow.setVisibility(View.GONE);

    }


    public void addDataToList(List<RepoModel> data) {

        recyclerViewAdapter.addDataToList(data);
        viewModel.setProgressbar(false);
        progressbar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        nodatatoshow.setVisibility(View.GONE);
    }

    private static final String TAG = "MainActivity";

    public void testRoom(){
      //  viewModel.deleteAllNote();
      //   viewModel.insert(repoModel);


    }

    public boolean checkNetworkConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            Toast.makeText(this, "please check network connection", Toast.LENGTH_SHORT).show();
            connected = false;
        }

        return connected;
    }

    @Override
    public void handleLongClicked(String repo_url, String owner_url) {
        setupAlertDialog(repo_url, owner_url);
    }

    public void setupAlertDialog(final String repo_url, final String owner_url) {
// setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select what you want to open :");

// add a list
        String[] animals = {"repo page", "repo owner page"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        opentBrowser(repo_url);
                        break;
                    case 1:
                        opentBrowser(owner_url);
                        break;

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void opentBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent browserChooserIntent = Intent.createChooser(browserIntent, "Choose browser of your choice");
        startActivity(browserChooserIntent);
    }


}