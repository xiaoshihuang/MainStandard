package com.xintai.xhao.adapter;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xintai.xhao.utils.MyLog;

/**
 * ViewPager的适配器
 * @author xhao
 *	引导页WelcomeAct
 */
public class WelcomeAdapter extends PagerAdapter{
	List<View> viewList;
	Context c;
	
	public WelcomeAdapter(Context c, List<View> viewList) {
		this.c=c;
		this.viewList=viewList;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object arg2) {
			MyLog.i("destroyItem"+position);
//			View view = viewList.get(position) ;
//			//		Toast.makeText(c, position+"destroyItem", Toast.LENGTH_SHORT).show();
//			//		这里对资源的释放对，页面的卡顿，有所帮助
//			BitmapDrawable drawable = (BitmapDrawable)view.getBackground() ;
//			if (drawable != null) {
//				drawable.getBitmap().recycle() ;
//			}
			container.removeView(viewList.get(position));// 删除页卡
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		MyLog.i("instantiateItem"+position);
		View view = viewList.get(position) ;
//		Toast.makeText(c, position+"instantiateItem", Toast.LENGTH_SHORT).show();
		container.addView(view, 0);// 添加页卡
		return viewList.get(position);
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

}
