package com.happy.archtectualcomponents;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
private List<Item> liveData=new ArrayList<>();
private Context mContext;
    private static final String TAG = "ItemAdapter";
    public ItemAdapter() {
        this.liveData = liveData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout,parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.setDataToViews(liveData.get(position).getTitle(),liveData.get(position).getDescription(),
                String.valueOf(liveData.get(position).getPriority()));
       holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, liveData.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return liveData.size();
    }

    public void setData(List<Item> items){
        this.liveData=items;
        notifyDataSetChanged();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView title,description,priority;
        private Boolean completed;

        public ItemHolder(View itemView) {
            super(itemView);
            mView = itemView;
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            priority=itemView.findViewById(R.id.priority);
        }

        public void setDataToViews(String title,String desc,String priority){
            this.title.setText(title);
            this.description.setText(desc);
            this.priority.setText(priority);
        }


    }
}
