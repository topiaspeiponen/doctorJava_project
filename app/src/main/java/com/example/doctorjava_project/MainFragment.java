package com.example.doctorjava_project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

/**
 * @author Created by Topias on 25/04/2019.
 * @version 1.0
 * @since 1.0
 */

/**
 * Inflates the layout (renders it in the memory), which makes it appear
 * in the screen view.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    TextView stepCounter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        stepCounter = getView().findViewById(R.id.stepCounter);
        Button stepAddButton = getView().findViewById(R.id.stepAddButton);

        stepAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stepCounter.setText(Integer.toString(Integer.parseInt(stepCounter.getText().toString())+5));
                Log.d(TAG, "+5 steps added!");
            }
        });
    }

}
