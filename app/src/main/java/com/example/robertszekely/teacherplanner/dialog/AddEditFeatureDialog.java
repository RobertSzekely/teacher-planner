package com.example.robertszekely.teacherplanner.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.robertszekely.teacherplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robertszekely on 29/05/2017.
 */

public class AddEditFeatureDialog extends DialogFragment {
    @BindView(R.id.dialog_feature_body_field)
    EditText mBodyField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_new_feature, null))

                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddEditFeatureDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
