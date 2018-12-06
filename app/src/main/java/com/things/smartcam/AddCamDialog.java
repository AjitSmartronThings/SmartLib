package com.things.smartcam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class AddCamDialog extends AppCompatActivity {

    LinearLayout layout_lan_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cam_dialog);
        layout_lan_search = (LinearLayout) findViewById(R.id.layout_lan_search);

        layout_lan_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCamDialog.this,MainActivity.class);
            }
        });
    }
}
