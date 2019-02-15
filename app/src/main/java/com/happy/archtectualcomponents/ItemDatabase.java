package com.happy.archtectualcomponents;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Item.class},version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    private static ItemDatabase instance;
    public abstract ItemDao itemDao();
    public  static synchronized ItemDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),ItemDatabase.class,"item").fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    }
}
