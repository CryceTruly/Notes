package com.happy.archtectualcomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
private ItemViewModel itemViewModel;
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
public static final int ADD_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        final ItemAdapter adapter=new ItemAdapter(getBaseContext());
        recyclerView.setAdapter(adapter);
        itemViewModel= ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
               adapter.setData(items);
            }
        });
new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
     itemViewModel.delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
        Toast.makeText(MainActivity.this, "Item removed", Toast.LENGTH_SHORT).show();

    }
}).attachToRecyclerView(recyclerView);
    }


    public void goNext(View view) {
        Intent i=new Intent(getBaseContext(),NewItemActivity.class);
        startActivityForResult(i,ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ADD_REQUEST_CODE && resultCode==RESULT_OK){

                String title=data.getStringExtra(NewItemActivity.EXTRA_TITLE);
                String description=data.getStringExtra(NewItemActivity.EXTRA_DESCRIPTION);

                int priority=data.getIntExtra(NewItemActivity.EXTRA_PRIORITY,0);

                Item item=new Item(title,description,false,priority);

                itemViewModel.insert(item);
                Snackbar.make(recyclerView,"Item saved",Snackbar.LENGTH_LONG).show();



            }else{
                Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.delAll:
                itemViewModel.deleteAllItems();
                Toast.makeText(this, "All away", Toast.LENGTH_SHORT).show();
        return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
