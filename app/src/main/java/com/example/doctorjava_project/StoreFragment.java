package com.example.doctorjava_project;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.0
 * @since 1.0
 */

/**
 * Inflates the layout (renders it in the memory), which makes it appear
 * in the screen view.
 */
public class StoreFragment extends Fragment implements ExampleDialog.ExampleDialogListener {
    private Button infoButton;
    private TextView textviewLenght;
    private TextView infoText;
    private Button lenghtButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, null);


            Button infoButton = (Button)view.findViewById(R.id.infoButton);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });

                    return view;


    }

    @Override
    public void onResume(){
        super.onResume();
        infoText = getActivity().findViewById(R.id.textView3);
        infoText.setMovementMethod(new ScrollingMovementMethod());
    }

    public void openDialog(){
        StoreDialog storeDialog = new StoreDialog();
        storeDialog.show(getFragmentManager(),"Example");
    }
    public void openDialoglenght(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getFragmentManager(),"example dialog");
    }

    public void applyTexts(String lenght) {
        textviewLenght.setText(lenght);
    }





}
