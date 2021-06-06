package edu.neu.cs5520.numad21su_shengliu;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private EditText editTextWebsiteName;
    private EditText editTextURL;
    private DialogListener listener;

    private boolean itemExist;
    // add: null item, edit: not null
    private ItemCard currentItem;
    // add: -1, edit: real position
    private int currentPosition;

    // Constructor
    public Dialog(ItemCard item, int position, boolean itemExist) {
        this.currentItem = item;
        this.currentPosition = position;
        this.itemExist = itemExist;
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        String title;

        // binds Dialog with its layout. The data of Dialog binds with the view components
        editTextWebsiteName = view.findViewById(R.id.editWebName);
        editTextURL = view.findViewById(R.id.editPassword);

        if (itemExist) {
            // edit
            editTextWebsiteName.setText(currentItem.getName());
            editTextURL.setText(currentItem.getURL());
            title = "Edit Website";
        } else {
            // add
            title = "Add a Website";
        }


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel then do nothing
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get info from dialog's components
                        String webName = editTextWebsiteName.getText().toString();
                        String URL = editTextURL.getText().toString();
                        listener.transferInfo(webName, URL, currentPosition, itemExist);
                    }
                });


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }
    public interface DialogListener {
        void transferInfo(String webName, String URL, int position, boolean itemExist);
    }
}
