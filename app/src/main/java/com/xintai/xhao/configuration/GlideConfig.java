package com.xintai.xhao.configuration;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.xintai.xhao.F;
import com.xintai.xhao.utils.FileUtils;

/**
 * 配置在AndroidManifest
 */
public class GlideConfig implements GlideModule {
    //Glide详解https://blog.csdn.net/guolin_blog/article/details/53759439
    //之所以选择Glide是因为：
    // 1.效率高（可以测比较UniversalImageLoader，Volley，Picasso，Glide，Fresco），
    // 2.功能强大（完善的缓存机制，支持多种途径来源的图片包括Gif,使用占位图，自动对图片压缩显示，还能指定图片大小），
    // 3.使用简便(一句话调用with,load,into)。

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
        String downloadDirectoryPath = F.glade_cache_path;
        //设置缓存的大小为70M
        int cacheSize = F.glade_cache_size;
        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}
