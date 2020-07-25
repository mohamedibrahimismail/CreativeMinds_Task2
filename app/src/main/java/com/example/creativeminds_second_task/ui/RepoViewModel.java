package com.example.creativeminds_second_task.ui;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.creativeminds_second_task.data.Repository;
import com.example.creativeminds_second_task.data.remote.GetDataService;
import com.example.creativeminds_second_task.data.remote.RetrofitClientInstance;
import com.example.creativeminds_second_task.data.remote.models.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.creativeminds_second_task.Constants.TOKEN;

public class RepoViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<RepoModel>> repo_list = new MutableLiveData<>();
    private MutableLiveData<Integer> page_list = new MutableLiveData<>();
    private MutableLiveData<Boolean> SwipRefresfer = new MutableLiveData<>(); ;
    private MutableLiveData<Boolean> Progressbar = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadMore = new MutableLiveData<>();


    private static final String TAG = "RepoViewModel";

    public RepoViewModel() {
        Log.e(TAG, "init View Model: --------------------");

//        repo_list = repository.getrepo_list();
        page_list.setValue(1);
        SwipRefresfer.setValue(true);
        Progressbar.setValue(true);


    }



    //------------------------------

    public void initalizeRepository(Application application){
        repository = new Repository(application);
    }


    public void reachEnd(){
        page_list.setValue(page_list.getValue()+1);
        setProgressbar(true);
    }

    public void resetList(){
        page_list.setValue(1);
        setSwipRefresfer(true);
        repository.deleteall();
    }

    public MutableLiveData<Boolean> getSwipRefresfer() {
        return SwipRefresfer;
    }

    public MutableLiveData<Boolean> getProgressbar() {
        return Progressbar;
    }

    public void setSwipRefresfer(Boolean swipRefresfer) {
        SwipRefresfer.setValue(swipRefresfer);
    }

    public void setProgressbar(Boolean progressbar) {
        Progressbar.setValue(progressbar);
    }

    public LiveData<Integer> getPage_list() {
        return page_list;
    }

    public void setPage_list(int page_list) {
        this.page_list.setValue(page_list);
    }

    public void getRepos(Context context) {

//        Log.e(TAG, "getRepos: "+repository.getAllNotes().getValue().size()+"" );



        if (checkNetworkConnection(context)) {
            //    loginbtn.setVisibility(View.GONE);
            //    progressbar.setVisibility(View.VISIBLE);
            GetDataService service = RetrofitClientInstance.getRetrofitInstance(context).create(GetDataService.class);
            Call<List<RepoModel>> call = service.getRepos(TOKEN, page_list.getValue() + "", "14");
            call.enqueue(new Callback<List<RepoModel>>() {
                @Override
                public void onResponse(Call<List<RepoModel>> call, Response<List<RepoModel>> response) {
                    if (response.code() == 200) {
                        if (!response.body().isEmpty()) {
                            if (page_list.getValue() == 1) {
                                //    setData(response.body());
                                repo_list.setValue(response.body());
                                insert(response.body());
   //                             Log.e(TAG, "getRepos: "+repository.getNote(response.body().get(0).getId().toString()).getValue().getFullName()+"" );
                                SwipRefresfer.setValue(false);
                                Progressbar.setValue(false);
                            } else {
                                //       addDataToList(response.body());
                                repo_list.getValue().addAll(response.body());
                                insert(response.body());
                                SwipRefresfer.setValue(false);
                                Progressbar.setValue(false);
                            }
                        } else {
//                            SwipeRefreshLayout.setRefreshing(false);
//                            progressbar.setVisibility(View.GONE);
                            SwipRefresfer.setValue(false);
                            Progressbar.setValue(false);
                        }
                    } else {
//                        SwipeRefreshLayout.setRefreshing(false);
//                        progressbar.setVisibility(View.GONE);
                        SwipRefresfer.setValue(false);
                        Progressbar.setValue(false);
                        //   Utilities.ShowToast(context, "----------------------");
                    }
                }

                @Override
                public void onFailure(Call<List<RepoModel>> call, Throwable t) {
                    SwipRefresfer.setValue(false);
                    Progressbar.setValue(false);

//                    SwipeRefreshLayout.setRefreshing(false);
//                    Utilities.ShowToast(context, "----------------------");
//                    loginbtn.setVisibility(View.VISIBLE);
//                    progressbar.setVisibility(View.GONE);
                }
            });
        } else {

            setSwipRefresfer(false);
            setProgressbar(false);
        }

    }

    public boolean checkNetworkConnection(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            Toast.makeText(context, "please check network connection", Toast.LENGTH_SHORT).show();
            connected = false;
        }

        return connected;
    }

    //----------------------------------------

    public void insert(List<RepoModel> notes) {
        repository.insert(notes);
    }


    public void update(RepoModel note) {
        repository.update(note);
    }

    public void delete(RepoModel note) {
        repository.delete(note);
    }

    public void deleteAllNote() {
        repository.deleteall();
    }

    public LiveData<List<RepoModel>> getrepo_list() {
        return repo_list;
    }

    public void setRepo_list(List<RepoModel> repo_list) {
        this.repo_list.setValue(repo_list);
    }

    public LiveData<List<RepoModel>> getlocalRepo() {
        return repository.getAllNotes();
    }

    public LiveData<RepoModel> getFavoriteMovie(String id) {
        return repository.getNote(id);
    }

}
