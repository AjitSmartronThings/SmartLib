package com.things.smartcam.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.things.smartcam.R;


public class EarnFragment extends Fragment {

    private static EarnFragment INSTANCE = null;


    public EarnFragment() {
        // Required empty public constructor
    }

    public static EarnFragment getInstace() {
        if (INSTANCE == null) INSTANCE = new EarnFragment();
        return INSTANCE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earn, container, false);
    }

}
