package com.xintai.xhao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xintai.xhao.MyApplication;

import java.io.InputStream;

/**
 * Created by xionghao on 2018/11/13 0013.
 */

public class ImageUtils {

    /**
     * 使用：imageView.setImageBitmap(bitmap);
     * @param resId 资源图片
     * @return Bitmap
     */
    public static Bitmap readBitMap(int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = MyApplication.getInstance().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
