package com.things.smartcam.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.things.smartcam.Add_Camera_Manually;
import com.things.smartcam.LanSearch;
import com.things.smartcam.R;

public class FullScreenDialog extends DialogFragment {

    public static String TAG = "FullScreenDialog";
    Dialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mDialog = getDialog();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.layout_full_screen_dialog, container);

        LinearLayout layout_scan_qr_code= (LinearLayout) view.findViewById(R.id.layout_scan_qr_code);
        layout_scan_qr_code.setOnClickListener(scanQRCode);
        LinearLayout layout_online_devices= (LinearLayout) view.findViewById(R.id.layout_online_devices);
        layout_online_devices.setOnClickListener(onlineDevices);

        LinearLayout layout_mannual_adding= (LinearLayout) view.findViewById(R.id.layout_mannual_adding);
        layout_mannual_adding.setOnClickListener(mannualAdding);
        LinearLayout layout_lan_search= (LinearLayout) view.findViewById(R.id.layout_lan_search);
        layout_lan_search.setOnClickListener(lanSearch);

        return view;
    }

    View.OnClickListener scanQRCode = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    View.OnClickListener onlineDevices = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    View.OnClickListener mannualAdding = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),Add_Camera_Manually.class);
            startActivity(intent);
            mDialog.dismiss();
        }
    };

    View.OnClickListener lanSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),LanSearch.class);
            startActivity(intent);
            mDialog.dismiss();
        }
    };

}