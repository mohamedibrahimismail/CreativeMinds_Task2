package com.example.creativeminds_second_task.data.remote;


import com.example.creativeminds_second_task.data.remote.models.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {


    @GET("/users/square/repos")
    Call<List<RepoModel>> getRepos(
            @Query("access_token") String access_token,
            @Query("page") String page,
            @Query("per_page") String per_page);
}
