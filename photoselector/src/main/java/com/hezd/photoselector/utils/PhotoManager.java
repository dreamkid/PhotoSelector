package com.hezd.photoselector.utils;

import com.hezd.photoselector.model.MediaInfo;

import java.util.List;

/**
 * @auther hezd
 * created on 2018/9/14 16:42
 */
public class PhotoManager {
    private static List<MediaInfo> sImageList;
    public static void setCurrentImageList(List<MediaInfo> imagelist) {
        sImageList = imagelist;
    }
    public static List<MediaInfo> getCurrentImageList() {
        return sImageList;
    }
}
