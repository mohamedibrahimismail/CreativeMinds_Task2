package com.example.creativeminds_second_task.data.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.creativeminds_second_task.data.remote.models.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepoModel note);

    @Update
    void update(RepoModel note);

    @Delete
    void delete(RepoModel note);

    @Query("DELETE from repo_table")
    void deleteAllNotes();

    @Query("select * from repo_table")
    LiveData<List<RepoModel>> getAllNotes();

    @Query("select * from repo_table where id = :id")
    LiveData<RepoModel> getNote(String id);


}
