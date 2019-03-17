package com.happy.archtectualcomponents;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NewItemActivity extends AppCompatActivity {
    private static final String TAG = "NewItemActivity";
    private TextInputEditText title,desc;
    NumberPicker priority;
    public static final String EXTRA_TITLE=
            "com.happy.archtectualcomponents.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION=
            "com.happy.archtectualcomponents.DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.happy.archtectualcomponents.EXTRA_PRIORITY";


    public static final String EXTRA_ID=
            "com.happy.archtectualcomponents.EXTRA_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        Intent intent=getIntent();
        if (intent.hasExtra(EXTRA_ID)) {

            getSupportActionBar().setTitle("Edit Item");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
priority.setValue(intent.getIntExtra(EXTRA_PRIORITY,0));

        }else{

            getSupportActionBar().setTitle("Create New");

        }

    }

    private void initViews() {
        priority=findViewById(R.id.number_picker);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.body);
        priority.setMinValue(1);
        priority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
    finish();
        }else{
            save();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        Log.d(TAG, "save: ");

      String  tit=title.getText().toString().trim();

        String d=desc.getText().toString().trim();
        int p=priority.getValue();

        if(tit.isEmpty() || d.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
                ;
        validateData(tit,d,p);
 Intent intent=new Intent();
 intent.putExtra(EXTRA_TITLE,tit);

intent.putExtra(EXTRA_DESCRIPTION,d);
intent.putExtra(EXTRA_PRIORITY,p);

int id=getIntent().getIntExtra(EXTRA_ID,-1);
if(id!=-1)
{
    intent.putExtra(EXTRA_ID,id);
}
setResult(RESULT_OK,intent);
finish();




    }

    private void validateData(String title, String desc, int proirity) {
        if(title.isEmpty() || desc.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        return;
        }

    }
}
