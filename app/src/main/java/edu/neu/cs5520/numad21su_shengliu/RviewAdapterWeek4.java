package edu.neu.cs5520.numad21su_shengliu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RviewAdapterWeek4 extends RecyclerView.Adapter<RviewHolderWeek4>{
    private final ArrayList<ItemCardWeek4> itemList;
    private ItemClickListenerWeek4 listener;
    Context context;

    @Override
    public RviewHolderWeek4 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_week4, parent, false);
        return new RviewHolderWeek4(context, view, listener);
    }

    //Constructor
    public RviewAdapterWeek4(ArrayList<ItemCardWeek4> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void setOnItemClickListener(ItemClickListenerWeek4 listener) {
        // binds ItemClickListener with ItemCard, as adapter is the middle man
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RviewHolderWeek4 holder, int position) {
        // 更新当前viewHolder
        // LayoutManager请求某个位置的item，便去ViewHolder的两个地方找。
        //  在Cache,就啥都不用调动，直接return.
        //  不在Cashe, 在Recycled Pool，调动onBindViewHolder（），就像我们APP，load过的数据，
        //  回看不用重新load，但数据有可能变动了，所以view要进行更新。
        ItemCardWeek4 currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getName());
        holder.itemURL.setText(currentItem.getURL());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
