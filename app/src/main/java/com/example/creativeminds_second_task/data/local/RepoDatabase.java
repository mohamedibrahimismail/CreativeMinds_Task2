package com.example.creativeminds_second_task.data.local;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.creativeminds_second_task.data.remote.models.RepoModel;

@Database(entities = {RepoModel.class},version = 2,exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {

    private static RepoDatabase instance;

    public abstract RepoDao noteDao();

    public static synchronized RepoDatabase getInstance(Context context){

        if(instance==null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RepoDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private RepoDao noteDao;

        private PopulateDbAsyncTask(RepoDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          /*  noteDao.insert(new Favorite("Title 1","Description 1",1));
            noteDao.insert(new Favorite("Title 2","Description 2",2));
            noteDao.insert(new Favorite("Title 3","Description 3",3));
*/
            return null;

        }
    }

}
