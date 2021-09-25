package com.example.diabetes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment1_Register extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment1__register, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button register_button = getActivity().findViewById(R.id.get_started);
        register_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment2_Register fragment2 = new Fragment2_Register();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                        .replace(R.id.frame_layout,fragment2)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });
    }
}