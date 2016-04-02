package com.way.recycleview.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.way.recycleview.R;
import com.way.recycleview.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by way on 16/4/1.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private OnItemClickListener mListener;
    public MyAdapter(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = Item.ITEMS[position];

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = item.getHeight();
        holder.itemView.setLayoutParams(lp);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.imageView, holder.itemView.getResources().getString(item.getAuthor()));
        Log.i("way", "item = " + item);
        //Glide.with(holder.imageView.getContext()).load(item.getPhoto()).into(holder.imageView);
        holder.imageView.setImageResource(item.getPhoto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Item.ITEMS.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }

    public interface OnItemClickListener {
        void onClick(MyViewHolder holder, int position);
    }

}
