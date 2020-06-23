package com.xintai.xhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xintai.xhao.R;
import com.xintai.xhao.widget.HeadLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xionghao on 2019/3/5 0005.
 * <p/>主页面的Fragment不需要继承带HeadLayout的Fragment，因为这几个Fragment的Head通常都是有变化的。
 */

public class Fragment1 extends Fragment {
    private View view_fragment1;
    private HeadLayout headLayout;
    private Banner banner;
    private String str = "abc";
    Object object;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //        加载fragment的布局的，尽量在这里不要做耗时操作，比如从数据库加载大量数据显示listview。
        view_fragment1 = inflater.inflate(R.layout.view_fragment1, null);
        return view_fragment1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //        当执行onActivityCreated()的时候 activity的onCreate才刚完成。
        //        所以不能再onCreateView()中进行 与activity有交互的UI操作。UI交互操作可以在onActivityCreated()里面进行。
        headLayout = (HeadLayout) view_fragment1.findViewById(R.id.headlayout);
        initView();
    }

    private List<String> mTitleList = new ArrayList<>();
    private List<Integer> mImgList = new ArrayList<>();//支持多种图片类型

    private void initView() {
        headLayout.setGoBackNone();
        headLayout.setTitle("工作台");

        banner = (Banner) view_fragment1.findViewById(R.id.banner);
        mImgList.add(R.mipmap.guide_1);
        mImgList.add(R.mipmap.guide_2);
        mImgList.add(R.mipmap.guide_3);
        mImgList.add(R.mipmap.guide_4);
        for (int i = 1; i <= mImgList.size(); i++) {
            mTitleList.add("第" + i + "张图片");
            //            mTitleList.add("");
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE); // 显示圆形指示器和标题（标题在左，指示器在右边）
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoad());
        //设置图片集合
        banner.setImages(mImgList);
        // 设置标题集合（需要）
        banner.setBannerTitles(mTitleList);
        //设置banner动画效果
        //banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        //banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        // banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        //偷懒的写法，只需要设置图片，图片加载器，然后启动。
        //banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        //指示器默认居中,可以通过 banner.setIndicatorGravity(BannerConfig.RIGHT);，设置指示器的位置（左中右）

        //注意：默认的是没有标题的，即便你设置了也不会显示。
        //如果需要展示标题，是需要设置样式的（默认，标题在左，指示器在右）
        //banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //banner.setBannerTitles(mTitleList);，如果选择上面的样式，那么一定要设置标题，否则报错。

    }

    //定义的一个Bannerde 图片加载器，继承ImageLoader
    public class BannerImageLoad extends ImageLoader { //在该方法内用Glide进行加载图片
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }
}
