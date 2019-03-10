package com.happy.archtectualcomponents;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
//    Defines db operations
    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM items")
    void deleteAllItems();
    @Query("SELECT * FROM items ORDER BY priority DESC")
    LiveData<List<Item>> getAllItems();
}
