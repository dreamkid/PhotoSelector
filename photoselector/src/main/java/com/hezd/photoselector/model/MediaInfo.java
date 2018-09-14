package com.hezd.photoselector.model;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/24.
 */

public class MediaInfo implements Serializable {
    /**
     * 文件路径
     */
    private String assetpath;
    private boolean isCheked;
    private String mimeType;
    private String name;

    @Override
    public String toString() {
        return "MediaInfo{" +
                "assetpath='" + assetpath + '\'' +
                ", isCheked=" + isCheked +
                ", mimeType='" + mimeType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaInfo mediaInfo = (MediaInfo) o;

        if (isCheked != mediaInfo.isCheked) return false;
        if (assetpath != null ? !assetpath.equals(mediaInfo.assetpath) : mediaInfo.assetpath != null)
            return false;
        if (mimeType != null ? !mimeType.equals(mediaInfo.mimeType) : mediaInfo.mimeType != null)
            return false;
        return name != null ? name.equals(mediaInfo.name) : mediaInfo.name == null;
    }

    @Override
    public int hashCode() {
        int result = assetpath != null ? assetpath.hashCode() : 0;
        result = 31 * result + (isCheked ? 1 : 0);
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getAssetpath() {
        return assetpath;
    }

    public void setAssetpath(String assetpath) {
        this.assetpath = assetpath;
    }

    public boolean isCheked() {
        return isCheked;
    }

    public void setCheked(boolean cheked) {
        isCheked = cheked;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
