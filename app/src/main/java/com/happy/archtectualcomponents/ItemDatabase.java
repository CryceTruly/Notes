package com.happy.archtectualcomponents;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Item.class},version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    private static ItemDatabase instance;

    public abstract ItemDao itemDao();

    public static synchronized ItemDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, "item")
                    .fallbackToDestructiveMigration().addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateItemsAsysncTask(instance).execute();
        }
    };


    private static class populateItemsAsysncTask extends AsyncTask<Void, Void, Void> {
        private ItemDao itemDao;

        public populateItemsAsysncTask(ItemDatabase db) {
            this.itemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.insert(new Item("Title One","This is item one",false,1));
            itemDao.insert(new Item("Title Two","This is item two",false,2));
            itemDao.insert(new Item("Title Three","This is item three",true,3));
            itemDao.insert(new Item("Title Four","This is item four",false,4));
            return null;
        }
    }
}