package edu.neu.cs5520.numad21su_shengliu;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinksListWeek4 extends AppCompatActivity implements GetInputWeek4.DialogListener{

    // Creating RecyclerView, Adapter, LayoutManager
    private ArrayList<ItemCardWeek4> itemList = new ArrayList<>();

    private RecyclerView recyclerView;
    private RviewAdapterWeek4 reviewAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private FloatingActionButton addButton;


    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links_list_week4);
        context = LinksListWeek4.this;

        init(savedInstanceState);
        addButton = findViewById(R.id.addButton);
        // for "add +" listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // int position = 0;
                openDialog(null, -1, false);
                // we add item only when user clicks on OK, not Cancel addItem(position);
            }
        });




        // Swipe left to remove
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinksListWeek4.this, "Delete an item", Toast.LENGTH_LONG).show();
                int position = viewHolder.getLayoutPosition();
                itemList.remove(position);

                reviewAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void openDialog(ItemCardWeek4 item, int position, boolean itemExist) {
        GetInputWeek4 dialog = new GetInputWeek4(item, position, itemExist);
        dialog.show(getSupportFragmentManager(), "first dialog");
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int size = itemList == null ? 0 : itemList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put name information into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", itemList.get(i).getName());
            // put URL into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", itemList.get(i).getURL());
        }
        super.onSaveInstanceState(outState);
    }


    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void createRecyclerView() {
        rLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        reviewAdapter = new RviewAdapterWeek4(itemList, context);

        // for "edit" listener
        ItemClickListenerWeek4 itemClickListener = new ItemClickListenerWeek4() {
            @Override
            public void onItemClick(int position) {
                //attributions bond to the item has been changed
                // 通过位置position，get到了itemList里面的itemcard,然后调用itemcard的onItemClick()
                itemList.get(position).onItemClick(position);

                openDialog(itemList.get(position), position, true);

                reviewAdapter.notifyItemChanged(position);
            }
        };
        reviewAdapter.setOnItemClickListener(itemClickListener);

        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

    private void initialItemData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (itemList == null || itemList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    itemList.add(new ItemCardWeek4(name, url));
                }
            }
        }
    }

    @Override
    public void transferInfo(String webName, String URL, int position, boolean itemExist) {
        if (itemExist) {
            itemList.get(position).setName(webName);
            itemList.get(position).setURL(URL);
            reviewAdapter.notifyItemChanged(position);
        } else {
            addItem(webName, URL);
        }
    }


    public void addItem(String webName, String URL) {
        // dialog---get user's input
        // create an item card object with user's info
        itemList.add(0, new ItemCardWeek4(webName, URL));
        Toast.makeText(LinksListWeek4.this, "Item added successfully", Toast.LENGTH_LONG).show();

        reviewAdapter.notifyItemInserted(0);
    }

}
