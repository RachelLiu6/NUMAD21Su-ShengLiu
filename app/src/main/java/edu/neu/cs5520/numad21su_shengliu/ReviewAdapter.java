package edu.neu.cs5520.numad21su_shengliu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder>{
    private final ArrayList<ItemCard> itemList;
    private ItemClickListener listener;
    Context context;

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ReviewHolder(view, listener);
    }

    //Constructor
    public ReviewAdapter(ArrayList<ItemCard> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        // binds ItemClickListener with ItemCard, as adapter is the middle man
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        // 更新当前viewHolder
        // LayoutManager请求某个位置的item，便去ViewHolder的两个地方找。
        //  在Cache,就啥都不用调动，直接return.
        //  不在Cashe, 在Recycled Pool，调动onBindViewHolder（），就像我们APP，load过的数据，
        //  回看不用重新load，但数据有可能变动了，所以view要进行更新。
        ItemCard currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getName());
        holder.itemURL.setText(currentItem.getURL());

        holder.itemURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String siteUrl = currentItem.getURL();
                if (URLUtil.isValidUrl(siteUrl)) {
                    // url valid, jump to URl
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(siteUrl));
                    context.startActivity(browserIntent);
                } else {
                    // url invalid, notify user
                    Toast.makeText(context, "This URL is invalid, please re-enter the url!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}