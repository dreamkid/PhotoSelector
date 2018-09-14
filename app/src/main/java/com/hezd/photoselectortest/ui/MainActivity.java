package com.hezd.photoselectortest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hezd.photoselector.model.MediaInfo;
import com.hezd.photoselector.ui.SelectDialog;
import com.hezd.photoselectortest.R;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.hezd.photoselector.ui.SelectDialog.RESULT_CODE_GALLERY;
import static com.hezd.photoselector.ui.SelectDialog.RESULT_CODE_PHOTO;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE_IMAGE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,SelectDialog.class),REQUEST_CODE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CODE_PHOTO) {
            showImageSize(data);
        }else if(resultCode == RESULT_CODE_GALLERY) {
            showImageSize(data);
        }
    }

    private void showImageSize(Intent data) {
        if(data!=null) {
            ArrayList<MediaInfo> images = (ArrayList<MediaInfo>) data.getSerializableExtra("images");
            Toast.makeText(this,"共"+images.size()+"张照片",Toast.LENGTH_SHORT).show();
        }
    }
}
