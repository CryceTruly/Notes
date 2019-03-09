package com.happy.archtectualcomponents;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        final ItemAdapter adapter=new ItemAdapter();
        recyclerView.setAdapter(adapter);
        itemViewModel= ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
               adapter.setData(items);
            }
        });

    }


    public void goNext(View view) {
        Intent i=new Intent(getBaseContext(),NewItemActivity.class);
        startActivityForResult(i,ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ADD_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                String title=data.getStringExtra(NewItemActivity.DESCRIPTION);
                String description=data.getStringExtra(NewItemActivity.DESCRIPTION);
                int priority=data.getIntExtra(NewItemActivity.PRIORITY,0);

                Item item=new Item(title,description,false,priority);

                itemViewModel.insert(item);
                Snackbar.make(recyclerView,"Item saved",Snackbar.LENGTH_LONG).show();



            }else{
                Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
