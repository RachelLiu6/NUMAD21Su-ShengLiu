package edu.neu.cs5520.numad21su_shengliu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RviewHolderWeek4 extends RecyclerView.ViewHolder{
    public TextView itemName;
    public TextView itemURL;

    public RviewHolderWeek4(Context context, View itemView, final ItemClickListenerWeek4 listener) {
        super(itemView);
        itemURL = itemView.findViewById(R.id.URL);
        itemName = itemView.findViewById(R.id.item_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });

        itemURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String siteUrl = itemURL.getText().toString();
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

}
