package com.hezd.photoselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hezd.photoselector.R;
import com.hezd.photoselector.model.MediaInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luminita on 2016/12/14.
 */

public class PhotoSelectAdapter extends BaseAdapter {

    private Context mContext;
    private List<MediaInfo> mList = new ArrayList<>();
    private LayoutInflater mInflater;

    public PhotoSelectAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public void setData(List<MediaInfo> list) {

        if(list!=null&&list.size()>0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (mList != null && mList.size() > 0)
            return mList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mList != null && mList.size() > 0)
            return mList.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_photo_select, null);
            holder.imgContent = view.findViewById(R.id.iv_content);
            holder.check = view.findViewById(R.id.cb_check);
            //绘制图片大小
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final MediaInfo mediaInfo = mList.get(i);
        String assetpath = mediaInfo.getAssetpath();
        File file = new File(assetpath);
        if(file.exists()) {
            Glide.with(mContext).
                    load(mediaInfo.getAssetpath()).
                    centerCrop().
                    placeholder(R.mipmap.ic_launcher)
                    .dontAnimate().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    into(holder.imgContent);
        }else {
            Glide.with(mContext).
                    load(R.mipmap.ic_launcher).
                    centerCrop().
                    placeholder(R.mipmap.ic_launcher)
                    .dontAnimate().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    into(holder.imgContent);
        }

        holder.check.setChecked(mediaInfo.isCheked());
        return view;
    }

    private class ViewHolder {
        public CheckBox check;
        public ImageView imgContent;
    }

}
