package com.happy.archtectualcomponents;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ListAdapter<Item,ItemAdapter.ItemHolder> {
private Context mContext;
public itemOnClickListener listener;
    private static final String TAG = "ItemAdapter";

    public ItemAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Item> DIFF_CALLBACK=new DiffUtil.ItemCallback<Item>() {
        @Override
        public boolean areItemsTheSame(@NonNull Item item, @NonNull Item t1) {
            return item.getId()==t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item item, @NonNull Item t1) {
            return item.getTitle()==t1.getTitle()&&item.getDescription()==t1.getDescription()&&item.getPriority()==t1.getPriority();
        }
    }
;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout,parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.setDataToViews(getItem(position).getTitle(),getItem(position).getDescription(),
                String.valueOf(getItem(position).getPriority()));



    }
    public Item getItemAt(int p ){
        return getItem(p);

    }



    class ItemHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView title,description,priority;
        private Boolean completed;

        public ItemHolder(View itemView) {
            super(itemView);
            mView = itemView;
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            priority = itemView.findViewById(R.id.priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();

                    if (listener!=null&& pos!=RecyclerView.NO_POSITION){

                        listener.onItemClick(getItem(pos));
                    }

                }
            });
        }

        public void setDataToViews(String title,String desc,String priority){
            this.title.setText(title);
            this.description.setText(desc);
            this.priority.setText(priority);
        }





    }

    public interface itemOnClickListener{
        void onItemClick(Item item);
    }

    public void setOnItemClickListener(itemOnClickListener listener){
        this.listener=listener;
    }
}
