package com.happy.archtectualcomponents;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
       itemRepository=new ItemRepository(application);
       allItems=itemRepository.getAllItems();
    }

    public void insert(Item item){
        itemRepository.insert(item);
    }
    public void update(Item item){
        itemRepository.update(item);
    }
    public void delete(Item item){
        itemRepository.delete(item);
    }
    public void deleteAllItems(){
        itemRepository.deleteAllNotes();
    }
    public LiveData<List<Item>> getAllItems(){
        return itemRepository.getAllItems();
    }

}
