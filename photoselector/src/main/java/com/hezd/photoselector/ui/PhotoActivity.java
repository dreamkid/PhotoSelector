package com.hezd.photoselector.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hezd.photoselector.R;
import com.hezd.photoselector.utils.AsyncTaskUtil;
import com.hezd.photoselector.utils.MediaUtils;
import com.hezd.photoselector.utils.PhotoManager;
import com.hezd.photoselector.adapter.PhotoAdapter;
import com.hezd.photoselector.interfaces.AsyncCallBack;
import com.hezd.photoselector.model.MediaInfo;
import com.hezd.photoselector.model.PhotoInfo;
import com.hezd.photoselector.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hezd on 2018/09/14.
 */

//本地图片页面
public class PhotoActivity extends Activity implements View.OnClickListener{
    private ImageView back;
    private TextView title;
    private ListView photoList;
    private List<PhotoInfo> list = new ArrayList<>();
    private HashMap<String, ArrayList<MediaInfo>> photoMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        getViews();
        setViews();
        initPhotoList();
        setListeners();
    }



    //点击事件
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_left)
            onBackPressed();
    }

    /**
     * 关联控件
     */
    public void getViews() {
        back = findViewById(R.id.iv_left);
        title = findViewById(R.id.tv_center);
        photoList = findViewById(R.id.lv_photo_list);
    }

    /**
     * 初始化控件
     */
    public void setViews() {
        title.setText("照片列表");
    }

    /**
     * 设置监听事件
     */
    public void setListeners() {
        back.setOnClickListener(this);
        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!photoMap.isEmpty()) {
                    String folderName = list.get(i).getFolderName();
                    ArrayList<MediaInfo> childList = photoMap.get(folderName);
                    if (childList.size() <= 0) {
                        ToastUtil.showToastSavor(PhotoActivity.this, "没有发现照片哦");
                        return;
                    }
                    PhotoManager.setCurrentImageList(childList);
                    Intent intent = new Intent(PhotoActivity.this, PhotoSelectActivity.class);
                    intent.putExtra("folderName",folderName);
                    startActivityForResult(intent, SelectDialog.REQUEST_CODE_GALLERY);
                }
            }
        });
    }

    /**
     * 获取手机内所有图片路径
     */
    private void initPhotoList() {

        AsyncTaskUtil.doAsync(new AsyncCallBack() {

            ProgressDialog progressDialog;

            @Override
            public void onPreExecute() {
                super.onPreExecute();
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    ToastUtil.showToastSavor(PhotoActivity.this, "没有发现存储设备");
                    return;
                }
                progressDialog = ProgressDialog.show(PhotoActivity.this, null, "正在加载...");
            }

            @Override
            public void doInBackground() {
                MediaUtils.getImgInfo(PhotoActivity.this, photoMap);
            }

            @Override
            public void onPostExecute() {
                progressDialog.dismiss();
                list = MediaUtils.subGroupOfImage(photoMap);
                photoList.setAdapter(new PhotoAdapter(PhotoActivity.this, list));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SelectDialog.REQUEST_CODE_GALLERY&&resultCode == SelectDialog.RESULT_CODE_GALLERY ) {
            setResult(resultCode,data);
            finish();
        }
    }
}
