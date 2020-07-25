package com.example.creativeminds_second_task.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.creativeminds_second_task.data.local.RepoDao;
import com.example.creativeminds_second_task.data.local.RepoDatabase;
import com.example.creativeminds_second_task.data.remote.models.RepoModel;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private RepoDao noteDao;
    private LiveData<List<RepoModel>> allNotes;
    private LiveData<RepoModel> getFavoriteMovie;

    public Repository(Application application) {
        RepoDatabase database = RepoDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();


    }


    public void insert(List<RepoModel> notes) {
        new InsertNoteAsynTask(noteDao).execute(notes);
    }

    public void update(RepoModel note) {
        new UpdateNoteAsynTask(noteDao).execute(note);
    }

    public void delete(RepoModel note) {
        new DeleteNoteAsynTask(noteDao).execute(note);
    }

    public void deleteall() {
        new DeleteAllNoteAsynTask(noteDao).execute();
    }

    public LiveData<List<RepoModel>> getAllNotes() {
        return allNotes;
    }


    public LiveData<RepoModel> getNote(String id) {
        getFavoriteMovie = noteDao.getNote(id);
        return getFavoriteMovie;

    }


    private static class InsertNoteAsynTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao noteDao;

        private InsertNoteAsynTask(RepoDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(List<RepoModel>... notes) {
            Log.e("Background Task", "doInBackground: "+notes[0].size() );
            for(RepoModel repoModel:notes[0])
            {noteDao.insert(repoModel);}
            return null;
        }
    }


    private static class UpdateNoteAsynTask extends AsyncTask<RepoModel, Void, Void> {

        private RepoDao noteDao;

        private UpdateNoteAsynTask(RepoDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(RepoModel... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsynTask extends AsyncTask<RepoModel, Void, Void> {

        private RepoDao noteDao;

        private DeleteNoteAsynTask(RepoDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(RepoModel... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNoteAsynTask extends AsyncTask<Void, Void, Void> {

        private RepoDao noteDao;

        private DeleteAllNoteAsynTask(RepoDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

/*
    private static class getFavoriteAsynTask extends AsyncTask<Integer,Void,Void>{

        private FavoriteDao noteDao;

        private getFavoriteAsynTask(FavoriteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... notes) {
            noteDao.getNote(notes[0]);
            return null;
        }
    }
*/
    /*
    private static class PopulatedAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private PopulatedAsyncTask(NoteDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Favorite("Title 1","Description 1 ",1));
            noteDao.insert(new Favorite("Title 2","Description 2 ",2));
            noteDao.insert(new Favorite("Title 3","Description 3 ",3));

            return null;
        }
    }
*/
}
