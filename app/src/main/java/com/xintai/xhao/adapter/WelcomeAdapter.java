package com.xintai.xhao.adapter;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xintai.xhao.utils.MyLog;

/**
 * ViewPager的适配器
 * @author xhao
 *	引导页WelcomeAct
 *	Android实战开发之ViewPager图片回收处理内存溢出完美解决方案（含Fragment）
 * https://blog.csdn.net/bobxie520/article/details/51167957
 * 内存检测工具Leak Canary
 * Android Studio3.0自带内存查看工具Android profiler,对手机版本有要求。
 * 由打印的Log可知道，ViewPager是有预加载的。就是显示1的时候2已经加载完毕，显示2的时候3加载完毕，显示3的时候4加载完毕且关闭1，显示4的时候2关闭。
 * 一路到4，然后点击4跳转新页面，在Activity的onDestroy里面打印viewList，发现里面的ImageView都没有图片资源，那么ImageView3.4的图片资源在哪里释放的呢？
 */
public class WelcomeAdapter extends PagerAdapter{
	private List<View> viewList;
	private int[] imgs;
	private Activity mActivity;
	
	public WelcomeAdapter(Activity mActivity, List<View> viewList,int[] imgs) {
		this.mActivity=mActivity;
		this.viewList=viewList;
		this.imgs = imgs;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object arg2) {
			MyLog.i("destroyItem"+position);
		//回收图片
		View view = viewList.get(position);
		if(view instanceof ImageView){
			((ImageView)view).setBackgroundDrawable(null);
			releaseImageViewResouce(((ImageView)view));
		}
		//移除页面
		container.removeView(view);
		view = null;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MyLog.i("instantiateItem"+position);
		//图片压缩--图片压缩的好处？
//		BitmapFactory.Options opts=new BitmapFactory.Options();
//		opts.inJustDecodeBounds=true;
//		BitmapFactory.decodeResource(mActivity.getResources(), imgs[position], opts);
//		DisplayMetrics outMetrics=new DisplayMetrics();
//		mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//		int x=opts.outWidth/outMetrics.widthPixels;
//		int y=opts.outHeight/outMetrics.heightPixels;
//		if(x>y&&x>1){
//			opts.inSampleSize=x;
//		}else if(y>x&&y>1){
//			opts.inSampleSize=y;
//		}
//		opts.inJustDecodeBounds=false;
//		Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), imgs[position], opts);
		//加载图片
		View view = viewList.get(position);
//		imageView.setImageBitmap(bitmap);//如果是ImageView，可以对图片进行压缩。这里最好一个页面不仅仅是一张图片。
		view.setBackgroundResource(imgs[position]);
		//加载页面
		((ViewPager)container).addView(view);
		return view;
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 释放ImaeView里面的图片资源
	 * @param imageView
	 */
	public void releaseImageViewResouce(ImageView imageView) {
		if (imageView == null) return;
		Drawable drawable = imageView.getDrawable();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap=null;
			}
		}
		System.gc();
	}

}
