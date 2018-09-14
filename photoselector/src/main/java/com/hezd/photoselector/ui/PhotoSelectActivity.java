package com.hezd.photoselector.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hezd.photoselector.R;
import com.hezd.photoselector.utils.PhotoManager;
import com.hezd.photoselector.adapter.PhotoSelectAdapter;
import com.hezd.photoselector.model.MediaInfo;
import com.hezd.photoselector.utils.ToastUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 幻灯片选择界面
 * Created by luminita on 2016/12/13.
 */

public class PhotoSelectActivity extends Activity implements  View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView photoGroup;
    private TextView title;
    private TextView numMark;
    private Button add;
    private ImageView back;
    /**
     * 当前相册下所有照片路径
     */
    private List<MediaInfo> phoList;
    /**
     * 已选择的照片
     */
    private LinkedList<MediaInfo> selectedImgList = new LinkedList<>();
    private int picCount;
    private PhotoSelectActivity mContext;
    private PhotoSelectAdapter selectAdapter;
    private LinearLayout photoDetail;
    private ImageView photoFull;
    public static final int INIT_SUCCESS = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case INIT_SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    setData();
                    break;
            }
        }
    };
    private ProgressBar progressBar;
    private String mFlderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
        mContext = PhotoSelectActivity.this;
        phoList = PhotoManager.getCurrentImageList();

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        mFlderName = getIntent().getStringExtra("folderName");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 关联控件
     */
    public void getViews() {
        progressBar = findViewById(R.id.progressbar);
        photoGroup = findViewById(R.id.gv_photo);
        title = findViewById(R.id.tv_title);
        numMark = findViewById(R.id.tv_num);
        add = findViewById(R.id.btn_complete);
        back = findViewById(R.id.iv_back);
        photoDetail = findViewById(R.id.ll_detail);
        photoFull = findViewById(R.id.iv_photo);
    }

    /**
     * 初始化控件
     */
    public void setViews() {
        selectAdapter = new PhotoSelectAdapter(mContext);
        photoGroup.setAdapter(selectAdapter);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
        selectAdapter.setData(phoList);
        title.setText(TextUtils.isEmpty(mFlderName)?"":mFlderName);
    }

    /**
     * 设置监听事件
     */
    public void setListeners() {
        back.setOnClickListener(this);
        photoFull.setOnClickListener(this);
        add.setOnClickListener(this);
        //GroupView的item点击事件
        photoGroup.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            onBackPressed();

        } else if (i == R.id.iv_photo) {
            photoDetail.setVisibility(View.GONE);

        } else if (i == R.id.cb_check_all) {
        } else if (i == R.id.btn_complete) {
            Intent intent = new Intent();
            intent.putExtra("images", selectedImgList);
            setResult(SelectDialog.RESULT_CODE_GALLERY, intent);
            finish();

        }
    }

    private String getFormatImageList(LinkedList<MediaInfo> selectedImgList) {
        StringBuilder sb = new StringBuilder();
        for(MediaInfo mediaInfo:selectedImgList) {
            sb.append(mediaInfo.getAssetpath()+",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


    public void setData() {
        selectAdapter.setData(phoList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //选择与取消单张照片
        MediaInfo mediaInfo = (MediaInfo) parent.getItemAtPosition(position);
        if (selectedImgList.contains(mediaInfo)) {
            mediaInfo.setCheked(false);
            // phoList.get(i).setChecked(false);
            selectedImgList.remove(mediaInfo);
            picCount--;
        } else {
            if (picCount >= 9) {
                ToastUtil.showToastSavor(mContext, "最多只能选择9张");
                return;
            }

            mediaInfo.setCheked(true);
            //phoList.set(i,mediaInfo);
            selectedImgList.add(mediaInfo);
            picCount++;

        }
        //selectAdapter.setData(phoList);
        selectAdapter.notifyDataSetChanged();
        numMark.setText("(" + picCount + "/9)");

    }


}
