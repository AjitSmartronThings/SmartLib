package com.things.smartcam.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.things.smartcam.R;


public class ReferFragment extends Fragment {

    private static ReferFragment INSTANCE = null;

    public ReferFragment() {
        // Required empty public constructor
    }

    public static ReferFragment getInstace() {
        if (INSTANCE == null) INSTANCE = new ReferFragment();
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_refer, container, false);
    }

}
