package com.xintai.xhao.dialog;



import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.xintai.xhao.R;

/**
 * 自定义Dialog组件
 * @author Administrator
 *
 */
public class NewDialog extends Dialog {
	private TextView title,content;
	private Button btn_ok,btn_cancle;
	private View view;
	private LinearLayout linear;
	private int width;
	private ProgressBar progressbar;

//	dialog.setCanceledOnTouchOutside(false);//调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
//	dialog.setCancelable(false);//调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
	
	public NewDialog(Context context) {
		super(context, R.style.Dialog);//定义主题/类型
		// TODO Auto-generated constructor stub
		create1();
	}

	private void create1() {
		// TODO Auto-generated method stub
		setContentView(R.layout.dialog_newdialog);

		linear=(LinearLayout) findViewById(R.id.linear);
		view=findViewById(R.id.view);
		title=(TextView) findViewById(R.id.title);
		content=(TextView) findViewById(R.id.context);
		progressbar= (ProgressBar) findViewById(R.id.progressbar);
		btn_ok=(Button) findViewById(R.id.btn_ok);
		btn_cancle=(Button) findViewById(R.id.btn_cancle);
		
		//获取屏幕宽度方法1
//		width=this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
		
		//获取屏幕宽度方法2
		DisplayMetrics  dm = new DisplayMetrics();    
		this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);    
		width = dm.widthPixels; 
 
		
		this.getWindow().setLayout(width-width/5, LayoutParams.WRAP_CONTENT);
		
		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	/**
	 * 显示进度条
	 */
	public void showProgressbar(){
		progressbar.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置进度条
	 * @param progress 进度条数字
	 */
	public void setProgress(int progress){
		progressbar.setProgress(progress);
	}
	
	/**
	 * 修改dialog标题
	 * @param str
	 * @return
	 */
	public NewDialog setTitle(String str){
		title.setText(str);
		return this;
	}
	/**
	 * 修改提示的内容
	 * @param str
	 * @return
	 */
	public NewDialog setContent(String str){
		content.setText(str);
		return this;
	}
	/**
	 * 修改左边按钮名称
	 * @param str
	 * @return
	 */
	public NewDialog setYesText(String str){
		btn_ok.setText(str);
		return this;
	}
	/**
	 * 修改右边按钮名称
	 * @param str
	 * @return
	 */
	public NewDialog setNoText(String str){
		btn_cancle.setText(str);
		return this;
	}

	/**
	 * 设置Dialog里面的按钮是否可以被点击
	 * @param clickable
	 */
	public void setButUnClickable(Boolean clickable){
		btn_ok.setClickable(clickable);
		btn_cancle.setClickable(clickable);
	}
	/**
	 * 点击左边按钮触发事件
	 * @param click
	 * @return
	 */
	public NewDialog setOnYClick(View.OnClickListener click){
		btn_ok.setOnClickListener(click);
		return this;
	}
	/**
	 * 点击右边按钮触发事件
	 * @param click
	 * @return
	 */
	public NewDialog setOnNClick(View.OnClickListener click){
		btn_cancle.setOnClickListener(click);
		return this;
	}
	
	//设置整个dialog背景
	public void setBackgroundColor(int color){
		linear.setBackgroundColor(color);//这里指的是Color类，Color.Black,或者Color.parsrColor(#ffffff)
	}
	public void setBackgroundDrawable(Drawable d){
		linear.setBackgroundDrawable(d);//这里getResources().getDrawable(R.XX.XX)
	}
	public void setBackgroundResource(int resid){
		linear.setBackgroundResource(resid);//这里直接引用R.XX.XX
	}
	//设置标题背景
	public void setTitleBackgroundColor(int color){
		view.setBackgroundColor(color);
	}
	public void setTitleBackgroundDrawable(Drawable d){
		view.setBackgroundDrawable(d);
	}
	public void setTitleBackgroundResource(int resid){
		view.setBackgroundResource(resid);
	}

}
