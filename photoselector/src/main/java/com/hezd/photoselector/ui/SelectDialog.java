package com.hezd.photoselector.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.hezd.photoselector.R;
import com.hezd.photoselector.model.MediaInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class SelectDialog extends Activity implements View.OnClickListener {

    /**
     * 从摄像头获取
     */
    public static final int REQUEST_CODE_PHOTO = 0x1;
    public static final int RESULT_CODE_PHOTO = 0x2;
    /**
     * 从相册选择
     */
    public static final int REQUEST_CODE_GALLERY = 0x3;
    public static final int RESULT_CODE_GALLERY = 0x4;
    private LinearLayout mParentLayout;
    private TextView mTakePhoto;
    private TextView mGalleryTv;
    private TextView mCancelTv;
    private String tempImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        getViews();
        setViews();
        setListenres();
    }

    private void getViews() {
        mParentLayout = findViewById(R.id.parent);
        mTakePhoto = findViewById(R.id.tv_take_photo);
        mGalleryTv = findViewById(R.id.tv_album);
        mCancelTv = findViewById(R.id.tv_cancel);
    }

    private void setViews() {

    }

    private void setListenres() {
        mParentLayout.setOnClickListener(this);
        mTakePhoto.setOnClickListener(this);
        mGalleryTv.setOnClickListener(this);
        mCancelTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id== R.id.parent ||id == R.id.tv_cancel) {
            finish();
        }else if(id == R.id.tv_take_photo) {
            takePhotoFromCamera();
        }else if(id == R.id.tv_album) {
            takePhotoFromGallery();
        }

    }

    private void takePhotoFromGallery() {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivityForResult(intent,REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            ArrayList<MediaInfo> images = new ArrayList<>();
            MediaInfo mediaInfo = new MediaInfo();
            mediaInfo.setAssetpath(tempImagePath);
            images.add(mediaInfo);
            intent.putExtra("images",images);
            setResult(RESULT_CODE_PHOTO,intent);
            finish();
        }else if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_CODE_GALLERY) {
            setResult(RESULT_CODE_GALLERY,data);
            finish();
        }
    }

    private void takePhotoFromCamera() {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this,"没有sd卡",Toast.LENGTH_SHORT).show();
            return;
        }

        String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        cacheDir = cacheDir.endsWith(File.separator)?cacheDir:cacheDir.concat(File.separator);
        String packageName = getPackageName();

        cacheDir +=packageName.split("\\.")[1];
        File cDir = new File(cacheDir);
        if(!cDir.exists())
            cDir.mkdirs();
        tempImagePath = cacheDir+ File.separator+"temp.jpg";
        File file = new File(tempImagePath);
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,REQUEST_CODE_PHOTO);
    }
}
