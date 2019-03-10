package com.happy.archtectualcomponents;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ItemRepository {
    private LiveData<List<Item>> allItems;
    private ItemDao itemDao;


    public ItemRepository(Application application){
        ItemDatabase itemDatabase=ItemDatabase.getInstance(application);
        itemDao=itemDatabase.itemDao();
        allItems=itemDao.getAllItems();
    }

    public void insert(Item item){
new InserItemAsyncTask(itemDao).execute(item);
    }
    public void update(Item item){
new UpdateItemAsyncTask(itemDao).execute(item);
    }
    public void delete(Item item){
new DeleteItemsyncTask(itemDao).execute(item);
    }
    public void deleteAllNotes(){
        new DeleteAllItemsyncTask(itemDao).execute();

    }

    public LiveData<List<Item>> getAllItems(){
        return allItems;
    }


    private static class InserItemAsyncTask extends AsyncTask<Item,Void,Void>{
        ItemDao itemDao;
        public InserItemAsyncTask(ItemDao itemDao) {
            this.itemDao=itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.insert(items[0]);
            return null;
        }
    }
    private static class UpdateItemAsyncTask extends AsyncTask<Item,Void,Void>{
        ItemDao itemDao;
        public UpdateItemAsyncTask(ItemDao itemDao) {
            this.itemDao=itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteItemsyncTask extends AsyncTask<Item,Void,Void>{
        ItemDao itemDao;
        public DeleteItemsyncTask(ItemDao itemDao) {
            this.itemDao=itemDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllItemsyncTask extends AsyncTask<Void,Void,Void>{
        ItemDao itemDao;
        public DeleteAllItemsyncTask(ItemDao itemDao) {
            this.itemDao=itemDao;
        }



        @Override
        protected Void doInBackground(Void... voids) {
            itemDao.deleteAllItems();
            return null;
        }
    }
}
