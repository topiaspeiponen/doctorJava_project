package com.example.doctorjava_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.doctorjava_project.R;

/**
 * The type Example dialog.
 */
public class ExampleDialog extends AppCompatDialogFragment  {
    private EditText editTextStepLenght;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle("Done")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String lenght = editTextStepLenght.getText().toString();
                        listener.applyTexts(lenght);
                    }
                });
        editTextStepLenght = view.findViewById(R.id.edit_Lenght);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener ) context ;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement ExampleDialogListener");
        }
    }

    /**
     * The interface Example dialog listener.
     */
    public interface ExampleDialogListener{
        /**
         * Apply texts.
         *
         * @param lenght the lenght
         */
        void applyTexts(String lenght);
    }
}
